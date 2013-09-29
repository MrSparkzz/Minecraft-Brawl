package org.infernogames.mb.Commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * 
 * @author Paul, Breezeyboy
 *
 */
public class CommandHelp extends MBCommand{

   public CommandHelp(){
      super("help", "ssc,help", 0, "Help command");
   }

   @Override
   public void onCommand(Player p, String[] args) {
      @SuppressWarnings("unchecked")
      List<MBCommand> commands = MainCommand.getCommands();
      p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8-- &aMinecraft Brawl Help &8--"));
      for(MBCommand c : commands){
         if(!(c.getCommand().equalsIgnoreCase("help"))){
         p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8/&a" + c.getCommand() + " &8- &e" + c.getUsage()));
      }
      }
   }
   
}
