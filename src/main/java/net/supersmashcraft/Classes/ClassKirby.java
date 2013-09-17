package net.supersmashcraft.Classes;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ClassKirby extends SSCClass {
   
   public ClassKirby() {
      super("Kirby", armor(Material.LEATHER_BOOTS, Color.fromRGB(255, 161, 161)), armor(Material.LEATHER_LEGGINGS,
               Color.fromRGB(255, 161, 161)), armor(Material.LEATHER_CHESTPLATE, Color.fromRGB(255, 161, 161)),
               armor(Material.LEATHER_HELMET, Color.fromRGB(255, 161, 161)), item(Material.GHAST_TEAR));
   }
   
   @Override
   public void onPlayerSpawn(final Player p) {
      // TODO
   }
   
}