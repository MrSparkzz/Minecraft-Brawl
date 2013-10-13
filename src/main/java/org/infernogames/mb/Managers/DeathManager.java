package org.infernogames.mb.Managers;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.scheduler.BukkitRunnable;
import org.infernogames.mb.MBPlugin;
import org.infernogames.mb.Arena.Arena;
import org.infernogames.mb.Managers.PlayerManager.PlayerData;
import org.infernogames.mb.Utils.StringUtils;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 */
public class DeathManager implements Listener {
   
   private Arena arena;
   
   public DeathManager(Arena arena) {
      this.arena = arena;
      MBPlugin.registerRepeatedRunnable(new DeathRunnable(), 0, 10);
      MBPlugin.registerListener(this);
   }
   
   private HashMap<String, String> knocked = new HashMap<String, String>();
   
   @EventHandler(priority = EventPriority.HIGHEST)
   public void onEntityDBE(EntityDamageByEntityEvent event) {
      if (event.getEntity() instanceof Player && arena.getPlayerManager().hasPlayer((Player) event.getEntity())) {
         if (event.getCause() != DamageCause.VOID) {
            Player p = (Player) event.getEntity();
            if (knocked.containsKey(p.getName())) {
               knocked.remove(p.getName());
            }
            String damager = "";
            if (event.getDamager() instanceof Player) {
               damager = ((Player) event.getDamager()).getName();
            } else {
               damager = StringUtils.formatEnum(event.getDamager().getType());
            }
            knocked.put(p.getName(), damager);
         }
      }
   }
   
   private class DeathRunnable extends BukkitRunnable {
      @Override
      public void run() {
         if (!arena.hasStarted()) {
            return;
         }
         for (PlayerData data : arena.getPlayerManager().getArenaPlayers()) {
            if (!arena.getRegion().contains(data.getPlayer().getLocation())) {
               if (knocked.containsKey(data.toString())) {
                  data.removeLife(DeathCause.KNOCKED_OUT);
                  arena.broadcast(data + " was knocked out by " + knocked.get(data.toString()));
                  knocked.remove(data.toString());
               } else {
                  data.removeLife(DeathCause.FALL_OUT);
                  arena.broadcast(data + " fell out!");
               }
            }
         }
      }
   }
   
   public enum DeathCause {
      PLAYER, MONSTER, KNOCKED_OUT, FALL_OUT, ENVIRONMENTAL;
   }
}
