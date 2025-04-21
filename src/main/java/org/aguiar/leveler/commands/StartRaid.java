package org.aguiar.leveler.commands;

import org.aguiar.leveler.Leveler;
import org.aguiar.leveler.entities.RaidZombie;
import org.aguiar.leveler.utils.LevelerPlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartRaid implements CommandExecutor {
  private final Leveler plugin;

  public StartRaid(Leveler plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
    if (!(sender instanceof Player player)) {
      sender.sendMessage("This command is for players only");
      return false;
    }

    LevelerPlayerData playerData = plugin.playersData.get(player.getUniqueId().toString());
    RaidZombie raidZombies = new RaidZombie(plugin, playerData);

    raidZombies.spawnBoss(player.getLocation().add(0, 0, 3));
    raidZombies.spawnWorker(player.getLocation().add(1, 0, 3));
    raidZombies.spawnWorker(player.getLocation().add(-1, 0, 3));
    raidZombies.spawnWorker(player.getLocation().add(2, 0, 2));
    raidZombies.spawnWorker(player.getLocation().add(-2, 0, 2));

    player.sendMessage("§aRaid iniciada! Cuidado com os zombies!");

    return true;
  }
}
