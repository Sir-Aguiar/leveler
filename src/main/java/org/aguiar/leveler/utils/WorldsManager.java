package org.aguiar.leveler.utils;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WorldsManager {
  private static final List<World> existentWorlds = new ArrayList<>();

  public static World createWorld(String worldName) {
    WorldCreator worldCreator = new WorldCreator(worldName);

    worldCreator.environment(World.Environment.NORMAL);
    worldCreator.type(WorldType.FLAT);

    World createdWorld = worldCreator.createWorld();

    existentWorlds.add(createdWorld);

    return createdWorld;
  }

  public static World getWorld(String worldName) {
    return existentWorlds.stream().filter(world -> world.getName().equals(worldName)).findFirst().get();
  }

  public static List<World> getPlayerWorlds(Player player) {
    return existentWorlds.stream().filter(world -> {
      String[] worldComponents = world.getName().split("_");
      return worldComponents[0].equals(player.getUniqueId().toString());
    }).toList();
  }

  public static void deleteWorld(String worldName) {
    existentWorlds.removeIf(world -> world.getName().equals(worldName));
  }

  public static void deleteWorld(World world) {
    existentWorlds.remove(world);
  }

}
