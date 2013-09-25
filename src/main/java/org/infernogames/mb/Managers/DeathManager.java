package org.infernogames.mb.Managers;

import org.bukkit.scheduler.BukkitRunnable;
import org.infernogames.mb.MBPlugin;
import org.infernogames.mb.Arena.Arena;
import org.infernogames.mb.Managers.PlayerManager.PlayerData;

public class DeathManager {
   
   private Arena arena;
   
   public DeathManager(Arena arena) {
      this.arena = arena;
      new DeathRunnable().runTaskTimer(MBPlugin.instance, 0, 10);
   }
   
   private class DeathRunnable extends BukkitRunnable {
      @Override
      public void run() {
         for (PlayerData data : arena.getPlayerManager().getArenaPlayers()) {
            if (!arena.getRegion().contains(data.getPlayer().getLocation())) {
               
            }
         }
      }
   }
   
   public enum DeathCause {
      PLAYER, MONSTER, KNOCKED_OUT, FALL_OUT, ENVIRONMENTAL;
   }
}
