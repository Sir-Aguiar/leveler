package org.aguiar.leveler.utils;

import org.aguiar.leveler.database.entities.PlayerProgression;
import org.aguiar.leveler.entities.ZombieClasses;

public class MobStats {
  public static final double MOB_BASE_XP = 10.0;

  public static final double ZOMBIE_BASE_XP_MULTIPLIER = 1;
  public static final double ZOMBIE_BOSS_BASE_XP_MULTIPLIER = 1.15;

  public static final double ZOMBIE_SOLDIER_HP = 8.0;
  public static final double ZOMBIE_SOLDIER_DAMAGE = 1.50;

  private static final double ZOMBIE_BOSS_HP = 10.0;
  private static final double ZOMBIE_BOSS_DAMAGE = 0.75;

  public static double getScaledXP(PlayerProgression playerData, ZombieClasses zombieClass) {
    double playerLevel = playerData.getPlayerLevel();
    double playerExperience = playerData.getPlayerExperience();

    if (zombieClass == ZombieClasses.BOSS) {
      return (playerLevel * ZOMBIE_BOSS_BASE_XP_MULTIPLIER) + (playerExperience / 10) + MOB_BASE_XP;
    }

    return MOB_BASE_XP;
  }

  public static double getScaledZombieSoldierDamage(PlayerProgression playerData) {
    return Math.min((playerData.getPlayerExperience() / 10) + ZOMBIE_SOLDIER_DAMAGE, 50.0f);
  }

  public static double getScaledZombieBossHp(PlayerProgression playerData) {
    return Math.min((playerData.getPlayerExperience() / 10) + ZOMBIE_BOSS_HP, 2048.0f);
  }

  public static double getScaledZombieBossDamage(PlayerProgression playerData) {
    return Math.min((playerData.getPlayerExperience() / 10) + ZOMBIE_BOSS_DAMAGE, 50.0f);
  }

  public static double getScaledZombieSoldierHp(PlayerProgression playerData) {
    return Math.min((playerData.getPlayerExperience() / 12) + ZOMBIE_SOLDIER_HP, 2048.0f);
  }

}
