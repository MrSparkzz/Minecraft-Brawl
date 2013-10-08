package org.infernogames.mb.Commands;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.infernogames.mb.Arena.Arena;
import org.infernogames.mb.Managers.PlayerManager;
import org.infernogames.mb.Utils.Msg;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 *         This class allows you to leave a arena.
 */
public class CommandLeave extends MBCommand {
   
   public CommandLeave() {
      super("leave", -1, new Permission("mb.arena.leave", "Leaves an arena", PermissionDefault.TRUE));
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
