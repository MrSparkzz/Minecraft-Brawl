package net.supersmashcraft.Commands;

import net.supersmashcraft.ClassUtils.JoinUtils;
import net.supersmashcraft.ClassUtils.Msg;
import net.supersmashcraft.Managers.ArenaManager;

import org.bukkit.entity.Player;

public class CommandLeave extends SSCCommand {

   public CommandLeave() {
      super("leave", "ssc.arena.leave", -1);
   }

   @Override
   public void onCommand(Player p, String[] args) {
      if(ArenaManager.isPlayerInArena(p)){
         Msg.msg(p, "You have left the arena " + JoinUtils.stopPlayer(p) + "!");
      } else {
         Msg.warning(p, "You are not in an Arena!");
      }
   }
   
}
