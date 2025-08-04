package org.aguiar.leveler.entities.loot;

import org.bukkit.Material;


/*
  Raids tem níveis de dificuldade
  Níveis de dificuldade afetam o loot associado
  Loots externos podem ser associados a raids
  Algoritmo de seleção de loot baseado em chance e raridade de loot, seja ele exeterno ou interno
*/

public abstract class Loot {
  private String lootId;
  private LootClass lootClass;
  private LootRarity lootRarity;
  private String lootLore;
  private String lootName;
  private Double lootChance;
  private Material material;
  private Integer minAmount;
  private Integer maxAmount;

  private boolean canRepeat;

  public String getLootId() {
    return lootId;
  }

  public void setLootId(String lootId) {
    this.lootId = lootId;
  }

  public LootClass getLootClass() {
    return lootClass;
  }

  public void setLootClass(LootClass lootClass) {
    this.lootClass = lootClass;
  }

  public LootRarity getLootRarity() {
    return lootRarity;
  }

  public void setLootRarity(LootRarity lootRarity) {
    this.lootRarity = lootRarity;
  }

  public String getLootLore() {
    return lootLore;
  }

  public void setLootLore(String lootLore) {
    this.lootLore = lootLore;
  }

  public String getLootName() {
    return lootName;
  }

  public void setLootName(String lootName) {
    this.lootName = lootName;
  }

  public Double getLootChance() {
    return lootChance;
  }

  public void setLootChance(Double lootChance) {
    this.lootChance = lootChance;
  }

  public Material getMaterial() {
    return material;
  }

  public void setMaterial(Material material) {
    this.material = material;
  }

  public Integer getMinAmount() {
    return minAmount;
  }

  public void setMinAmount(Integer minAmount) {
    this.minAmount = minAmount;
  }

  public Integer getMaxAmount() {
    return maxAmount;
  }

  public void setMaxAmount(Integer maxAmount) {
    this.maxAmount = maxAmount;
  }

  public boolean isCanRepeat() {
    return canRepeat;
  }

  public void setCanRepeat(boolean canRepeat) {
    this.canRepeat = canRepeat;
  }
}
