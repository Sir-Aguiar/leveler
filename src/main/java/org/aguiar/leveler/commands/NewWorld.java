package org.aguiar.leveler.commands;

import org.aguiar.leveler.Leveler;
import org.aguiar.leveler.entities.dungeons.OwlDungeon;
import org.aguiar.leveler.utils.WorldsManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NewWorld implements CommandExecutor {
  private final Leveler plugin;

  public NewWorld(Leveler plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (!(sender instanceof Player player)) {
      return false;
    }

    String worldName = "_raid_" + player.getUniqueId() + System.currentTimeMillis();

    World raidWorld = WorldsManager.createWorld(worldName);
    Location dungeonLocation = new Location(raidWorld, 0, -61, 0);

    if (raidWorld != null) {
      String schemPath = plugin.getDataFolder() + "/schematics/lvl_1_dungeon.schem";
      OwlDungeon owlDungeon = new OwlDungeon("Owl Dungeon", schemPath, dungeonLocation, raidWorld);
      owlDungeon.teleportPlayer(player);
    } else {
      player.sendMessage("§cFalha ao criar a arena da raid!");
    }

    return true;
  }
}
