package net.supersmashcraft.ClassUtils;

import java.util.HashMap;

import net.supersmashcraft.SSCPlugin;
import net.supersmashcraft.Abilities.AbilityFloat;
import net.supersmashcraft.Arena.Arena;
import net.supersmashcraft.Managers.PlayerManager;
import net.supersmashcraft.Managers.PlayerManager.PlayerData;

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
            for (final PlayerData data : PlayerManager.getAllPlayers()) {
               final Player player = data.getPlayer();
               if (player == null) {
                  return;
               }
               if (!PlayerManager.getPlayerArena(player).getManager().hasStarted()) {
                  return;
               }
               // Check if under them isn't air, if so allow them to jump
               Location l = player.getLocation().subtract(0, 1, 0);
               if (!l.getBlock().getType().equals(Material.AIR)) {
                  player.setAllowFlight(true);
                  if (data.c.hasAbility("Float"))
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
      if (!PlayerManager.playerInArena(event.getPlayer()))
         return;
      final Player player = event.getPlayer();
      if (event.isFlying()) {
         player.setAllowFlight(false);
         event.setCancelled(true);
         Arena a = PlayerManager.getPlayerArena(player);
         if (kirby.containsKey(player.getName())) {
            int j = kirby.get(player.getName());
            if (j < ((AbilityFloat) a.getPlayerManager().getPlayer(player).c.getAbility("Float")).getFloatTimes()) {
               kirby.remove(player.getName());
               kirby.put(player.getName(), j + 1);
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
         
         if (a.getManager().getPlayerManager().getPlayer(player).name == "Kirby") {
            normal.setY(0.75 + Math.abs(normal.getY()) * 0.98);
         } else {
            normal.setY(0.75 + Math.abs(normal.getY()) * 0.65);
         }
         event.getPlayer().setVelocity(normal);
         
         player.getWorld().playSound(player.getLocation(), Sound.ZOMBIE_INFECT, 0.5f, 1.8f);
      }
   }
   
}
