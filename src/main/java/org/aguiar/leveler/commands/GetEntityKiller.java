package org.aguiar.leveler.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.UUID;

public class GetEntityKiller implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
    if (!(sender instanceof Player player)) {
      return false;
    }

    double ITEM_DAMAGE = 10000.0;

    ItemStack itemStack = new ItemStack(Material.BONE, 1);
    ItemMeta itemMeta = itemStack.getItemMeta();

    assert itemMeta != null;

    itemMeta.setDisplayName(ChatColor.RED + "Osso Matador");
    itemMeta.setLore(Arrays.asList(
            ChatColor.DARK_RED + "Destrói qualquer entidade com um golpe",
            ChatColor.GRAY + "Dano: " + ChatColor.RED + ITEM_DAMAGE
    ));

    AttributeModifier damageModifier = new AttributeModifier(
            UUID.randomUUID(),
            Attribute.GENERIC_ATTACK_DAMAGE.toString(),
            ITEM_DAMAGE,
            AttributeModifier.Operation.ADD_NUMBER,
            EquipmentSlot.HAND
    );

    itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, damageModifier);

    itemStack.setItemMeta(itemMeta);

    player.getInventory().addItem(itemStack);

    return true;
  }
}
