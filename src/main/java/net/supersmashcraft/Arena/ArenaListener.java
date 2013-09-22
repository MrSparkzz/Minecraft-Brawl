package net.supersmashcraft.Arena;

import net.supersmashcraft.Managers.PlayerManager;
import net.supersmashcraft.Managers.PlayerManager.PlayerData;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.util.Vector;

public class ArenaListener implements Listener {
   
   @EventHandler
   public void onPlayerMove(PlayerMoveEvent event) {
      if (PlayerManager.playerInArena(event.getPlayer())) {
         Player p = event.getPlayer();
         Vector v = p.getLocation().toVector();
         Arena a = PlayerManager.getPlayerArena(p);
         if (a.getManager().hasStarted()) {
            if (!v.isInAABB(a.getMinimumPoint().toVector(), a.getMaximumPoint().toVector())) {
               PlayerData data = a.getManager().getPlayerManager().getPlayer(p);
               data.removeLife();
            }
         }
      }
   }
   
   @EventHandler
   public void onPluginDisable(PluginDisableEvent event) {
      for (PlayerData data : PlayerManager.getAllPlayers()) {
         data.a.getManager().getPlayerManager().stopPlayer(data.getPlayer());
      }
   }
   
   @EventHandler
   public void onEntityDBE(EntityDamageEvent event) {
      if (event.getEntity() instanceof Player) {
         Player p = (Player) event.getEntity();
         if (PlayerManager.playerInArena(p)) {
            if (event.getCause() == DamageCause.FALL) {
               event.setCancelled(true);
               return;
            }
            if (p.getHealth() - event.getDamage() < 1) {
               Arena a = PlayerManager.getPlayerArena(p);
               if (a.getManager().hasStarted()) {
                  a.getManager().getPlayerManager().getPlayer(p).removeLife();
               } else {
                  p.teleport(a.getLobbyLocation());
               }
            }
         }
      }
   }
   
   @EventHandler
   public void onPlayerQuit(PlayerQuitEvent event) {
      Player p = event.getPlayer();
      if (PlayerManager.playerInArena(p)) {
         PlayerManager.getPlayerArena(p).getManager().getPlayerManager().stopPlayer(p);
      }
   }
   
}
