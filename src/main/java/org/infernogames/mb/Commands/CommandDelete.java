package org.infernogames.mb.Commands;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.infernogames.mb.Arena.Arena;
import org.infernogames.mb.Managers.ArenaManager;
import org.infernogames.mb.Managers.FileManager;
import org.infernogames.mb.Managers.PlayerManager.PlayerData;
import org.infernogames.mb.Utils.Msg;

public class CommandDelete extends SSCCommand {
   
   public CommandDelete() {
      super("delete", "ssc.arena.delete", 1);
   }
   
   @Override
   public void onCommand(Player p, String[] args) {
      if (ArenaManager.arenaRegistered(args[0])) {
         Arena a = ArenaManager.getArena(args[0]);
         for (PlayerData data : a.getManager().getPlayerManager().getArenaPlayers()) {
            Player player = Bukkit.getPlayer(data.name);
            a.getManager().getPlayerManager().stopPlayer(data);
            Msg.msg(player, "Your arena has been disabled!");
         }
         ArenaManager.deleteArena(a);
         FileManager man = new FileManager("Arenas");
         man.getConfig().set("Arenas." + a.getName(), null);
         man.saveConfig();
         Msg.msg(p, "You have deleted the Arena " + a.getName() + "!");
      } else {
         Msg.warning(p, "That arena does not exist!");
      }
   }
   
}
