package org.aguiar.leveler.commands;

import org.aguiar.leveler.Leveler;
import org.aguiar.leveler.database.entities.PlayerProgression;
import org.aguiar.leveler.database.repositories.PlayerProgressionRepository;
import org.aguiar.leveler.entities.RaidZombie;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

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

    PlayerProgression playerData = null;
    PlayerProgressionRepository playerProgressionRepository = new PlayerProgressionRepository(plugin.database.playerProgressionsDAO);

    try {
      playerData = playerProgressionRepository.getById(player.getUniqueId());
    } catch (SQLException e) {
      e.printStackTrace();
    }

    RaidZombie raidZombies = new RaidZombie(plugin, playerData);

    raidZombies.spawnBoss(player.getLocation().add(0, 0, 3));
    raidZombies.spawnSoldier(player.getLocation().add(1, 0, 3));
    raidZombies.spawnSoldier(player.getLocation().add(-1, 0, 3));
    raidZombies.spawnSoldier(player.getLocation().add(2, 0, 2));
    raidZombies.spawnSoldier(player.getLocation().add(-2, 0, 2));

    player.sendMessage("§aRaid iniciada! Cuidado com os zombies!");

    return true;
  }
}
