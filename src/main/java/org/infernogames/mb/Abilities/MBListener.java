package org.infernogames.mb.Abilities;

import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.infernogames.mb.Arena.Arena;
import org.infernogames.mb.Managers.PlayerManager;
import org.infernogames.mb.Managers.PlayerManager.PlayerData;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 *         Listens and calls the ability events when activated
 */
public class MBListener implements Listener {
   
   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event) {
      if (PlayerManager.playerInArena(event.getPlayer())) {
         Player p = event.getPlayer();
         Action ac = event.getAction();
         Arena a = PlayerManager.getPlayerArena(event.getPlayer());
         PlayerData d = a.getPlayerManager().getPlayer(p);
         for (Entry<MBAbility, String[]> ab : d.c.getAbilities().entrySet()) {
            if (ab.getKey() == null) {
               System.out.println("Null key;");
               continue;
            }
            ab.getKey().onClick(p, ac, ab.getValue());
         }
      }
   }
   
}
