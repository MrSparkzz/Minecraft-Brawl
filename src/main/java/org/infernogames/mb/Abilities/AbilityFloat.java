package org.infernogames.mb.Abilities;

import org.bukkit.entity.Player;
import org.infernogames.mb.Managers.PlayerManager;
import org.infernogames.mb.Managers.PlayerManager.PlayerData;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 *         Used to decide how many times a player can float
 */
public class AbilityFloat extends MBAbility {
   private int floatTimes = 4;
   
   public AbilityFloat() {
      super("Float");
   }
   
   public int getFloatTimes(Player p) {
      PlayerData d = PlayerManager.getPlayerArena(p).getPlayerManager().getPlayer(p);
      String[] args = d.c.getAbilityArguments("Float");
      if (args.length >= 1) {
         return Integer.parseInt(args[0]);
      }
      return floatTimes;
   }
}
