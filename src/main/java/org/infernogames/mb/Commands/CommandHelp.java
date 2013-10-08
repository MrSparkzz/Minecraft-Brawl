package org.infernogames.mb.Commands;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.infernogames.mb.Utils.Msg;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 *         This class shows help on different commands.
 */
public class CommandHelp extends MBCommand {
   
   public CommandHelp() {
      super("help", -1, new Permission("mb.help", "Shows MCBrawl Help", PermissionDefault.TRUE));
   }
   
   @Override
   public void onCommand(Player p, String[] args) {
      Msg.msg(p, "&8-=-=- &aMinecraft Brawl Help &8-=-=-", false);
      if (args.length != 1) {
         for (MBCommand c : MainCommand.getCommands()) {
            if (p.hasPermission(c.getPermission())) {
               Msg.msg(p, "&8/mb &a" + c.getCommand() + " &8- &e" + c.getUsage(), false);
            }
         }
      } else {
         for (MBCommand c : MainCommand.getCommands()) {
            if (c.getCommand().equalsIgnoreCase(args[0]) && p.hasPermission(c.getPermission())) {
               Msg.msg(p, "&8/mb &a" + c.getCommand() + " &8- &e" + c.getUsage(), false);
               return;
            }
         }
         onCommand(p, new String[0]);
      }
   }
   
}
