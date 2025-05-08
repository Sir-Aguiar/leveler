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
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.io.FileInputStream;

public abstract class Dungeon {
  private final String name;
  private final Location dungeonLocation;
  private final File file;
  private Clipboard schem;

  public Dungeon(String dungeonName, String schemPath, Location dungeonLocation, World world) {
    this.file = new File(schemPath);

    ClipboardFormat format = ClipboardFormats.findByFile(file);

    try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
      schem = reader.read();
    } catch (Exception e) {
      e.printStackTrace();
    }

    WorldEditPlugin worldEditPlugin = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
    com.sk89q.worldedit.world.World worldEditWorld = BukkitAdapter.adapt(world);
    BlockVector3 pasteOrigin = BlockVector3.at(dungeonLocation.getX(), dungeonLocation.getY(), dungeonLocation.getZ());

    try (EditSession editSession = worldEditPlugin.getWorldEdit().newEditSession(worldEditWorld)) {
      Operation operation = new ClipboardHolder(this.getSchem()).createPaste(editSession).to(pasteOrigin).ignoreAirBlocks(false).build();
      Operations.complete(operation);
    } catch (WorldEditException e) {
      e.printStackTrace();
    }

    this.name = dungeonName;
    this.dungeonLocation = dungeonLocation;
  }

  public Clipboard getSchem() {
    return schem;
  }

  public File getFile() {
    return file;
  }

  public Location getDungeonLocation() {
    return dungeonLocation;
  }

  public String getName() {
    return name;
  }
}
