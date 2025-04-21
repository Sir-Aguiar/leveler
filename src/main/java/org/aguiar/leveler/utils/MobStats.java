package org.aguiar.leveler.utils;

public class MobStats {
  public static final double ZOMBIE_SOLDIER_HP = 8.0;
  private static final double ZOMBIE_BOSS_HP = 10.0;
  public static final double ZOMBIE_SOLDIER_DAMAGE = 1.50;
  private static final double ZOMBIE_BOSS_DAMAGE = 0.75;

  public static double getScaledZombieSoldierDamage(LevelerPlayerData playerData) {
    return (playerData.getPlayerExperience() / 18) + ZOMBIE_SOLDIER_DAMAGE;
  }

  public static double getScaledZombieBossHp(LevelerPlayerData playerData) {
    return (playerData.getPlayerExperience() / 10) + ZOMBIE_BOSS_HP;
  }

  public static double getScaledZombieBossDamage(LevelerPlayerData playerData) {
    return (playerData.getPlayerExperience() / 15) + ZOMBIE_BOSS_DAMAGE;
  }

  public static double getScaledZombieSoldierHp(LevelerPlayerData playerData) {
    return (playerData.getPlayerExperience() / 12) + ZOMBIE_SOLDIER_HP;
  }

}
