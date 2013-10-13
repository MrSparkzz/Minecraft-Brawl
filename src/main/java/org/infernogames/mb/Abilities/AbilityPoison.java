package org.infernogames.mb.Abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.infernogames.mb.Utils.Damager;

public class AbilityPoison extends MBAbility {
   
   private int radius = 5;
   private int time = 40;
   
   public AbilityPoison() {
      super("Poison");
   }
   
   @Override
   public void onClick(Player p, Action a, String[] args) {
      if (rightClick(a) && !hasCooldown(p)) {
         if (args.length >= 1) {
            radius = Integer.parseInt(args[0]);
            if (args.length >= 2) {
               time = Integer.parseInt(args[1]);
            }
         }
         new Damager(p).poison(radius, time);
      }
   }
   
}
