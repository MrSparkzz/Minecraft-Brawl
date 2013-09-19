package net.supersmashcraft.Classes;

import org.bukkit.Color;
import org.bukkit.Material;

public class ClassFox extends SSCClass {
   
   public ClassFox() {
      super("Fox");
      armor(Material.LEATHER_BOOTS, Color.fromRGB(255, 161, 161));
      armor(Material.LEATHER_LEGGINGS, Color.fromRGB(255, 161, 161));
      armor(Material.LEATHER_CHESTPLATE, Color.fromRGB(255, 161, 161));
      armor(Material.LEATHER_HELMET, Color.fromRGB(255, 161, 161));
      item(Material.GHAST_TEAR);
   }
   
}