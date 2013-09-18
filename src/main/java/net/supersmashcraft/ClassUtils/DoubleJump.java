package net.supersmashcraft.ClassUtils;

import javax.annotation.Nonnull;

import net.supersmashcraft.SSCPlugin;
import net.supersmashcraft.Managers.ArenaManager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * 
 * @author Paul, Breezeyboy, Max_The_Link_Fan, Double0Negative
 * 
 */
public class DoubleJump implements Listener {
   public DoubleJump() {
      new BukkitRunnable() {
         @Override
         public void run() {
            for (final String pName : ArenaManager.getAllPlayers()) {
               final Player player = Bukkit.getPlayerExact(pName);
               if(player == null){
                  return;
               }
               if (player.getExp() < 1.0f) {
                  player.setExp(player.getExp() + 0.2f);
               } else if (player.getExp() > 1.0f) {
                  player.setExp(1.0f);
               }
               refreshJump(player);
            }
         }
      }.runTaskTimer(SSCPlugin.instance, 0, 5);
   }
   
   @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
   public void noFallDamage(final EntityDamageEvent event) {
      if (event.getCause() != DamageCause.FALL)
         return;
      if (!(event.getEntity() instanceof Player))
         return;
      final Player player = (Player) event.getEntity();
      if (ArenaManager.isPlayerInArena(player)) {
         event.setCancelled(true);
         player.addAttachment(SSCPlugin.instance, "doublejump.nofalldamage", false);
         return;
      }
   }
   
   @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
   public void onGroundStateChanged(final PlayerMoveEvent event) {
      final Player p = event.getPlayer();
      if (!ArenaManager.isPlayerInArena(p))
         return;
      if (p.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.AIR)
         return;
      
      if (p.getExp() == 0.0f) {
         p.addAttachment(SSCPlugin.instance, "doublejump.nofalldamage", true);
         p.addAttachment(SSCPlugin.instance, "doublejump.using", false);
      }
   }
   
   @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
   public void onPlayerToggleFlight(final PlayerToggleFlightEvent event) {
      if (!ArenaManager.isPlayerInArena(event.getPlayer()))
         return;
      final Player player = event.getPlayer();
      if (event.isFlying()) {
         event.getPlayer().addAttachment(SSCPlugin.instance, "doublejump.using", true);
         event.getPlayer().setAllowFlight(false);
         if (ArenaManager.getPlayerClass(event.getPlayer()).name() == "Kirby") {
            player.setExp(player.getExp() + 0.17f);
         } else {
            player.setExp(0.0f);
         }
         event.setCancelled(true);
         
         final double pitch = Math.toRadians(player.getLocation().getPitch());
         final double yaw = Math.toRadians(player.getLocation().getYaw());
         
         final Vector normal = new Vector(-(Math.cos(pitch) * Math.sin(yaw)), -Math.sin(pitch), Math.cos(pitch)
                  * Math.cos(yaw));
         
         normal.setY(0.75 + Math.abs(normal.getY()) * 0.6);
         event.getPlayer().setVelocity(normal);
         
         player.getWorld().playSound(player.getLocation(), Sound.ZOMBIE_INFECT, 0.5f, 1.8f);
      }
   }
   
   public void refreshJump(@Nonnull final Player player) {
      if (player.getExp() >= 1.0f) {
         player.setAllowFlight(true);
      }
   }
   
}
