package org.aguiar.leveler.events;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.aguiar.leveler.Leveler;
import org.aguiar.leveler.database.entities.PlayerProgression;
import org.aguiar.leveler.database.repositories.PlayerProgressionRepository;
import org.aguiar.leveler.utils.PlayerLevelProgression;
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

    float baseExperience = (playerExp / 7.75f) + experienceFactor;

    playerData.setPlayerExperience(playerExp + baseExperience);
    playerData.setPlayerLevel(PlayerLevelProgression.calculatePlayerLevel(playerExp));

    float experienceForNextLevel = PlayerLevelProgression.experienceForNextLevel((int) playerData.getPlayerLevel());

    // Just in case the sqlite is blocked by other thread

    boolean updated = false;
    int tries = 0;
    SQLException lastException = null;

    while (!updated && tries < 3) {
      try {
        plugin.database.playerProgressionsDAO.update(playerData);
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

    String message = String.format("%s%sPlayer XP: %s%.2f/%.2f", ChatColor.GREEN, ChatColor.BOLD, ChatColor.GOLD, playerExp, experienceForNextLevel);

    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
  }
}
