package org.infernogames.mb.Classes;

import org.bukkit.Color;
import org.bukkit.Material;

public class ClassLink extends SSCClass {
   
   public ClassLink() {
      super("Link", "The tri-force..", Material.BOW);
      armor(Material.LEATHER_BOOTS, Color.fromRGB(255, 161, 161));
      armor(Material.LEATHER_LEGGINGS, Color.fromRGB(255, 161, 161));
      armor(Material.LEATHER_CHESTPLATE, Color.fromRGB(255, 161, 161));
      armor(Material.LEATHER_HELMET, Color.fromRGB(255, 161, 161));
      item(Material.IRON_SWORD);
      // I have to fix armor colors.
   }
   
}