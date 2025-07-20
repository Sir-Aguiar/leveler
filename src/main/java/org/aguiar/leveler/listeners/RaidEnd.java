package org.aguiar.leveler.listeners;

import org.aguiar.leveler.Leveler;
import org.aguiar.leveler.entities.dungeons.Dungeon;
import org.aguiar.leveler.events.RaidEndEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RaidEnd implements Listener {
  private final Leveler plugin;

  public RaidEnd(Leveler plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onLevelUp(RaidEndEvent event) {
    Player player = event.getPlayer();

    Dungeon dungeon = event.getDungeon();

    if (dungeon == null) {
      plugin.getLogger().warning("Dungeon is null for player " + player.getName() + " in RaidEndEvent.");
      return;
    }

    dungeon.upgradeRaid();

    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
  }
}
