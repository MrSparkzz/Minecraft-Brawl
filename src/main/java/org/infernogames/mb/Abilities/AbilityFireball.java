package org.infernogames.mb.Abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.scheduler.BukkitRunnable;
import org.infernogames.mb.MBPlugin;

public class AbilityFireball extends MBAbility {
   
   private long ticks = 15;
   
   public AbilityFireball() {
      super("Fireball");
   }
   
   List<String> cooldown = new ArrayList<String>();
   
   @Override
   public void onClick(final Player p, Action a) {
      if (!cooldown.contains(p.getName())) {
         final Arrow arrow = p.launchProjectile(Arrow.class);
         arrow.setTicksLived(10);
         arrow.setFireTicks(10);
         cooldown.add(p.getName());
         new BukkitRunnable() {
            @Override
            public void run() {
               cooldown.remove(p.getName());
               arrow.remove();
            }
         }.runTaskLater(MBPlugin.instance, ticks);
      }
   }
   
}
