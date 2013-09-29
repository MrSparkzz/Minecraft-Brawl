package org.infernogames.mb.Commands;

import org.bukkit.entity.Player;
import org.infernogames.mb.Utils.Msg;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 */
public class CommandHelp extends MBCommand {
   
   public CommandHelp() {
      super("help", "ssc.help", -1, "Help command");
   }
   
   @Override
   public void onCommand(Player p, String[] args) {
      Msg.msg(p, "&8-- &aMinecraft Brawl Help &8--");
      if (args.length != 1) {
         for (MBCommand c : MainCommand.getCommands()) {
            if (p.hasPermission(c.getPermission())) {
               Msg.msg(p, "&8/&a" + c.getCommand() + " &8- &e" + c.getUsage());
            }
         }
      } else {
         for (MBCommand c : MainCommand.getCommands()) {
            if (c.getCommand().equalsIgnoreCase(args[0]) && p.hasPermission(c.getPermission())) {
               Msg.msg(p, "&8/&a" + c.getCommand() + " &8- &e" + c.getUsage());
               return;
            }
         }
         onCommand(p, args);
      }
   }
   
}
