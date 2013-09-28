package org.infernogames.mb.Abilities;

import java.util.Map.Entry;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.infernogames.mb.Arena.Arena;
import org.infernogames.mb.Managers.PlayerManager;
import org.infernogames.mb.Managers.PlayerManager.PlayerData;

public class MBListener implements Listener {
   
   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event) {
      if (PlayerManager.playerInArena(event.getPlayer())) {
         Arena a = PlayerManager.getPlayerArena(event.getPlayer());
         PlayerData d = a.getManager().getPlayerManager().getPlayer(event.getPlayer());
         for (Entry<MBAbility, String[]> ab : d.c.getAbilities().entrySet()) {
            ab.getKey().onClick(event.getPlayer(), event.getAction(), ab.getValue());
         }
      }
   }
   
}
