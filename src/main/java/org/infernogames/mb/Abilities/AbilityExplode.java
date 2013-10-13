package org.infernogames.mb.Abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.infernogames.mb.Utils.Damager;

public class AbilityExplode extends ATypeCooldown {
   
   int radius;
   double playerDamage;
   double enemyDamage;
   
   public AbilityExplode() {
      super("Explode");
   }
   
   @Override
   public void onClick(Player p, Action a, String[] args) {
      if (rightClick(a) && !hasCooldown(p)) {
         if (args.length >= 1) {
            radius = Integer.parseInt(args[0]);
            if (args.length >= 2) {
               playerDamage = Double.parseDouble(args[1]);
               if (args.length >= 3) {
                  enemyDamage = Double.parseDouble(args[2]);
               }
            }
         }
         new Damager(p).explode(radius, playerDamage, enemyDamage);
      }
   }
}
