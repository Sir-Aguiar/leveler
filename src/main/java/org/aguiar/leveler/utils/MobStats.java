package org.aguiar.leveler.utils;

import org.aguiar.leveler.database.entities.PlayerProgression;
import org.aguiar.leveler.entities.ZombieClasses;

public class MobStats {
  public static final double MOB_BASE_XP = 10.0;
  public static final double XP_POWER = 1.388;
  public static final double XP_CONSTANT = 0.1;

  public static final double ZOMBIE_XP_MULTIPLIER = 1;
  public static final double ZOMBIE_BOSS_XP_MULTIPLIER = 1.15;

  public static final double ZOMBIE_SOLDIER_HP = 8.0;
  public static final double ZOMBIE_SOLDIER_DAMAGE = 0.75;

  private static final double ZOMBIE_BOSS_HP = 10.0;
  private static final double ZOMBIE_BOSS_DAMAGE = 1.0;

  public static double getScaledXP(PlayerProgression playerData, ZombieClasses zombieClass) {
    double playerLevel = playerData.getPlayerLevel();
    double playerExperience = playerData.getPlayerExperience();

    if (playerLevel < 1) playerLevel = 1;
    // Formula: XP_base * Fator_XP(Nivel) * Multiplicador_por_Classe
    // Vamos usar uma formula similar: Constante_XP * (Nivel - 1)^P, ou Constante_XP * Nivel^P
    // Uma forma comum é: XP_base * (1 + Constante_XP * (playerLevel - 1)^P)
    // Onde P pode ser 1.0, 1.1, ou até perto de 1.388 para manter o ritmo.
    // Vamos tentar P=1.0 para simplicidade inicial (escalonamento linear do Fator_XP)
    // Ou P=1.1 ou 1.2 para um crescimento um pouco mais acentuado
    // Ou P=1.388 para tentar alinhar o ritmo de ganho com o custo de nível

    double scalingFactor = 1.0 + XP_CONSTANT * Math.pow(playerLevel - 1, XP_POWER);

    if (playerLevel == 1) scalingFactor = 1.0;

    double xpMultiplier = 1.0;

    if (zombieClass.equals(ZombieClasses.BOSS)) xpMultiplier = 1.15;

    double scaledXP = MOB_BASE_XP * scalingFactor * xpMultiplier;

    return scaledXP;

  }

  public static double getScaledZombieSoldierDamage(PlayerProgression playerData) {
    return Math.min((playerData.getPlayerLevel() * 0.95) + ZOMBIE_SOLDIER_DAMAGE, 50.0f);
  }

  public static double getScaledZombieBossHp(PlayerProgression playerData) {
    return Math.min((playerData.getPlayerExperience() / 10) + ZOMBIE_BOSS_HP, 2048.0f);
  }

  public static double getScaledZombieBossDamage(PlayerProgression playerData) {
    return Math.min((playerData.getPlayerLevel() * 0.85) + ZOMBIE_BOSS_DAMAGE, 50.0f);
  }

  public static double getScaledZombieSoldierHp(PlayerProgression playerData) {
    return Math.min((playerData.getPlayerExperience() / 12) + ZOMBIE_SOLDIER_HP, 2048.0f);
  }

}
