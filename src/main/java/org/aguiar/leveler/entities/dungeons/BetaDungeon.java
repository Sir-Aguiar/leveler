package org.aguiar.leveler.entities.dungeons;

import org.aguiar.leveler.Leveler;
import org.bukkit.entity.Player;

public class BetaDungeon extends Dungeon {
  public BetaDungeon(Leveler plugin, Player player) {
    super("first_dungeon_beta", plugin, player);
  }
}
