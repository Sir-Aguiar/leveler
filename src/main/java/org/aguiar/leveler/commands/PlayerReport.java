package org.aguiar.leveler.commands;

import org.aguiar.leveler.Leveler;
import org.aguiar.leveler.database.entities.PlayerProgression;
import org.aguiar.leveler.database.repositories.PlayerProgressionRepository;
import org.aguiar.leveler.utils.PlayerLevelProgression;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerReport implements CommandExecutor {
  Leveler plugin;

  public PlayerReport(Leveler plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
    if (!(sender instanceof Player player)) {
      return false;
    }

    // Access player data repository
    PlayerProgressionRepository playerProgressionRepository = new PlayerProgressionRepository(plugin.database.playerProgressionsDAO);

    try {
      // Get player data from database
      PlayerProgression playerData = playerProgressionRepository.getById(player.getUniqueId());

      if (playerData == null) {
        player.sendMessage(ChatColor.RED + "Your player data was not found. Please contact an administrator.");
        return true;
      }

      // Get player stats
      float playerExp = playerData.getPlayerExperience();
      float playerLevel = playerData.getPlayerLevel();
      int skillPoints = playerData.getSkillPoints();

      // Calculate XP needed for next level
      float experienceForNextLevel = PlayerLevelProgression.experienceForNextLevel((int) playerLevel);

      AtomicInteger lineLength = new AtomicInteger();

      String level = ChatColor.YELLOW + "Level: " + ChatColor.GREEN + (int) playerLevel + "\n";
      String experience = ChatColor.YELLOW + "Experience: " + ChatColor.GREEN + playerExp + " / " + (experienceForNextLevel + playerExp) + "\n";
      String skill = ChatColor.YELLOW + "Skill Points: " + ChatColor.GREEN + skillPoints + "\n";

      String message = level + experience + skill;

      message.lines().forEach(line -> {
        if (line.length() > lineLength.get()) {
          lineLength.set(line.length());
        }
      });

      StringBuilder lines = new StringBuilder();

      for (int index = 0; index < lineLength.get(); index++) {
        lines.append("=");
      }

      player.sendMessage(ChatColor.YELLOW + lines.toString());
      player.sendMessage(message);
      player.sendMessage(ChatColor.YELLOW + lines.toString());

      return true;
    } catch (SQLException e) {
      player.sendMessage(ChatColor.RED + "An error occurred while retrieving your data.");
      e.printStackTrace();
      return false;
    }
  }
}
