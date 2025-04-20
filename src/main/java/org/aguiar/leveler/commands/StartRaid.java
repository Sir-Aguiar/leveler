package org.aguiar.leveler.commands;

import org.aguiar.leveler.entities.RaidZombie;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class StartRaid implements CommandExecutor {
  private final Plugin plugin;

  public StartRaid(Plugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
    if (!(sender instanceof Player player)) {
      sender.sendMessage("This command is for players only");
      return false;
    }

    RaidZombie raidZombies = new RaidZombie(plugin);
    raidZombies.spawnBoss(player.getLocation().add(0, 0, 3));
    raidZombies.spawnWorker(player.getLocation().add(1, 0, 3));
    raidZombies.spawnWorker(player.getLocation().add(-1, 0, 3));
    raidZombies.spawnWorker(player.getLocation().add(2, 0, 2));
    raidZombies.spawnWorker(player.getLocation().add(-2, 0, 2));

    player.sendMessage("§aRaid iniciada! Cuidado com os zombies!");


    return true;
  }
}
