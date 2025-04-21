package org.aguiar.leveler.events;

import org.aguiar.leveler.Leveler;
import org.aguiar.leveler.entities.LevelerPlayerData;
import org.aguiar.leveler.utils.PlayerLevelProgression;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
  private Leveler plugin;

  public PlayerJoin(Leveler leveler) {
    plugin = leveler;
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    LevelerPlayerData playerData = plugin.playersData.computeIfAbsent(player.getUniqueId().toString(), k -> new LevelerPlayerData(0.0f, 0.0f));

    playerData.setPlayerLevel(PlayerLevelProgression.calculatePlayerLevel(playerData.getPlayerExperience()));

    plugin.savePlayerData();
  }
}
