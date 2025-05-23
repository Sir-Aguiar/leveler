package org.aguiar.leveler.commands;

import org.aguiar.leveler.Leveler;
import org.aguiar.leveler.entities.dungeons.BetaDungeon;
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

    BetaDungeon betaDungeon = new BetaDungeon(plugin, player);
    betaDungeon.teleportPlayer(player);

    return true;
  }
}
