package org.aguiar.leveler.listeners;

import org.aguiar.leveler.Leveler;
import org.aguiar.leveler.utils.DungeonConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

import java.util.List;
import java.util.Map;

public class SpawnSetListener implements Listener {
  private final Leveler plugin;

  public SpawnSetListener(Leveler plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onPlayerInteract(PlayerInteractEvent event) {
    if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null) return;

    if (event.getHand() == EquipmentSlot.OFF_HAND) return;

    ItemStack itemInHand = event.getItem();

    Player player = event.getPlayer();

    Location clickedPosition = event.getClickedBlock().getLocation();

    if (itemInHand == null) {
      player.sendMessage(String.format("X: %.1f, Y: %.1f, Z: %.1f", clickedPosition.getX(), clickedPosition.getY(), clickedPosition.getZ()));
      return;
    }

    Material itemMaterial = itemInHand.getType();

    World dungeonWorld = player.getWorld();

    boolean hasRequiredMetadata = dungeonWorld.hasMetadata("dungeonId") && dungeonWorld.hasMetadata("raidProgression");

    if (!hasRequiredMetadata) return;

    boolean isSetSpawn = itemMaterial.equals(Material.NETHERITE_HOE);
    boolean isAddSpawn = itemMaterial.equals(Material.DIAMOND_HOE);
    boolean isAddAndSetSpawn = itemMaterial.equals(Material.GOLDEN_HOE);

    if (!isSetSpawn && !isAddSpawn && !isAddAndSetSpawn) return;

    String dungeonId = dungeonWorld.getMetadata("dungeonId").stream().findFirst().map(MetadataValue::asString).get();
    DungeonConfiguration dungeonConfiguration = new DungeonConfiguration(plugin, dungeonId);

    if (dungeonConfiguration.loadConfig()) {
      List<Map<String, Object>> spawnPoints = (List<Map<String, Object>>) dungeonConfiguration.getConfig().getList("spawn_points");
      assert spawnPoints != null;

      Map<String, Object> newSpawn = Map.of("x", clickedPosition.getX(), "y", clickedPosition.getY(), "z", clickedPosition.getZ());

      if (isSetSpawn) {
        dungeonConfiguration.getConfig().set("spawn_points", List.of(newSpawn));
        player.sendMessage(ChatColor.GREEN + "Ponto de nascimento redefinido");
      } else if (isAddSpawn) {
        spawnPoints.add(newSpawn);
        player.sendMessage(ChatColor.GREEN + "Novo ponto de nascimento adicionado");
      } else {
        spawnPoints.addFirst(newSpawn);
        player.sendMessage(ChatColor.GREEN + "Novo ponto de nascimento adicionado e redefinido");
      }

      dungeonConfiguration.saveConfig();
    } else {
      player.sendMessage(ChatColor.RED + "Não foi possível carregar configurações da dungeon, verifique os logs do servidor");
    }
  }
}
