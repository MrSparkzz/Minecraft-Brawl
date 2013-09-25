package org.infernogames.mb.Classes;


import org.bukkit.Color;
import org.bukkit.Material;
import org.infernogames.mb.Abilities.AbilityFlash;

public class ClassFox extends SSCClass {
   
   public ClassFox() {
      super("Fox", "&7Fox fox fox.. flash!", Material.PORTAL);
      armor(Material.LEATHER_BOOTS, Color.fromRGB(247, 243, 10));
      armor(Material.LEATHER_LEGGINGS, Color.fromRGB(255, 255, 255));
      armor(Material.LEATHER_CHESTPLATE, Color.fromRGB(53, 191, 40));
      armor(Material.LEATHER_HELMET, Color.fromRGB(255, 255, 255));
      item(Material.PORTAL);
      addAbility(new AbilityFlash(icon, 10, 6));
      // I have to change items and armor color still
      // Fox has two events, ask on skype if your finishing fox.
      // ~Max
   }
}
