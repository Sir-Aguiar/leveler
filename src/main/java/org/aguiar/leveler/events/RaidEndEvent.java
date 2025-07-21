package org.aguiar.leveler.events;

import org.aguiar.leveler.entities.dungeons.Dungeon;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class RaidEndEvent extends Event {
  private static final HandlerList handlers = new HandlerList();
  private final Player player;
  private final Dungeon dungeon;


  public RaidEndEvent(Player player, Dungeon dungeon) {
    super();

    this.player = player;
    this.dungeon = dungeon;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  public Player getPlayer() {
    return player;
  }

  public Dungeon getDungeon() {
    return dungeon;
  }

  @Override
  public @NotNull HandlerList getHandlers() {
    return handlers;
  }
}
