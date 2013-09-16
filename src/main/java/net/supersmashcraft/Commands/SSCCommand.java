package net.supersmashcraft.Commands;

import org.bukkit.entity.Player;

/**
 * 
 * @author Paul, Breezeyboy, Max_The_Link_Fan
 * 
 */
public abstract class SSCCommand {
   
   private String command;
   private String permission;
   private int args;
   
   public SSCCommand(final String command, final String permission, int args) {
      
   }
   
   public abstract void onCommand(Player p, String[] args);
   
   public String getCommand() {
      return this.command;
   }
   
   public String getPermission() {
      return this.permission;
   }
   
   public int getArgumentLength() {
      return this.args;
   }
   
}
