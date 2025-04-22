package org.aguiar.leveler.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.aguiar.leveler.database.entities.PlayerAttributes;
import org.aguiar.leveler.database.entities.PlayerProgression;

import java.sql.SQLException;

public class Database {
  public final ConnectionSource connectionSource;

  public final Dao<PlayerAttributes, String> playerAttributesDAO;
  public final Dao<PlayerProgression, String> playerProgressionsDAO;

  public Database(String path) throws SQLException {
    connectionSource = new JdbcConnectionSource("jdbc:sqlite:" + path);

    TableUtils.createTableIfNotExists(connectionSource, PlayerAttributes.class);
    TableUtils.createTableIfNotExists(connectionSource, PlayerProgression.class);

    playerAttributesDAO = DaoManager.createDao(connectionSource, PlayerAttributes.class);
    playerProgressionsDAO = DaoManager.createDao(connectionSource, PlayerProgression.class);
  }

}
