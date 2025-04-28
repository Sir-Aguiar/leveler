package org.aguiar.leveler.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.aguiar.leveler.Leveler;
import org.aguiar.leveler.database.entities.PlayerProgression;
import org.aguiar.leveler.database.repositories.PlayerProgressionRepository;
import org.aguiar.leveler.entities.ZombieClasses;
import org.aguiar.leveler.events.LevelUpEvent;
import org.aguiar.leveler.utils.PlayerLevelProgression;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.MetadataValue;

import java.sql.SQLException;
import java.util.UUID;

public class RaidZombieDeathListener implements Listener {
  private final Leveler plugin;

  public RaidZombieDeathListener(Leveler plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onDeath(EntityDeathEvent event) {
    if (!(event.getEntity() instanceof Zombie entity)) {
      return; // Ignora se não for um Zombie
    }

    if (!entity.hasMetadata("isRaid")) {
      return;
    }

    Player player = entity.getKiller();

    if (player == null) {
      return;
    }

    UUID playerId = player.getUniqueId();

    PlayerProgressionRepository playerProgressionRepository = new PlayerProgressionRepository(plugin.database.playerProgressionsDAO);
    PlayerProgression playerData = null;

    try {
      playerData = playerProgressionRepository.getById(playerId);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    if (playerData == null) {
      player.sendMessage(ChatColor.RED + "Erro: Dados do jogador não encontrados.");
      return;
    }

    String zombieType = entity.getMetadata("type").stream().findFirst().map(MetadataValue::asString).orElse(ZombieClasses.SOLDIER.toString());
    float xpDrop = entity.getMetadata("xpDrop").stream().findFirst().map(MetadataValue::asFloat).orElse(0.0f);

    float playerExp = playerData.getPlayerExperience(); // This is XP *within* the current level
    int playerLevel = playerData.getPlayerLevel();
    float totalCurrentExp = PlayerLevelProgression.experienceForLevel(playerLevel) + playerExp; // Calculate total accumulated XP

    float totalNewExp = totalCurrentExp + xpDrop; // Total accumulated XP after kill

    int originalLevel = playerLevel; // Store original level for comparison
    int newLevel = PlayerLevelProgression.calculatePlayerLevel(totalNewExp); // Calculate potential new level

    if (newLevel > originalLevel) {
      float xpRequiredForNewLevel = PlayerLevelProgression.experienceForLevel(newLevel); // Get total XP needed to reach this new level
      float remainingExp = totalNewExp - xpRequiredForNewLevel; // Calculate XP progress *into* the new level

      playerData.setPlayerLevel(newLevel);
      playerData.setPlayerExperience(remainingExp); // Store the XP progress for the new level
      playerData.setSkillPoints(playerData.getSkillPoints() + (newLevel - originalLevel)); // Grant skill points for levels gained
      Bukkit.getPluginManager().callEvent(new LevelUpEvent(player, originalLevel, newLevel)); // Fire the level up event
    } else {
      // Did not level up, just add the XP drop to the current level's progress
      playerData.setPlayerExperience(playerExp + xpDrop);
    }

    boolean updated = false;
    int tries = 0;
    SQLException lastException = null;

    while (!updated && tries < 3) {
      try {
        playerProgressionRepository.update(playerData);
        updated = true;
      } catch (SQLException e) {
        lastException = e;
        if (e.getCause() instanceof org.sqlite.SQLiteException) {
          tries++;

          try {
            Thread.sleep(100L * tries);
          } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
          }
        }
      }
    }

    if (!updated) {
      plugin.getLogger().warning("Falha ao atualizar progresso após " + tries + " tries");
      plugin.getLogger().warning(lastException.getMessage());
    }

    // Recalculate experienceForNextLevel based on the potentially updated player level for the action bar message
    // Note: experienceForNextLevel now correctly represents the *total* XP needed for the next level.
    // We need the XP required *within* the current level for the display.
    float xpNeededForCurrentLevel = PlayerLevelProgression.experienceForLevel(playerData.getPlayerLevel() + 1) - PlayerLevelProgression.experienceForLevel(playerData.getPlayerLevel());
    if (playerData.getPlayerLevel() == 0) {
        xpNeededForCurrentLevel = PlayerLevelProgression.experienceForLevel(1); // Special case for level 0
    }

    String message = String.format("%s%sPlayer XP: %s%.2f/%.2f", ChatColor.GREEN, ChatColor.BOLD, ChatColor.GOLD, playerData.getPlayerExperience(), xpNeededForCurrentLevel);
    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
  }
}
