package org.aguiar.leveler.entities.dungeons;

import org.aguiar.leveler.Leveler;
import org.aguiar.leveler.database.entities.PlayerProgression;
import org.aguiar.leveler.database.repositories.PlayerProgressionRepository;
import org.aguiar.leveler.entities.raids.Raid;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BetaDungeon extends Dungeon {
  private final Raid levelOneRaid;

  public BetaDungeon(Leveler plugin, Player player) {
    super("first_dungeon_beta", plugin, player);

    List<UUID> playersInDungeon = player.getWorld().getPlayers().stream().map(Player::getUniqueId).toList();
    List<PlayerProgression> playersData = new ArrayList<>();
    PlayerProgressionRepository playerProgressionRepository = new PlayerProgressionRepository(plugin.database.playerProgressionsDAO);

    playersInDungeon.forEach(playerId -> {
      try {
        playersData.add(playerProgressionRepository.getById(playerId));
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });

    levelOneRaid = new Raid(plugin, playersData);
  }

  public Raid getLevelOneRaid() {
    return this.levelOneRaid;
  }
}
