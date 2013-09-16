package net.supersmashcraft.Commands;

import net.supersmashcraft.ClassUtils.JoinUtils;
import net.supersmashcraft.ClassUtils.Msg;
import net.supersmashcraft.Managers.ArenaManager;
import net.supersmashcraft.Managers.ClassManager;

import org.bukkit.entity.Player;

/**
 * 
 * @author Paul, Breezeyboy, Max_The_Link_Fan
 * 
 */
public class CommandJoin extends SSCCommand {
   
   public CommandJoin() {
      super("join", "scb.arena.join", 2);
   }
   
   @Override
   public void onCommand(final Player p, final String[] args) {
      if (JoinUtils.basicArenaChecks(p, args[0])) {
         // For now, lets not have a lobby. Lets just have them define it
         // with the name
         if (ClassManager.classExists(args[1])) {
            if (p.hasPermission("scb.class." + args[1])) {
               // Have him join arena
               ArenaManager.getArena(args[0]).getPlayerManager()
                        .addPlayer(p, ClassManager.getRegisteredClass(args[1]));
            } else {
               Msg.warning(p, "You do not have permission to use that class!");
            }
         } else {
            Msg.warning(p, "That class does not exist!");
         }
      }
   }
   
}
