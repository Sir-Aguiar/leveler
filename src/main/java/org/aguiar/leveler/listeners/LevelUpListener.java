package org.aguiar.leveler.listeners;

import org.aguiar.leveler.Leveler;
import org.aguiar.leveler.events.LevelUpEvent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LevelUpListener implements Listener {
  private final Leveler plugin;

  public LevelUpListener(Leveler plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onLevelUp(LevelUpEvent event) {
    Player player = event.getPlayer();

    float oldLevel = event.getOldLevel();
    float newLevel = event.getNewLevel();

    player.sendTitle(ChatColor.GOLD + "LEVEL UP!", ChatColor.GREEN + "Level " + (int) oldLevel + " → " + (int) newLevel, 10, 70, 20);

    // Play an XP reward sound
    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
  }
}
