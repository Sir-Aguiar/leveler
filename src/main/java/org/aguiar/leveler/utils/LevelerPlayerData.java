package org.aguiar.leveler.utils;

import org.bukkit.entity.Player;

import java.util.UUID;

public class LevelerPlayerData {
  private double playerLevel;
  private float playerExperience;

  public LevelerPlayerData(double playerLevel, float playerExperience) {
    this.playerLevel = playerLevel;
    this.playerExperience = playerExperience;
  }

  public LevelerPlayerData() {
  }

  public double getPlayerLevel() {
    return playerLevel;
  }

  public void setPlayerLevel(double playerLevel) {
    this.playerLevel = playerLevel;
  }

  public float getPlayerExperience() {
    return playerExperience;
  }

  public void setPlayerExperience(float playerExperience) {
    this.playerExperience = playerExperience;
  }
}
