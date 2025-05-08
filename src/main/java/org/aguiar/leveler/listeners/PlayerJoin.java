package org.aguiar.leveler.listeners;

import org.aguiar.leveler.Leveler;
import org.aguiar.leveler.database.entities.PlayerAttributes;
import org.aguiar.leveler.database.entities.PlayerProgression;
import org.aguiar.leveler.database.repositories.PlayerAttributesRepository;
import org.aguiar.leveler.database.repositories.PlayerProgressionRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class PlayerJoin implements Listener {
  private final Leveler plugin;

  public PlayerJoin(Leveler leveler) {
    plugin = leveler;
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    PlayerProgression playerProgression = null;
    PlayerAttributes playerAttributes = null;

    PlayerProgressionRepository playerProgressionRepository = new PlayerProgressionRepository(plugin.database.playerProgressionsDAO);
    PlayerAttributesRepository playerAttributesRepository = new PlayerAttributesRepository(plugin.database.playerAttributesDAO);

    try {
      playerProgression = playerProgressionRepository.getById(player.getUniqueId());
      playerAttributes = playerAttributesRepository.getById(player.getUniqueId());
    } catch (SQLException e) {
      e.printStackTrace();
    }

    if (playerAttributes == null) {
      try {
        playerAttributesRepository.setDefaultAttributes(player.getUniqueId().toString(), player.getName());
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    if (playerProgression == null) {
      try {

        playerProgressionRepository.setDefaultProgression(player.getUniqueId());
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
