package org.aguiar.leveler.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LevelUpEvent extends Event {
  private static final HandlerList handlers = new HandlerList();

  private final Player player;
  private final float oldLevel;
  private final float newLevel;

  public LevelUpEvent(Player player, float oldLevel, float newLevel) {
    this.player = player;
    this.oldLevel = oldLevel;
    this.newLevel = newLevel;
  }

  public Player getPlayer() {
    return player;
  }

  public float getOldLevel() {
    return oldLevel;
  }

  public float getNewLevel() {
    return newLevel;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}
