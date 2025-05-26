package org.aguiar.leveler.listeners;

import org.aguiar.leveler.Leveler;
import org.aguiar.leveler.utils.DungeonConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefineBarriersListener implements Listener {
  private final Leveler plugin;

  public DefineBarriersListener(Leveler plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onInteract(PlayerInteractEvent event) {
    if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null) return;

    if (event.getHand() == EquipmentSlot.OFF_HAND) return;

    if (event.getClickedBlock().getBlockData().getMaterial() != Material.BARRIER) return;

    Player player = event.getPlayer();

    boolean hasRequiredMetadata = player.hasMetadata("_edit_dungeonId") && player.hasMetadata("_edit_dungeonLevel") && player.hasMetadata("_edit_editMode");

    if (!hasRequiredMetadata) return;

    String dungeonId = player.getMetadata("_edit_dungeonId").stream().findFirst().map(MetadataValue::asString).get();
    String dungeonLevel = player.getMetadata("_edit_dungeonLevel").stream().findFirst().map(MetadataValue::asString).get();
    String editMode = player.getMetadata("_edit_editMode").stream().findFirst().map(MetadataValue::asString).get();

    player.sendMessage(String.format("Você está na dungeon %s editando o nível %s no modo %s", dungeonId, dungeonLevel, editMode));

    DungeonConfiguration dungeonConfiguration = new DungeonConfiguration(plugin, dungeonId);

    if (dungeonConfiguration.loadConfig()) {
      String configName = "level_" + dungeonLevel + "_barriers";
      List<Map<String, Object>> levelBarriers = (List<Map<String, Object>>) dungeonConfiguration.getConfig().getList(configName);

      if (levelBarriers == null) {
        levelBarriers = new ArrayList<>();
      }

      Location clickedLocation = event.getClickedBlock().getLocation();
      Map<String, Object> newBarrier = Map.of("x", clickedLocation.getX(), "y", clickedLocation.getY(), "z", clickedLocation.getZ());

      if (editMode.equalsIgnoreCase("set")) {
        levelBarriers = new ArrayList<>();
        levelBarriers.add(newBarrier);
        player.sendMessage(ChatColor.GREEN + "Nova barreira definida ao nível " + dungeonLevel);
        player.setMetadata("_edit_editMode", new FixedMetadataValue(plugin, "add"));
      } else if (editMode.equalsIgnoreCase("add")) {
        levelBarriers.add(newBarrier);
        player.sendMessage(ChatColor.GREEN + "Nova barreira adicionada ao nível " + dungeonLevel);
      }

      dungeonConfiguration.getConfig().set(configName, levelBarriers);
      dungeonConfiguration.saveConfig();
    } else {
      player.sendMessage(ChatColor.RED + "Não foi possível carregar configurações da dungeon, verifique os logs do servidor");
    }
  }
}
