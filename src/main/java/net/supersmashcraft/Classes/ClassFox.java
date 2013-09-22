package net.supersmashcraft.Classes;

import org.bukkit.Color;
import org.bukkit.Material;

public class ClassFox extends SSCClass {
   
   public ClassFox() {
      super("Fox", "&7Fox fox fox.. flash!", Material.PORTAL);
      armor(Material.LEATHER_BOOTS, Color.fromRGB(247, 243, 10));
      armor(Material.LEATHER_LEGGINGS, Color.fromRGB(255, 255, 255));
      armor(Material.LEATHER_CHESTPLATE, Color.fromRGB(53, 191, 40));
      armor(Material.LEATHER_HELMET, Color.fromRGB(255, 255, 255));
      item(Material.GHAST_TEAR);
      // I have to change items and armor color still
      // Fox has two events, ask on skype if your finishing fox.
      // ~Max
   }
}
