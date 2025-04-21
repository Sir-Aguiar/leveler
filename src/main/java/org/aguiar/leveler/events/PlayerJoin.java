package org.aguiar.leveler.events;

import org.aguiar.leveler.Leveler;
import org.aguiar.leveler.utils.LevelerPlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class PlayerJoin implements Listener {
  private Leveler plugin;

  public PlayerJoin(Leveler leveler) {
    plugin = leveler;
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();

    boolean playerExists = plugin.playersData.get(player.getUniqueId().toString()) != null;

    if (!playerExists) {
      plugin.playersData.put(player.getUniqueId().toString(), new LevelerPlayerData(0.0f, 0.0f));
      plugin.savePlayerData();
    }
  }
}
