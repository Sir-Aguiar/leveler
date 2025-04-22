package org.aguiar.leveler.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
  private final String databasePath;
  private Connection connection;

  public DatabaseManager(String databasePath) {
    this.databasePath = databasePath;
    connect();
  }

  public void connect() {
    try {
      if (connection == null || connection.isClosed()) {
        connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void disconnect() throws SQLException {
    if (connection != null && !connection.isClosed()) {
      connection.close();
    }
  }

  public Connection getConnection() {
    return connection;
  }
}