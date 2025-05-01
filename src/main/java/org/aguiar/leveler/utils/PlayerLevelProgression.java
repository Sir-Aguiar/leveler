package org.aguiar.leveler.utils;

public class PlayerLevelProgression {
  private static final double BASE_XP = 250.0;
  private static final double EXPONENT = 1.388;

  public static float experienceForNextLevel(int currentLevel) {
    return experienceForLevel(currentLevel + 1);
  }

  /**
   * Calculates the *total* cumulative experience required to reach a specific level.
   * Level 0 requires 0 XP. Level 1 requires BASE_XP.
   *
   * @param level The target level.
   * @return The total cumulative XP required to reach that level.
   */
  public static float experienceForLevel(int level) {
    if (level <= 0) {
      return 0;
    }

    return (float) (BASE_XP * Math.pow(level, EXPONENT));
  }

  /**
   * Calculates the player's level based on their total accumulated experience.
   * This is the inverse of experienceForLevel.
   * XP = BASE * level^EXPONENT  =>  level = (XP / BASE)^(1/EXPONENT)
   *
   * @param totalExperience The player's total accumulated experience points.
   * @return The calculated player level.
   */
  public static int calculatePlayerLevel(float totalExperience) {
    if (totalExperience < experienceForLevel(1)) {
      return 0;
    }

    return (int) Math.floor(Math.pow(totalExperience / BASE_XP, 1.0 / EXPONENT));
  }

  public static float experienceForCurrentLevel(int level) {
    if (level == 0) {
      return PlayerLevelProgression.experienceForLevel(1);
    }

    return PlayerLevelProgression.experienceForLevel(level + 1) - PlayerLevelProgression.experienceForLevel(level);
  }
}
