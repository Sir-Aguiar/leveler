package org.aguiar.leveler.utils;

public class PlayerLevelProgression {
  private static final double BASE_XP = 250.0; // Base XP for level 1
  private static final double EXPONENT = 1.388; // Exponent for scaling

  /**
   * Calculates the total experience required to reach the *next* level from the current level.
   * This is the value displayed as 'XP needed'.
   *
   * @param currentLevel The player's current level.
   * @return The amount of XP needed to reach the next level.
   */
  public static float experienceForNextLevel(int currentLevel) {
    // XP needed for next level = (Total XP for next level) - (Total XP for current level)
    // Simplified: It's the XP threshold *relative* to the start of the current level.
    // For level 0 -> 1, it's experienceForLevel(1)
    // For level 1 -> 2, it's experienceForLevel(2) - experienceForLevel(1)
    // However, the original formula seems to calculate the *total* XP needed for the next level directly.
    // Let's keep the original formula's intent for now, assuming it represents the total XP needed for level `currentLevel + 1`.
    // If level 0 needs experienceForLevel(1) total XP, then experienceForNextLevel(0) should return experienceForLevel(1).
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
      return 0; // Level 0 requires 0 XP
    }
    // Formula calculates the total XP needed to *attain* the given level.
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
      return 0; // Below level 1 threshold
    }
    // Calculate the level based on the inverse formula
    // We take the floor because you only reach the level once you cross its threshold.
    return (int) Math.floor(Math.pow(totalExperience / BASE_XP, 1.0 / EXPONENT));
  }
}
