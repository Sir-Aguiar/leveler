package org.aguiar.leveler.commands;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
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
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;

public class NewWorld implements CommandExecutor {
  private final Leveler plugin;
  private final WorldEdit worldEdit;
  private Clipboard OWL_SCHEM = null;

  public NewWorld(Leveler plugin) {
    this.plugin = plugin;

    WorldEditPlugin worldEditPlugin = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");

    if (worldEditPlugin == null) {
      Bukkit.getLogger().severe("WorldEdit não encontrado! A funcionalidade de schematic não funcionará.");
    }

    assert worldEditPlugin != null;

    worldEdit = worldEditPlugin.getWorldEdit();

    loadSchematics();
  }

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (!(sender instanceof Player player)) {
      return false;
    }

    String worldName = player.getUniqueId().toString() + "_raid_" + System.currentTimeMillis();

    WorldCreator creator = new WorldCreator(worldName);
    creator.environment(World.Environment.NORMAL);
    creator.type(WorldType.FLAT);

    World raidWorld = player.getServer().createWorld(creator);
    Location spawnLocation = new Location(raidWorld, -6, -59, -9, -180, 0);

    if (raidWorld != null) {
      raidWorld.setGameRule(org.bukkit.GameRule.DO_DAYLIGHT_CYCLE, false);
      raidWorld.setTime(6000); // Dia
      raidWorld.setDifficulty(org.bukkit.Difficulty.HARD);


      Location owlLocation = new Location(raidWorld, 0, -61, 0);
      com.sk89q.worldedit.world.World worldEditWorld = BukkitAdapter.adapt(raidWorld);
      BlockVector3 pasteOrigin = BlockVector3.at(owlLocation.getX(), owlLocation.getY(), owlLocation.getZ());

      try (EditSession editSession = worldEdit.newEditSession(worldEditWorld)) {
        Operation operation = new ClipboardHolder(OWL_SCHEM).createPaste(editSession).to(pasteOrigin).ignoreAirBlocks(false).build();
        Operations.complete(operation);
        // editSession.flushSession();
      } catch (WorldEditException e) {
        e.printStackTrace();
      }

      player.teleport(spawnLocation);
    } else {
      player.sendMessage("§cFalha ao criar a arena da raid!");
    }

    return true;
  }

  private void loadSchematics() {
    File owlFile = new File(plugin.getDataFolder(), "schematics/lvl_1_dungeon.schem");
    ClipboardFormat format = ClipboardFormats.findByFile(owlFile);

    try (ClipboardReader reader = format.getReader(new FileInputStream(owlFile))) {
      OWL_SCHEM = reader.read();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
