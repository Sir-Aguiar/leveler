package org.aguiar.leveler.entities;

public class LevelerPlayerData {
  private float playerLevel;
  private float playerExperience;

  public LevelerPlayerData(float playerLevel, float playerExperience) {
    this.playerLevel = playerLevel;
    this.playerExperience = playerExperience;
  }

  public LevelerPlayerData() {
  }

  public float getPlayerLevel() {
    return playerLevel;
  }

  public void setPlayerLevel(float playerLevel) {
    this.playerLevel = playerLevel;
  }

  public float getPlayerExperience() {
    return playerExperience;
  }

  public void setPlayerExperience(float playerExperience) {
    this.playerExperience = playerExperience;
  }
}
