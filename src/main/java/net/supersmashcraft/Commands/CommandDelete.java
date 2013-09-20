package net.supersmashcraft.Commands;

import net.supersmashcraft.Arena.Arena;
import net.supersmashcraft.ClassUtils.JoinUtils;
import net.supersmashcraft.ClassUtils.Msg;
import net.supersmashcraft.Managers.ArenaManager;
import net.supersmashcraft.Managers.FileManager;
import net.supersmashcraft.Managers.PlayerManager.PlayerData;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandDelete extends SSCCommand {
   
   public CommandDelete() {
      super("delete", "ssc.arena.delete", 1);
   }
   
   @Override
   public void onCommand(Player p, String[] args) {
      if (ArenaManager.arenaRegistered(args[0])) {
         Arena a = ArenaManager.getArena(args[0]);
         for (PlayerData data : a.getPlayerManager().getPlayers()) {
            Player player = Bukkit.getPlayer(data.name);
            JoinUtils.stopPlayer(player);
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
