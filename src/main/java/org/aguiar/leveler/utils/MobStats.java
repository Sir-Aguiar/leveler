package org.aguiar.leveler.utils;

import org.aguiar.leveler.database.entities.PlayerProgression;

public class MobStats {
  public static final double ZOMBIE_SOLDIER_HP = 8.0;
  public static final double ZOMBIE_SOLDIER_DAMAGE = 1.50;
  private static final double ZOMBIE_BOSS_HP = 10.0;
  private static final double ZOMBIE_BOSS_DAMAGE = 0.75;

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
