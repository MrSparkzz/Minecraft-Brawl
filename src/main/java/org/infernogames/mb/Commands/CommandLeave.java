package org.infernogames.mb.Commands;


import org.bukkit.entity.Player;
import org.infernogames.mb.Arena.Arena;
import org.infernogames.mb.Managers.PlayerManager;
import org.infernogames.mb.Utils.Msg;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 */
public class CommandLeave extends MBCommand {
   
   public CommandLeave() {
      super("leave", "ssc.arena.leave", -1, "Leaves a game");
   }
   
   @Override
   public void onCommand(Player p, String[] args) {
      if (PlayerManager.playerInArena(p)) {
         Arena a = PlayerManager.getPlayerArena(p);
         Msg.msg(p, "You have left the arena " + a.getName() + "!");
         a.getPlayerManager().stopPlayer(a.getPlayerManager().getPlayer(p));
      } else {
         Msg.warning(p, "You are not in an Arena!");
      }
   }
   
}
