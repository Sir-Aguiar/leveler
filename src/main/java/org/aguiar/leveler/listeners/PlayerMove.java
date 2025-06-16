package org.aguiar.leveler.listeners;

import org.aguiar.leveler.Leveler;
import org.aguiar.leveler.entities.dungeons.Dungeon;
import org.aguiar.leveler.utils.DungeonConfiguration;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.MetadataValue;

public class PlayerMove implements Listener {
  private final Leveler plugin;

  public PlayerMove(Leveler plugin) {
    this.plugin = plugin;
  }


  @EventHandler
  public void onMove(PlayerMoveEvent event) {
    if (event.getTo() == null) return;

    if (event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockY() == event.getTo().getBlockY() && event.getFrom().getBlockZ() == event.getTo().getBlockZ()) {
      return;
    }

    World world = event.getTo().getWorld();

    if (world == null) return;

    if (!world.hasMetadata("dungeonId")) return;

    Dungeon dungeon = plugin.getActiveDungeon(event.getPlayer().getUniqueId());

    if (dungeon == null || dungeon.isRaidStarted()) {
      return;
    }

    DungeonConfiguration levelConfig = dungeon.getLevelsConfig();

    if (levelConfig.loadConfig("levels")) {
      Integer raidProgression = world.getMetadata("raidProgression").stream().findFirst().map(MetadataValue::asInt).get();
      String objectKey = "level_" + (raidProgression + 1) + "_start_bounds";

      double minX = levelConfig.getConfig().getDouble(objectKey + ".min_x");
      double minY = levelConfig.getConfig().getDouble(objectKey + ".min_y");
      double minZ = levelConfig.getConfig().getDouble(objectKey + ".min_z");
      double maxX = levelConfig.getConfig().getDouble(objectKey + ".max_x");
      double maxY = levelConfig.getConfig().getDouble(objectKey + ".max_y");
      double maxZ = levelConfig.getConfig().getDouble(objectKey + ".max_z");

      double actualMinX = Math.min(minX, maxX);
      double actualMaxX = Math.max(minX, maxX);
      double actualMinY = Math.min(minY, maxY);
      double actualMaxY = Math.max(minY, maxY);
      double actualMinZ = Math.min(minZ, maxZ);
      double actualMaxZ = Math.max(minZ, maxZ);

      double playerX = event.getPlayer().getLocation().getX();
      double playerY = event.getPlayer().getLocation().getY();
      double playerZ = event.getPlayer().getLocation().getZ();

      boolean isInBounds = playerX >= actualMinX && playerX <= actualMaxX && playerY >= actualMinY && playerY <= actualMaxY && playerZ >= actualMinZ && playerZ <= actualMaxZ;

      if (isInBounds) {
        event.getPlayer().sendMessage("Você começou a raid");
        dungeon.startRaid();
      }
    }

  }
}
