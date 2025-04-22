package org.aguiar.leveler.listeners;

import org.aguiar.leveler.events.LevelUpEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LevelUpListener implements Listener {
  @EventHandler
  public void onLevelUp(LevelUpEvent event) {
    Player player = event.getPlayer();

    player.sendMessage("Você subiu de nível");
  }
}
