package org.aguiar.leveler.database.repositories;

import com.j256.ormlite.dao.Dao;
import org.aguiar.leveler.database.entities.PlayerAttributes;

import java.sql.SQLException;
import java.util.UUID;

public class PlayerAttributesRepository {
  private final Dao<PlayerAttributes, String> DAO;

  public PlayerAttributesRepository(Dao<PlayerAttributes, String> DAO) {
    this.DAO = DAO;
  }

  public PlayerAttributes getById(UUID playerId) throws SQLException {
    return DAO.queryForId(playerId.toString());
  }

  public void setDefaultAttributes(String playerId, String username) throws SQLException {
    PlayerAttributes playerAttributes = new PlayerAttributes(playerId, username);
    DAO.create(playerAttributes);
  }

}
