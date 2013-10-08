package org.infernogames.mb.Arena;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.infernogames.mb.Arena.ArenaRegion.WarpType;
import org.infernogames.mb.Managers.DeathManager.DeathCause;
import org.infernogames.mb.Managers.PlayerManager;
import org.infernogames.mb.Managers.PlayerManager.PlayerData;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 *         Used to listen for certain events happening inside the arena.
 *         Excludes ability listeners.
 */
public class ArenaListener implements Listener {
   
   @EventHandler
   public void onPluginDisable(PluginDisableEvent event) {
      for (PlayerData data : PlayerManager.getAllPlayers()) {
         data.a.getPlayerManager().stopPlayer(data);
      }
   }
   
   @EventHandler
   public void onEntityDBE(EntityDamageByEntityEvent event) {
      if (event.getDamager() instanceof Player && PlayerManager.playerInArena((Player) event.getDamager())) {
         event.setCancelled(true);
      }
      if (event.getDamager() instanceof Projectile
               && ((Projectile) event.getDamager()).getShooter() instanceof Player
               && PlayerManager.playerInArena(((Player) ((Projectile) event.getDamager()).getShooter()))) {
         event.setCancelled(true);
      }
   }
   
   @EventHandler
   public void onEntityDBE(EntityDamageEvent event) {
      if (event.getEntity() instanceof Player) {
         Player p = (Player) event.getEntity();
         if (!PlayerManager.playerInArena(p)) {
            return;
         }
         if (event.getCause() == DamageCause.FALL) {
            event.setCancelled(true);
            return;
         }
         if (p.getHealth() - event.getDamage() < 1) {
            Arena a = PlayerManager.getPlayerArena(p);
            if (a.hasStarted()) {
               a.getPlayerManager().getPlayer(p).removeLife(DeathCause.PLAYER);
            } else {
               p.teleport(a.getRegion().getWarp(WarpType.LOBBY));
            }
         }
      }
   }
   
   @EventHandler
   public void onPlayerQuit(PlayerQuitEvent event) {
      Player p = event.getPlayer();
      if (PlayerManager.playerInArena(p)) {
         PlayerManager man = PlayerManager.getPlayerArena(p).getPlayerManager();
         man.stopPlayer(man.getPlayer(event.getPlayer()));
      }
   }
   
}
