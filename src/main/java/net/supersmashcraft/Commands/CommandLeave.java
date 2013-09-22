package net.supersmashcraft.Commands;

import net.supersmashcraft.Arena.Arena;
import net.supersmashcraft.ClassUtils.Msg;
import net.supersmashcraft.Managers.PlayerManager;

import org.bukkit.entity.Player;

public class CommandLeave extends SSCCommand {
   
   public CommandLeave() {
      super("leave", "ssc.arena.leave", -1);
   }
   
   @Override
   public void onCommand(Player p, String[] args) {
      if (PlayerManager.playerInArena(p)) {
         Arena a = PlayerManager.getPlayerArena(p);
         Msg.msg(p, "You have left the arena " + a.getName() + "!");
         a.getManager().getPlayerManager().stopPlayer(p);
      } else {
         Msg.warning(p, "You are not in an Arena!");
      }
   }
   
}
