package org.infernogames.mb.Classes;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ClassBowser extends SSCClass {
   
   public ClassBowser() {
      super("Bowser", "&4Your favorite spiked friend!", Material.CACTUS);
      item(Material.CACTUS);
      ItemStack helm = colorArmor(Material.LEATHER_HELMET, Color.fromRGB(110, 80, 0));
      ItemStack chest = colorArmor(Material.LEATHER_CHESTPLATE, Color.fromRGB(48, 110, 0));
      ItemStack leg = colorArmor(Material.LEATHER_LEGGINGS, Color.fromRGB(48, 110, 0));
      ItemStack boot = colorArmor(Material.LEATHER_BOOTS, Color.fromRGB(110, 80, 0));
      
      helm.addEnchantment(Enchantment.THORNS, 1);
      chest.addUnsafeEnchantment(Enchantment.THORNS, 4);
      leg.addUnsafeEnchantment(Enchantment.THORNS, 4);
      boot.addEnchantment(Enchantment.THORNS, 1);
      this.addItem(new ItemStack[] { helm, chest, leg, boot });
   }
}