package org.aguiar.leveler.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.aguiar.leveler.Leveler;
import org.aguiar.leveler.database.entities.PlayerProgression;
import org.aguiar.leveler.database.repositories.PlayerProgressionRepository;
import org.aguiar.leveler.entities.MobClass;
import org.aguiar.leveler.entities.ZombieClasses;
import org.aguiar.leveler.events.LevelUpEvent;
import org.aguiar.leveler.utils.PlayerLevelProgression;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.MetadataValue;

import java.sql.SQLException;
import java.util.UUID;

public class EntityDeath implements Listener {
  private final Leveler plugin;

  public EntityDeath(Leveler plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onRaidEntityDeath(EntityDeathEvent event) {
    if (!(event.getEntity() instanceof Monster entity)) {
      return;
    }

    boolean hasRequiredMeta = entity.hasMetadata("isRaid") && entity.hasMetadata("type") && entity.hasMetadata("xpDrop");

    if (!hasRequiredMeta) {
      return;
    }

    Player player = entity.getKiller();
    assert player != null;

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

    String mobClass = entity.getMetadata("type").stream().findFirst().map(MetadataValue::asString).orElse(MobClass.SOLDIER.toString());
    float xpDrop = entity.getMetadata("xpDrop").stream().findFirst().map(MetadataValue::asFloat).orElse(0.0f);

    float playerExp = playerData.getPlayerExperience();
    int playerLevel = playerData.getPlayerLevel();
    float totalCurrentExp = PlayerLevelProgression.experienceForLevel(playerLevel) + playerExp;

    float totalNewExp = totalCurrentExp + xpDrop;

    int newLevel = PlayerLevelProgression.calculatePlayerLevel(totalNewExp);

    if (newLevel > playerLevel) {
      float xpRequiredForNewLevel = PlayerLevelProgression.experienceForLevel(newLevel);
      float remainingExp = totalNewExp - xpRequiredForNewLevel;

      playerData.setPlayerLevel(newLevel);
      playerData.setPlayerExperience(remainingExp);
      playerData.setSkillPoints(playerData.getSkillPoints() + (newLevel - playerLevel));

      Bukkit.getPluginManager().callEvent(new LevelUpEvent(player, playerLevel, newLevel));
    } else {
      playerData.setPlayerExperience(playerExp + xpDrop);
    }

    try {
      playerProgressionRepository.update(playerData);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    float xpNeededForCurrentLevel = PlayerLevelProgression.experienceForCurrentLevel(playerData.getPlayerLevel());
    String message = String.format("%s%sPlayer XP: %s%.2f/%.2f", ChatColor.GREEN, ChatColor.BOLD, ChatColor.GOLD, playerData.getPlayerExperience(), xpNeededForCurrentLevel);
    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
  }
}
