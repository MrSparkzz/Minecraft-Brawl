package org.infernogames.mb.Arena;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
 */
public class ArenaListener implements Listener {
   
   @EventHandler
   public void onPluginDisable(PluginDisableEvent event) {
      for (PlayerData data : PlayerManager.getAllPlayers()) {
         data.a.getManager().getPlayerManager().stopPlayer(data);
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
            if (a.getManager().hasStarted()) {
               a.getManager().getPlayerManager().getPlayer(p).removeLife(DeathCause.PLAYER);
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
