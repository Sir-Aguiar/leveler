package org.aguiar.leveler.utils;

public class PlayerLevelProgression {
  private static final double PROGRESSION_FACTOR = 250.0;

  public static float calculatePlayerLevel(float playerExperience) {
    float level = (float) Math.pow(playerExperience / PROGRESSION_FACTOR, 1 / 1.388);

    System.out.println("PLAYER LEVEL: " + level + "\n");

    return Math.max(1, level);
  }

  public static float experienceForNextLevel(int currentLevel) {
    return (float) (PROGRESSION_FACTOR * Math.pow(currentLevel, 1.388));
  }
}