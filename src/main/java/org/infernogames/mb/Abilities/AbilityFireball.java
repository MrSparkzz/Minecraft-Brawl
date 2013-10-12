package org.infernogames.mb.Abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.scheduler.BukkitRunnable;
import org.infernogames.mb.MBPlugin;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 *         Ability to shoot a fire arrow which disappears over time
 */
public class AbilityFireball extends MBAbility {
   
   private long ticks = 20;
   
   public AbilityFireball() {
      super("Fireball");
   }
   
   List<String> cooldown = new ArrayList<String>();
   
   @SuppressWarnings("deprecation")
   @Override
   public void onClick(final Player p, Action a, String[] args) {
      if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK && !cooldown.contains(p.getName())) {
         if (args.length >= 1) {
            ticks = Long.parseLong(args[0]);
            if (args.length == 2 && p.getItemInHand().getType() != Material.getMaterial(Integer.parseInt(args[1]))) {
               return;
            }
         }
         final Arrow arrow = p.launchProjectile(Arrow.class);
         arrow.setFireTicks((int) ticks);
         cooldown.add(p.getName());
         
         MBPlugin.registerRunnable(new BukkitRunnable() {
            @Override
            public void run() {
               cooldown.remove(p.getName());
               arrow.remove();
            }
         }, ticks);
      }
   }
   
}
