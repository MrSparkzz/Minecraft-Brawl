package net.supersmashcraft.Arena;

import net.supersmashcraft.ClassUtils.JoinUtils;
import net.supersmashcraft.Managers.ArenaManager;
import net.supersmashcraft.Managers.PlayerManager.PlayerData;

import org.bukkit.Bukkit;
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
      if (ArenaManager.isPlayerInArena(event.getPlayer())) {
         Player p = event.getPlayer();
         Vector v = p.getLocation().toVector();
         Arena a = ArenaManager.getPlayerArena(p);
         if (v.isInAABB(a.getLocationOne().toVector(), a.getLocationOne().toVector())) {
            PlayerData data = a.getPlayerManager().getPlayerData(p);
            data.removeLifes(1);
         }
      }
   }
   
   @EventHandler
   public void onPluginDisable(PluginDisableEvent event) {
      for (String s : ArenaManager.getAllPlayers()) {
         JoinUtils.stopPlayer(Bukkit.getPlayer(s));
      }
   }
   
   @EventHandler
   public void onEntityDBE(EntityDamageEvent event) {
      if (event.getEntity() instanceof Player) {
         Player p = (Player) event.getEntity();
         if (ArenaManager.isPlayerInArena(p)) {
            if (event.getCause() == DamageCause.FALL) {
               event.setCancelled(true);
               return;
            }
            if (p.getHealth() - event.getDamage() < 1) {
               PlayerData data = ArenaManager.getPlayerArena(p).getPlayerManager().getPlayerData(p);
               data.removeLifes(1);
            }
         } else if (JoinUtils.teleporting.contains(p.getName())) {
            event.setCancelled(true);
         }
      }
   }
   
   @EventHandler
   public void onPlayerQuit(PlayerQuitEvent event) {
      if (ArenaManager.isPlayerInArena(event.getPlayer())) {
         JoinUtils.stopPlayer(event.getPlayer());
      }
   }
   
}
