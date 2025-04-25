package org.aguiar.leveler.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.aguiar.leveler.Leveler;
import org.aguiar.leveler.database.entities.PlayerProgression;
import org.aguiar.leveler.database.repositories.PlayerProgressionRepository;
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

    String zombieType = entity.getMetadata("type").stream().findFirst().map(MetadataValue::asString).orElse("Soldier");
    float experienceFactor = entity.getMetadata("experienceFactor").stream().findFirst().map(MetadataValue::asFloat).orElse(0.84f);

    float playerExp = playerData.getPlayerExperience();
    float playerLevel = playerData.getPlayerLevel();

    float baseExperience = (playerExp / 7.75f) + experienceFactor;
    float newExp = playerExp + baseExperience;

    float experienceForNextLevel = PlayerLevelProgression.experienceForNextLevel((int) playerData.getPlayerLevel());
    float newLevel = PlayerLevelProgression.calculatePlayerLevel(newExp);

    playerData.setPlayerExperience(newExp);
    playerData.setPlayerLevel(newLevel);
    playerData.setSkillPoints(playerData.getSkillPoints() + 1);

    // Just in case the sqlite is blocked by other thread
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

    if ((int) newLevel > (int) playerLevel) {
      Bukkit.getPluginManager().callEvent(new LevelUpEvent(player, playerLevel, newLevel));
    }

    String message = String.format("%s%sPlayer XP: %s%.2f/%.2f", ChatColor.GREEN, ChatColor.BOLD, ChatColor.GOLD, playerData.getPlayerExperience(), experienceForNextLevel);
    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
  }
}
