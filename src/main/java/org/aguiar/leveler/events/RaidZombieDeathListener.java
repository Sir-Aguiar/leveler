package org.aguiar.leveler.events;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.aguiar.leveler.Leveler;
import org.aguiar.leveler.entities.LevelerPlayerData;
import org.aguiar.leveler.utils.PlayerLevelProgression;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.MetadataValue;

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

    String playerId = player.getUniqueId().toString();
    LevelerPlayerData playerData = plugin.playersData.get(playerId);


    if (playerData == null) {
      player.sendMessage(ChatColor.RED + "Erro: Dados do jogador não encontrados.");
      System.out.println(plugin.playersData.toString());
      return; // Ou crie uma entrada padrão, se necessário
    }

    String zombieType = entity.getMetadata("type").stream().findFirst().map(MetadataValue::asString).orElse("Soldier");
    float experienceFactor = entity.getMetadata("experienceFactor").stream().findFirst().map(MetadataValue::asFloat).orElse(0.84f);
    float playerExp = playerData.getPlayerExperience();

    float baseExperience = (playerExp / 7.75f) + experienceFactor;
    playerData.setPlayerExperience(playerExp + baseExperience);
    playerData.setPlayerLevel(PlayerLevelProgression.calculatePlayerLevel(playerExp));
    float experienceForNextLevel = PlayerLevelProgression.experienceForNextLevel((int) playerData.getPlayerLevel());

    plugin.savePlayerData();

    String message = String.format("%s%sPlayer XP: %s%.2f/%2.f", ChatColor.GREEN, ChatColor.BOLD, ChatColor.GOLD, playerExp, experienceForNextLevel);

    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
  }
}
