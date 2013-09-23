package net.supersmashcraft.Arena;

import net.supersmashcraft.Abilities.SSCAbility;
import net.supersmashcraft.Managers.PlayerManager;
import net.supersmashcraft.Managers.PlayerManager.PlayerData;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;

public class ArenaListener implements Listener {
   
   /*
    * @EventHandler public void onPlayerMove(PlayerMoveEvent event) { if
    * (PlayerManager.playerInArena(event.getPlayer())) { Player p =
    * event.getPlayer(); Arena a = PlayerManager.getPlayerArena(p); if
    * (a.getManager().hasStarted()) { if(!LocationUtils.inside(p.getLocation(),
    * a.getMinimumPoint(), a.getMaximumPoint())){ p.sendMessage("Outside!"); } }
    * } }
    */
   
   @EventHandler
   public void onPluginDisable(PluginDisableEvent event) {
      for (PlayerData data : PlayerManager.getAllPlayers()) {
         data.a.getManager().getPlayerManager().stopPlayer(data);
      }
   }
   
   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event) {
      if (PlayerManager.playerInArena(event.getPlayer())) {
         Arena a = PlayerManager.getPlayerArena(event.getPlayer());
         PlayerData d = a.getManager().getPlayerManager().getPlayer(event.getPlayer());
         for (SSCAbility ab : d.c.getAbilities()) {
            ab.onClick(event.getPlayer(), event.getAction());
         }
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
         PlayerManager man = PlayerManager.getPlayerArena(p).getPlayerManager();
         man.stopPlayer(man.getPlayer(event.getPlayer()));
      }
   }
   
}
