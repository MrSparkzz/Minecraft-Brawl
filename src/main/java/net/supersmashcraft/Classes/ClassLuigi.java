package net.supersmashcraft.Classes;

import org.bukkit.Color;
import org.bukkit.Material;

public class ClassLuigi extends SSCClass {
   
   public ClassLuigi() {
      super("Luigi", "Your favorite brother!", Material.CLAY_BALL);
      item(Material.BLAZE_ROD);
      armor(Material.LEATHER_HELMET, Color.fromRGB(33, 222, 84));
      armor(Material.LEATHER_CHESTPLATE, Color.fromRGB(33, 222, 84));
      armor(Material.LEATHER_CHESTPLATE, Color.fromRGB(33, 222, 84));
      armor(Material.LEATHER_HELMET, Color.fromRGB(33, 222, 84));
   }
}