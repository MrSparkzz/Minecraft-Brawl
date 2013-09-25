package org.infernogames.mb.Classes;


import org.bukkit.Color;
import org.bukkit.Material;
import org.infernogames.mb.Abilities.AbilityFireball;

public class ClassMario extends SSCClass {
   
   public ClassMario() {
      super("Mario", "Bro!", Material.FIREBALL);
      item(Material.BLAZE_ROD);
      armor(Material.LEATHER_HELMET, Color.fromRGB(255, 31, 31));
      armor(Material.LEATHER_CHESTPLATE, Color.fromRGB(255, 31, 31));
      armor(Material.LEATHER_CHESTPLATE, Color.fromRGB(255, 31, 31));
      armor(Material.LEATHER_HELMET, Color.fromRGB(255, 31, 31));
      addAbility(new AbilityFireball(20));
   }
}