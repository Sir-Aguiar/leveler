package org.aguiar.leveler.database.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "player_attributes")
public class PlayerAttributes {
  @DatabaseField(id = true)
  private String UUID;

  @DatabaseField()
  private String username;

  @DatabaseField(defaultValue = "0")
  private int speed;

  @DatabaseField(defaultValue = "0")
  private int strength;

  public PlayerAttributes() {
  }

  public PlayerAttributes(String UUID, String username, int speed, int strength) {
    this.UUID = UUID;
    this.username = username;
    this.speed = speed;
    this.strength = strength;
  }

  public PlayerAttributes(String UUID, String username) {
    this.UUID = UUID;
    this.username = username;
    speed = 0;
    strength = 0;
  }

  public String getUUID() {
    return UUID;
  }

  public void setUUID(String UUID) {
    this.UUID = UUID;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public int getStrength() {
    return strength;
  }

  public void setStrength(int strength) {
    this.strength = strength;
  }
}
