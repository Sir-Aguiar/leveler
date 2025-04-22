package org.aguiar.leveler.database.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "player_progression")
public class PlayerProgression {
  @DatabaseField(id = true)
  private String playerId;

  @DatabaseField(defaultValue = "0")
  private float playerLevel;

  @DatabaseField(defaultValue = "0")
  private float playerExperience;

  @DatabaseField(defaultValue = "0")
  private int skillPoints;

  public PlayerProgression(String playerId) {
    this.playerId = playerId;
  }

  public String getPlayerId() {
    return playerId;
  }

  public float getPlayerLevel() {
    return playerLevel;
  }

  public float getPlayerExperience() {
    return playerExperience;
  }

  public int getSkillPoints() {
    return skillPoints;
  }

  public PlayerProgression() {
  }

  public void setPlayerId(String playerId) {
    this.playerId = playerId;
  }

  public void setPlayerLevel(float playerLevel) {
    this.playerLevel = playerLevel;
  }

  public void setPlayerExperience(float playerExperience) {
    this.playerExperience = playerExperience;
  }

  public void setSkillPoints(int skillPoints) {
    this.skillPoints = skillPoints;
  }

}
