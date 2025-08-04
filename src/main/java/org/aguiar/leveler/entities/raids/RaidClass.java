package org.aguiar.leveler.entities.raids;

public enum RaidClass {
  A, B, C, D, S;

  public static RaidClass fromString(String raidClass) {
    return switch (raidClass.toUpperCase()) {
      case "A" -> A;
      case "B" -> B;
      case "C" -> C;
      case "D" -> D;
      case "S" -> S;
      default -> throw new IllegalArgumentException("Unknown raid class: " + raidClass);
    };
  }
}
