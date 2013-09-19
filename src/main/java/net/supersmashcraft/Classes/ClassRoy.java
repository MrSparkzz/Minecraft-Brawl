package net.supersmashcraft.Classes;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ClassRoy extends SSCClass {
   
   public ClassRoy() {
      super("Roy");
      armor(Material.LEATHER_LEGGINGS, Color.fromRGB(255, 255, 255));
      armor(Material.LEATHER_BOOTS, Color.fromRGB(65, 49, 245));
      armor(Material.LEATHER_CHESTPLATE, Color.fromRGB(65, 49, 245));
      armor(Material.LEATHER_HELMET, Color.fromRGB(255, 191, 0));
      item(Material.GHAST_TEAR);
      
   }
   
   @Override
   public void onPlayerSpawn(final Player p) {
      p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000, 1), true);
   }
   
   @Override
   public void onPlayerDespawn(Player p){
      p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
   }
   
}