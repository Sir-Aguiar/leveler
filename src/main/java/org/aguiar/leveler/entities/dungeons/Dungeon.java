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
import org.aguiar.leveler.utils.DungeonConfiguration;
import org.aguiar.leveler.utils.WorldsManager;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

public abstract class Dungeon {
  private final Leveler plugin;
  private final Player player;
  private final DungeonConfiguration dungeonConfig;
  private final File schematicFile;
  private final String dungeonId;
  private final World world;
  private final Location spawnLocation;
  private Clipboard schem;


  public Dungeon(String dungeonId, Leveler plugin, Player player) {
    this.plugin = plugin;
    this.dungeonId = dungeonId;
    this.dungeonConfig = new DungeonConfiguration(plugin, dungeonId);
    this.player = player;

    this.world = createWorld();
    this.schematicFile = new File(plugin.getDataFolder(), "schematics" + File.separator + dungeonId + ".schem");
    pasteSchem();

    dungeonConfig.loadConfig();

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
}
