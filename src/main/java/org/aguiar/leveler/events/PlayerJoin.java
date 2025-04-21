package org.aguiar.leveler.events;

import org.aguiar.leveler.Leveler;
import org.aguiar.leveler.entities.LevelerPlayerData;
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
    if (!plugin.playersData.containsKey(player.getUniqueId().toString())) {
      LevelerPlayerData newPlayerData = new LevelerPlayerData(0.0f, 0.0f);
      plugin.playersData.put(player.getUniqueId().toString(), newPlayerData);
      plugin.savePlayerData();
    }
  }
}
