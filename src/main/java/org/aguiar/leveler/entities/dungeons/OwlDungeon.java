package org.aguiar.leveler.entities.dungeons;

import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public class OwlDungeon extends Dungeon {
  private final Location spawnLocation;

  public OwlDungeon(String dungeonName, String schemPath, Location dungeonLocation, World world) {
    super(dungeonName, schemPath, dungeonLocation, world);

    world.setGameRule(GameRule.DO_PATROL_SPAWNING, false);
    world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
    world.setGameRule(GameRule.FALL_DAMAGE, false);

    world.setTime(24000);

    spawnLocation = new Location(world, -6, -59, -9, -180, 0);
  }

  public void teleportPlayer(Player player) {
    player.teleport(spawnLocation);
  }

  public void teleportPlayers(List<Player> players) {
    players.forEach(this::teleportPlayer);
  }
}
