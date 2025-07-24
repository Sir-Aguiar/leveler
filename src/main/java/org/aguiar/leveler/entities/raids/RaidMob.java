package org.aguiar.leveler.entities.raids;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.EquipmentSlot;

import java.util.*;

public class RaidMob {
  private String name;
  private String mobClass;
  private EntityType entityType;
  private Map<String, Double> spawnLocation;
  private Map<EquipmentSlot, MobEquipment> equipments = new HashMap<>();
  private double health;
  private double damage;
  private double xpDrop;

  public RaidMob(String name, String mobClass, String entityType, Map<String, Double> spawnLocation) {
    this.name = name;
    this.mobClass = mobClass;
    this.entityType = EntityType.valueOf(entityType);
    this.spawnLocation = spawnLocation;
  }

  public RaidMob(String name, String mobClass, String entityType, Map<String, Double> spawnLocation, double health, double damage, double xpDrop) {
    this.name = name;
    this.mobClass = mobClass;
    this.entityType = EntityType.valueOf(entityType.toUpperCase());
    this.spawnLocation = spawnLocation;
    this.health = health;
    this.damage = damage;
    this.xpDrop = xpDrop;
  }

  public void loadEquipments(Map<String, Object> equipmentMap) {
    if (equipmentMap == null) {
      System.out.println("[RaidMob] ERRO: equipmentMap é nulo!");
      return;
    }

    System.out.println("[RaidMob] Iniciando carregamento de equipamentos para mob: " + name);
    System.out.println("[RaidMob] Total de slots a processar: " + equipmentMap.size());

    for (Map.Entry<String, Object> entry : equipmentMap.entrySet()) {
      // Slot
      String slotString = entry.getKey();
      System.out.println("[RaidMob] Processando slot: " + slotString);

      // Details
      Map<String, Object> equipmentDetails = (Map<String, Object>) entry.getValue();

      String materialString = (String) equipmentDetails.get("material");
      System.out.println("[RaidMob] - Material: " + materialString);

      Integer amount = (Integer) equipmentDetails.get("amount");
      System.out.println("[RaidMob] - Quantidade: " + amount);

      String itemName = ChatColor.translateAlternateColorCodes('&', String.valueOf(equipmentDetails.get("name")));
      System.out.println("[RaidMob] - Nome do item: " + itemName);

      // Lore
      List<String> lore = new ArrayList<>();
      List<String> rawLore = (List<String>) equipmentDetails.get("lore");
      for (String line : rawLore) {
        String coloredLine = ChatColor.translateAlternateColorCodes('&', line);
        lore.add(coloredLine);
        System.out.println("[RaidMob] * Linha de lore: " + coloredLine);
      }

      // Enchantments
      Map<Enchantment, Integer> enchantments = new HashMap<>();
      Map<String, Integer> enchantMap = (Map<String, Integer>) equipmentDetails.get("enchantments");

      for (Map.Entry<String, Integer> enchantEntry : enchantMap.entrySet()) {
        String enchantName = enchantEntry.getKey();
        Integer enchantLevel = enchantEntry.getValue();

        Enchantment enchantment = Enchantment.getByName(enchantName.toUpperCase());
        enchantments.put(enchantment, enchantLevel);
        System.out.println("[RaidMob]   * Encantamento: " + enchantment.getName() + " (Nivel " + enchantLevel + ")");
      }

      EquipmentSlot slot = EquipmentSlot.valueOf(slotString.toUpperCase());
      equipments.put(slot, new MobEquipment(Material.getMaterial(materialString), itemName, amount, lore, enchantments));

      System.out.println("[RaidMob] Equipamento adicionado com sucesso para o slot: " + slot);
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMobClass() {
    return mobClass;
  }

  public void setMobClass(String mobClass) {
    this.mobClass = mobClass;
  }

  public EntityType getEntityType() {
    return entityType;
  }

  public void setEntityType(EntityType entityType) {
    this.entityType = entityType;
  }

  public Map<String, Double> getSpawnLocation() {
    return spawnLocation;
  }

  public void setSpawnLocation(Map<String, Double> spawnLocation) {
    this.spawnLocation = spawnLocation;
  }

  public Map<EquipmentSlot, MobEquipment> getEquipments() {
    return equipments;
  }

  public double getHealth() {
    return health;
  }

  public void setHealth(double health) {
    this.health = health;
  }

  public double getDamage() {
    return damage;
  }

  public void setDamage(double damage) {
    this.damage = damage;
  }

  public double getXpDrop() {
    return xpDrop;
  }

  public void setXpDrop(double xpDrop) {
    this.xpDrop = xpDrop;
  }
}
