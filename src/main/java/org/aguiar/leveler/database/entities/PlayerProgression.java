package org.aguiar.leveler.database.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "player_progression")
public class PlayerProgression {
  @DatabaseField(id = true)
  private String playerId;

  @DatabaseField(defaultValue = "1")
  private int playerLevel;

  @DatabaseField(defaultValue = "0")
  private float playerExperience;

  @DatabaseField(defaultValue = "0")
  private int skillPoints;

  @DatabaseField(defaultValue = "0")
  private float rankProgression;

  @DatabaseField(defaultValue = "C")
  private char rank;

  public PlayerProgression(String playerId) {
    this.playerId = playerId;
    this.playerLevel = 1;
    this.playerExperience = 0.0f;
    this.skillPoints = 0;
    this.rankProgression = 0.0f;
    this.rank = 'C';
  }

  public float getRankProgression() {
    return rankProgression;
  }

  public char getRank() {
    return rank;
  }

  public PlayerProgression() {
  }

  public String getPlayerId() {
    return playerId;
  }

  public void setPlayerId(String playerId) {
    this.playerId = playerId;
  }

  public int getPlayerLevel() {
    return playerLevel;
  }

  public void setPlayerLevel(int playerLevel) {
    this.playerLevel = playerLevel;
  }

  public float getPlayerExperience() {
    return playerExperience;
  }

  public void setPlayerExperience(float playerExperience) {
    this.playerExperience = playerExperience;
  }

  public int getSkillPoints() {
    return skillPoints;
  }

  public void setSkillPoints(int skillPoints) {
    this.skillPoints = skillPoints;
  }

}
