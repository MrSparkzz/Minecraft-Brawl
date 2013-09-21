package net.supersmashcraft.ClassUtils;

import java.util.HashMap;

import net.supersmashcraft.SSCPlugin;
import net.supersmashcraft.Managers.ArenaManager;

import org.bukkit.Bukkit;
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

/**
 * 
 * @author Paul, Breezeyboy, Max_The_Link_Fan, doubleboss00
 * 
 */
public class DoubleJump implements Listener {
   public DoubleJump() {
      new BukkitRunnable() {
         @Override
         public void run() {
            for (final String pName : ArenaManager.getAllPlayers()) {
               final Player player = Bukkit.getPlayerExact(pName);
               if (player == null) {
                  return;
               }
               if (!ArenaManager.getPlayerArena(player).hasStarted()) {
                  return;
               }
               // Check if under them isn't air, if so allow them to jump
               Location l = player.getLocation().subtract(0, 1, 0);
               if (!l.getBlock().getType().equals(Material.AIR)) {
                  player.setAllowFlight(true);
                  if (ArenaManager.getPlayerClass(player).name() != "Kirby")
                     return;
                  if (!kirby.containsKey(player.getName())) {
                     kirby.put(player.getName(), 0);
                  } else if (kirby.get(player.getName()) != 0) {
                     kirby.remove(player.getName());
                     kirby.put(player.getName(), 0);
                  }
               }
            }
         }
      }.runTaskTimer(SSCPlugin.instance, 0, 3);
   }
   
   public static HashMap<String, Integer> kirby = new HashMap<String, Integer>();
   
   @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
   public void onPlayerToggleFlight(final PlayerToggleFlightEvent event) {
      if (!ArenaManager.isPlayerInArena(event.getPlayer()))
         return;
      final Player player = event.getPlayer();
      if (event.isFlying()) {
         player.setAllowFlight(false);
         event.setCancelled(true);
         if (kirby.containsKey(player.getName())) {
            int jump = kirby.get(player.getName());
            if (jump < 3) {
               kirby.remove(player.getName());
               kirby.put(player.getName(), jump + 1);
               player.setAllowFlight(true);
            } else {
               kirby.remove(player.getName());
               return;
            }
         }
         
         final double pitch = Math.toRadians(player.getLocation().getPitch());
         final double yaw = Math.toRadians(player.getLocation().getYaw());
         
         final Vector normal = new Vector(-(Math.cos(pitch) * Math.sin(yaw)) / 1.5, -Math.sin(pitch) / 1.5,
                  Math.cos(pitch) * Math.cos(yaw) / 1.5);
         if (ArenaManager.getPlayerClass(player).name() == "Kirby") {
            normal.setY(0.75 + Math.abs(normal.getY()) * 0.98);
         } else {
            normal.setY(0.75 + Math.abs(normal.getY()) * 0.65);
         }
         event.getPlayer().setVelocity(normal);
         
         player.getWorld().playSound(player.getLocation(), Sound.ZOMBIE_INFECT, 0.5f, 1.8f);
      }
   }
   
}
