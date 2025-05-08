package org.aguiar.leveler.database.repositories;

import com.j256.ormlite.dao.Dao;
import org.aguiar.leveler.database.entities.PlayerProgression;

import java.sql.SQLException;
import java.util.UUID;

public class PlayerProgressionRepository {
  private final Dao<PlayerProgression, String> DAO;

  public PlayerProgressionRepository(Dao<PlayerProgression, String> DAO) {
    this.DAO = DAO;
  }

  public PlayerProgression getById(UUID playerId) throws SQLException {
    return DAO.queryForId(playerId.toString());
  }

  public void update(PlayerProgression playerProgression) throws SQLException {
    DAO.update(playerProgression);
  }

  public void setDefaultProgression(UUID playerId) throws SQLException {
    PlayerProgression playerProgression = new PlayerProgression(playerId.toString());
    DAO.create(playerProgression);
  }
}
