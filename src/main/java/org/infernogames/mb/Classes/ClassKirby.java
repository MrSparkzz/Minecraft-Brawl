package org.infernogames.mb.Classes;


import org.bukkit.Color;
import org.bukkit.Material;
import org.infernogames.mb.Abilities.AbilityFloat;

public class ClassKirby extends SSCClass {
   
   public ClassKirby() {
      super("Kirby", "Floating away...", Material.INK_SACK, 8);
      armor(Material.LEATHER_BOOTS, Color.fromRGB(255, 161, 161));
      armor(Material.LEATHER_LEGGINGS, Color.fromRGB(255, 161, 161));
      armor(Material.LEATHER_CHESTPLATE, Color.fromRGB(255, 161, 161));
      armor(Material.LEATHER_HELMET, Color.fromRGB(255, 161, 161));
      item(Material.GHAST_TEAR);
      addAbility(new AbilityFloat(3));
   }
   
}