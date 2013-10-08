package org.infernogames.mb.Commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.infernogames.mb.MBPlugin;
import org.infernogames.mb.Arena.Arena;
import org.infernogames.mb.Managers.FileManager;
import org.infernogames.mb.Managers.PlayerManager.PlayerData;
import org.infernogames.mb.Utils.Msg;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 *         This class/command deletes the said arena.
 * 
 */
public class CommandDelete extends MBCommand {
   
   public CommandDelete() {
      super("delete", 1, new Permission("mb.arena.delete", "Deletes an arena", PermissionDefault.OP));
   }
   
   @Override
   public void onCommand(Player p, String[] args) {
      if (MBPlugin.arenaManager.arenaRegistered(args[0])) {
         Arena a = MBPlugin.arenaManager.getArena(args[0]);
         for (PlayerData data : a.getPlayerManager().getArenaPlayers()) {
            Player player = Bukkit.getPlayer(data.name);
            a.getPlayerManager().stopPlayer(data);
            Msg.msg(player, "Your arena has been disabled!");
         }
         MBPlugin.arenaManager.deleteArena(a);
         FileManager man = new FileManager("Arenas");
         man.getConfig().set("Arenas." + a.getName(), null);
         man.saveConfig();
         Msg.msg(p, "You have deleted the Arena " + a.getName() + "!");
      } else {
         Msg.warning(p, "That arena does not exist!");
      }
   }
   
}
