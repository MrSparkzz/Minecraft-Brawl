package net.supersmashcraft.Commands;

import java.util.ArrayList;
import java.util.List;

import net.supersmashcraft.ClassUtils.Msg;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {
   
   private static List<SSCCommand> commands = new ArrayList<SSCCommand>();
   
   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (args.length == 0) {
         // Show help
         sender.sendMessage("Help Message");
      } else {
         if (!(sender instanceof Player)) {
            sender.sendMessage("Sorry! These commands are player only for now.");
            return true;
         }
         SSCCommand cmd = null;
         Player p = (Player) sender;
         for (SSCCommand c : commands) {
            if (c.getCommand().equalsIgnoreCase(args[0])) {
               if (c.getArgumentLength() == -1 || c.getArgumentLength() == args.length - 1) {
                  if (p.hasPermission(c.getPermission())) {
                     cmd = c;
                  } else {
                     Msg.warning(p, "You do not have permission to use that command!");
                     return true;
                  }
               } else {
                  Msg.warning(p, "You have the wrong amount of arguments!");
                  return true;
               }
            }
         }
         if (cmd != null) {
            String[] newArgs = new String[args.length - 1];
            for (int i = 1; i < args.length; i++) {
               newArgs[i - 1] = args[i];
            }
            cmd.onCommand(p, newArgs);
         } else {
            Msg.warning(p, "We couldn't find that argument!");
            return true;
         }
      }
      return true;
   }
   
   public static void registerCommand(SSCCommand cmd) {
      commands.add(cmd);
   }
   
}
