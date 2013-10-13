package org.infernogames.mb.Abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.infernogames.mb.MBPlugin;

public class ATypeCooldown extends MBAbility {
   private List<String> cooldown = new ArrayList<String>();
   
   public ATypeCooldown(String name) {
      super(name);
   }
   
   public void addCooldown(Player p) {
      cooldown.add(p.getName());
   }
   
   public boolean hasCooldown(Player p) {
      return cooldown.contains(p.getName());
   }
   
   public void removeCooldown(Player p) {
      cooldown.remove(p.getName());
   }
   
   public void removeCooldown(final Player p, long secs) {
      MBPlugin.registerRunnable(new BukkitRunnable() {
         @Override
         public void run() {
            cooldown.remove(p.getName());
         }
      }, secs);
   }
   
   public void addRemoveCooldown(Player p, long secs) {
      addCooldown(p);
      removeCooldown(p, secs);
   }
}
