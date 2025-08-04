package org.aguiar.leveler.utils;

import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class WorldsManager {
  private static final Map<UUID, Location> playersOrigin = new HashMap<>();
  private static final List<World> existentWorlds = new ArrayList<>();

  public static World createWorld(String worldName) {
    WorldCreator worldCreator = new WorldCreator(worldName);

    worldCreator.environment(World.Environment.NORMAL);
    worldCreator.type(WorldType.FLAT);

    World createdWorld = worldCreator.createWorld();

    existentWorlds.add(createdWorld);

    return createdWorld;
  }

  public static List<World> getPlayerWorlds(Player player) {
    return existentWorlds.stream().filter(world -> {
      String[] worldComponents = world.getName().split("_");
      return worldComponents[0].equals("raid") && worldComponents[1].equals(player.getUniqueId().toString());
    }).toList();
  }

  public static World getWorld(String worldName) {
    return existentWorlds.stream().filter(world -> world.getName().equals(worldName)).findFirst().get();
  }

  public static void deleteWorld(String worldName) {
    World world = getWorld(worldName);

    deleteWorld(world);
  }

  public static void deleteWorld(World world) {
    unloadPlayers(world);

    boolean isWorldUnloaded = Bukkit.unloadWorld(world, false);

    if (isWorldUnloaded) {
      File worldFolder = world.getWorldFolder();

      if (worldFolder.exists()) {
        try {
          existentWorlds.remove(world);
          FileUtils.deleteDirectory(worldFolder);
        } catch (IOException e) {
          Bukkit.getLogger().severe("Error deleting world directory " + world.getName() + ": " + e.getMessage());
          e.printStackTrace();
        }
      }
    }
  }

  public static void unloadPlayers(World world) {
    List<Player> playersInWorld = world.getPlayers();

    playersInWorld.forEach(player -> {
      Location originLocation = playersOrigin.get(player.getUniqueId());
      player.teleport(originLocation);
      playersOrigin.remove(player.getUniqueId());
    });
  }

  public static void loadPlayers(Player player, Location location) {
    Location playerOrigin = player.getLocation();
    player.teleport(location);
    playersOrigin.put(player.getUniqueId(), playerOrigin);
  }

  public static void loadPlayers(List<Player> players, Location location) {
    players.forEach(player -> {
      loadPlayers(player, location);
    });
  }
}
