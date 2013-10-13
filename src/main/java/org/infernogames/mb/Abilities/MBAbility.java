package org.infernogames.mb.Abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.scheduler.BukkitRunnable;
import org.infernogames.mb.MBPlugin;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 */
public class MBAbility {
   
   private String name;
   
   public MBAbility(String name) {
      this.name = name;
   }
   
   public void onClick(Player p, Action a, String[] args) {
      
   }
   
   public String name() {
      return name;
   }
   
   public boolean rightClick(Action a) {
      return a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK;
   }
   
   public boolean leftClick(Action a) {
      return a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK;
   }
   
   private List<String> cooldown = new ArrayList<String>();
   
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
