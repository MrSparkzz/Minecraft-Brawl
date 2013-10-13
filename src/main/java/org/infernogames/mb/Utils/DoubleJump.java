package org.infernogames.mb.Utils;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.infernogames.mb.MBPlugin;
import org.infernogames.mb.Abilities.AbilityFloat;
import org.infernogames.mb.Arena.Arena;
import org.infernogames.mb.Managers.PlayerManager;
import org.infernogames.mb.Managers.PlayerManager.PlayerData;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 */
public class DoubleJump implements Listener {
   public DoubleJump() {
      MBPlugin.registerRepeatedRunnable(new BukkitRunnable() {
         @Override
         public void run() {
            for (final PlayerData data : PlayerManager.getAllPlayers()) {
               final Player player = data.getPlayer();
               if (player == null) {
                  return;
               }
               if (!PlayerManager.getPlayerArena(player).hasStarted()) {
                  return;
               }
               // Check if under them isn't air, if so allow them to jump
               Location l = player.getLocation().subtract(0, 1, 0);
               if (!l.getBlock().getType().equals(Material.AIR)) {
                  player.setAllowFlight(true);
                  if (!data.c.hasAbility("Float")) {
                     return;
                  }
                  if (!floats.containsKey(player.getName())) {
                     floats.put(player.getName(), 0);
                  } else if (floats.get(player.getName()) != 0) {
                     floats.remove(player.getName());
                     floats.put(player.getName(), 0);
                  }
               }
            }
         }
      }, 0, 3);
   }
   
   public static HashMap<String, Integer> floats = new HashMap<String, Integer>();
   
   @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
   public void onPlayerToggleFlight(final PlayerToggleFlightEvent event) {
      if (!PlayerManager.playerInArena(event.getPlayer()))
         return;
      final Player player = event.getPlayer();
      if (event.isFlying()) {
         player.setAllowFlight(false);
         event.setCancelled(true);
         Arena a = PlayerManager.getPlayerArena(player);
         PlayerData data = a.getPlayerManager().getPlayer(player);
         if (floats.containsKey(player.getName()) && data.c.hasAbility("Float")) {
            int j = floats.get(player.getName());
            AbilityFloat fl = (AbilityFloat) a.getPlayerManager().getPlayer(player).c.getAbility("Float");
            if (j < fl.getFloatTimes(player)) {
               floats.remove(player.getName());
               floats.put(player.getName(), j + 1);
               player.setAllowFlight(true);
            } else {
               floats.remove(player.getName());
               return;
            }
         }
         
         final double pitch = Math.toRadians(player.getLocation().getPitch());
         final double yaw = Math.toRadians(player.getLocation().getYaw());
         final Vector normal = new Vector(-(Math.cos(pitch) * Math.sin(yaw)) / 1.5, -Math.sin(pitch) / 1.5,
                  Math.cos(pitch) * Math.cos(yaw) / 1.5);
         
         if (!a.getPlayerManager().getPlayer(player).c.hasAbility("Float")) {
            normal.setY(1 + Math.abs(normal.getY()) * 0.98);
         } else {
            normal.setY(1 + Math.abs(normal.getY()) * 0.65);
         }
         event.getPlayer().setVelocity(normal);
         
         player.getWorld().playSound(player.getLocation(), Sound.SPLASH, 0.5f, 1.8f);
      }
   }
}
