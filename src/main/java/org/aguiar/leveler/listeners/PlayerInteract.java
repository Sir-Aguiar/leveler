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
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

import java.util.List;
import java.util.Map;

public class PlayerInteract implements Listener {
  private final Leveler plugin;

  public PlayerInteract(Leveler plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onSpawnSet(PlayerInteractEvent event) {
    if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null) return;

    ItemStack itemInHand = event.getItem();

    if (itemInHand == null) return;

    Material itemMaterial = itemInHand.getType();

    Player player = event.getPlayer();
    World dungeonWorld = player.getWorld();

    boolean hasRequiredMetadata = dungeonWorld.hasMetadata("dungeonId");

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

      Location clickedPosition = event.getClickedBlock().getLocation();

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
