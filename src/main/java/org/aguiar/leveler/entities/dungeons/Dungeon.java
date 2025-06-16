package org.aguiar.leveler.entities.dungeons;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.aguiar.leveler.Leveler;
import org.aguiar.leveler.entities.raids.Raid;
import org.aguiar.leveler.entities.raids.RaidMob;
import org.aguiar.leveler.utils.DungeonConfiguration;
import org.aguiar.leveler.utils.WorldsManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Dungeon {
  private final Leveler plugin;
  private final Player player;
  private final DungeonConfiguration dungeonConfig;
  private final DungeonConfiguration levelsConfig;
  private final File schematicFile;
  private final String dungeonId;
  private final World world;
  private final Location spawnLocation;
  private Clipboard schem;
  private boolean raidStarted = false;

  public Dungeon(String dungeonId, Leveler plugin, Player player) {
    this.plugin = plugin;
    this.dungeonId = dungeonId;
    this.dungeonConfig = new DungeonConfiguration(plugin, dungeonId);
    this.levelsConfig = new DungeonConfiguration(plugin, dungeonId);
    this.player = player;

    this.world = createWorld();
    this.schematicFile = new File(plugin.getDataFolder(), "schematics" + File.separator + dungeonId + ".schem");
    pasteSchem();

    dungeonConfig.loadConfig("config");
    levelsConfig.loadConfig("levels");

    Map<String, Object> spawnPoint = (Map<String, Object>) dungeonConfig.getConfig().getList("spawn_points").getFirst();

    double x = ((Number) spawnPoint.get("x")).doubleValue();
    double y = ((Number) spawnPoint.get("y")).doubleValue();
    double z = ((Number) spawnPoint.get("z")).doubleValue();

    this.spawnLocation = new Location(this.world, x, y, z);
  }

  public void teleportPlayer(Player player) {
    WorldsManager.loadPlayers(player, spawnLocation);
  }

  public void teleportPlayer(List<Player> players) {
    players.forEach(this::teleportPlayer);
  }

  public void pasteSchem() {
    ClipboardFormat format = ClipboardFormats.findByFile(schematicFile);

    try (ClipboardReader reader = format.getReader(new FileInputStream(schematicFile))) {
      schem = reader.read();
    } catch (Exception e) {
      e.printStackTrace();
    }

    WorldEditPlugin worldEditPlugin = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");

    com.sk89q.worldedit.world.World worldEditWorld = BukkitAdapter.adapt(world);
    BlockVector3 pasteOrigin = BlockVector3.at(0.0, -60.0, 0.0);

    try {
      assert worldEditPlugin != null;

      try (EditSession editSession = worldEditPlugin.getWorldEdit().newEditSession(worldEditWorld)) {
        Operation operation = new ClipboardHolder(this.getSchem()).createPaste(editSession).to(pasteOrigin).ignoreAirBlocks(false).build();
        Operations.complete(operation);
      }
    } catch (WorldEditException e) {
      e.printStackTrace();
    }
  }

  public World createWorld() {
    String worldName = "dungeon_" + player.getUniqueId() + "_" + System.currentTimeMillis();

    World createdWorld = WorldsManager.createWorld(worldName);

    createdWorld.setMetadata("dungeonId", new FixedMetadataValue(plugin, getDungeonId()));
    createdWorld.setMetadata("raidProgression", new FixedMetadataValue(plugin, 0));

    createdWorld.setGameRule(GameRule.DO_PATROL_SPAWNING, false);
    createdWorld.setGameRule(GameRule.DO_MOB_SPAWNING, false);
    createdWorld.setGameRule(GameRule.FALL_DAMAGE, false);

    createdWorld.setTime(24000);

    return createdWorld;
  }

  public void startRaid() {
    if (levelsConfig.loadConfig("levels")) {
      // Pegar a fase da respectiva raid
      Integer raidProgression = world.getMetadata("raidProgression").stream().findFirst().map(MetadataValue::asInt).get();

      // Mapear os mobs
      List<Map<String, Object>> mobs = (List<Map<String, Object>>) levelsConfig.getConfig().getList("level_" + (raidProgression + 1) + "_raid.mobs");
      List<RaidMob> raidMobs = new ArrayList<>();
      Location playerLocation = player.getLocation();

      Map<String, Double> playerLocationMap = Map.of("x", playerLocation.getX(), "y", playerLocation.getY(), "z", playerLocation.getZ());

      mobs.forEach(mob -> {
        RaidMob raidMob = new RaidMob((String) mob.get("type"), (String) mob.get("name"), (String) mob.get("entityType"), playerLocationMap);
        raidMobs.add(raidMob);
      });

      // Spawnar os mobs
      Raid currentRaid = new Raid(plugin, player);

      raidMobs.forEach(raidMob -> {
        currentRaid.spawnMob(world, raidMob);
      });

      setRaidStarted(true);
      player.sendMessage(ChatColor.GOLD + "Raid Iniciada");
    }


  }

  public boolean isRaidStarted() {
    return raidStarted;
  }

  public void setRaidStarted(boolean raidStarted) {
    this.raidStarted = raidStarted;
  }

  public Player getPlayer() {
    return player;
  }


  public Clipboard getSchem() {
    return schem;
  }

  public Location getSpawnLocation() {
    return spawnLocation;
  }

  public World getWorld() {
    return world;
  }

  public String getDungeonId() {
    return dungeonId;
  }

  public DungeonConfiguration getDungeonConfig() {
    return dungeonConfig;
  }

  public DungeonConfiguration getLevelsConfig() {
    return levelsConfig;
  }
}
