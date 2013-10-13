package org.infernogames.mb.Abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.infernogames.mb.Managers.PlayerManager;

public class AbilityEnderpearlTeleport extends MBAbility implements Listener {
   
   private int ticks = 30;
   
   public AbilityEnderpearlTeleport() {
      super("Teleport");
   }
   
   @SuppressWarnings("deprecation")
   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event) {
      if (rightClick(event.getAction()) && hasCooldown(event.getPlayer())) {
         event.setCancelled(true);
         event.getPlayer().updateInventory();
      }
   }
   
   @EventHandler(priority = EventPriority.HIGHEST)
   public void onPlayerTeleport(PlayerTeleportEvent event) {
      if (event.getCause() == TeleportCause.ENDER_PEARL && PlayerManager.playerInArena(event.getPlayer())) {
         final Player p = event.getPlayer();
         if (PlayerManager.getPlayerArena(p).getPlayerManager().getPlayer(p).c.hasAbility("Teleport")) {
            if (hasCooldown(event.getPlayer())
                     && !PlayerManager.getPlayerArena(p).getRegion().contains(event.getTo())) {
               event.setCancelled(true);
            } else {
               addRemoveCooldown(p, ticks);
            }
            PlayerManager.getPlayerArena(p).getPlayerManager().getPlayer(p).c.setupPlayer(p);
         }
      }
   }
}
