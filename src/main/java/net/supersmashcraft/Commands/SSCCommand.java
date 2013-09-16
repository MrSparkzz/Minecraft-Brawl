package net.supersmashcraft.Commands;

import org.bukkit.entity.Player;

/**
 * 
 * @author Paul, Breezeyboy, Max_The_Link_Fan
 * 
 */
public abstract class SSCCommand {
   
   public SSCCommand(final String command, final String permission) {
      
   }
   
   public abstract void onCommand(Player p, String[] args);
   
}
