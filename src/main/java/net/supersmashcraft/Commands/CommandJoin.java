package net.supersmashcraft.Commands;

import net.supersmashcraft.ClassUtils.Msg;
import net.supersmashcraft.Classes.ClassKirby;
import net.supersmashcraft.Managers.ArenaManager;

import org.bukkit.entity.Player;

public class CommandJoin extends SSCCommand {
   
   public CommandJoin() {
      super("join", "scb.arena.join");
   }
   
   @Override
   public void onCommand(Player p, String[] args) {
      if (ArenaManager.arenaRegistered(args[0])) {
         if (!ArenaManager.isPlayerInArena(p)) {
            if (p.hasPermission("scb.arena.join." + args[0])) {
               ArenaManager.getArena(args[0]).getPlayerManager().addPlayer(p, new ClassKirby());
            } else {
               Msg.warning(p, "You do not have permission to join that arena!!");
            }
         } else {
            Msg.warning(p, "You are already in an arena!");
         }
      } else {
         Msg.warning(p, "That arena does not exist!");
      }
   }
   
}
