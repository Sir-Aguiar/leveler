package org.aguiar.leveler.utils;

public class PlayerLevelProgression {
  private static final double PROGRESSION_FACTOR = 100.0;

  /**
   * Calcula o nível do jogador com base na experiência acumulada.
   *
   * @param playerExperience Experiência total do jogador.
   * @return Nível do jogador.
   */
  public static float calculatePlayerLevel(float playerExperience) {
    return (float) Math.sqrt(playerExperience / PROGRESSION_FACTOR);
  }

  /**
   * Calcula a experiência necessária para alcançar o próximo nível.
   *
   * @param currentLevel Nível atual do jogador.
   * @return Experiência necessária para o próximo nível.
   */
  public static float experienceForNextLevel(int currentLevel) {
    return (float) (PROGRESSION_FACTOR * Math.pow(currentLevel + 1, 2));
  }
}