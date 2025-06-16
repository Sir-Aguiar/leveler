package org.aguiar.leveler.entities.raids;

import org.bukkit.entity.EntityType;

import java.util.Locale;
import java.util.Map;

public class RaidMob {
  private String name;
  private String mobClass;
  private EntityType entityType;
  private Map<String, Double> spawnLocation;
  private Map<String, MobEquipment> equipments;
  private double health;
  private double damage;
  private double xpDrop;

  public RaidMob(String name, String mobClass, String entityType, Map<String, Double> spawnLocation, Map<String, MobEquipment> equipments) {
    this.name = name;
    this.mobClass = mobClass;
    this.entityType = EntityType.valueOf(entityType);
    this.spawnLocation = spawnLocation;
    this.equipments = equipments;
  }

  public RaidMob(String name, String mobClass, String entityType, Map<String, Double> spawnLocation) {
    this.name = name;
    this.mobClass = mobClass;
    this.entityType = EntityType.valueOf(entityType);
    this.spawnLocation = spawnLocation;
  }


  public RaidMob(String name, String mobClass, String entityType, Map<String, Double> spawnLocation, Map<String, MobEquipment> equipments, double health, double damage, double xpDrop) {
    this.name = name;
    this.mobClass = mobClass;
    this.entityType = EntityType.valueOf(entityType.toUpperCase());
    this.spawnLocation = spawnLocation;
    this.equipments = equipments;
    this.health = health;
    this.damage = damage;
    this.xpDrop = xpDrop;
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

  public Map<String, MobEquipment> getEquipments() {
    return equipments;
  }

  public void setEquipments(Map<String, MobEquipment> equipments) {
    this.equipments = equipments;
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
