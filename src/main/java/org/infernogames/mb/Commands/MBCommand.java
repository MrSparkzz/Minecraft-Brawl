package org.infernogames.mb.Commands;

import org.bukkit.entity.Player;

/**
 * 
 * @author Paul, Breezeyboy,
 * 
 */
public abstract class MBCommand {
   
   private String command;
   private String permission;
   private String usage;
   private int args;
   
   public MBCommand(final String command, final String permission, int args, final String usage){
      this.command = command;
      this.permission = permission;
      this.args = args;
      this.usage = usage;
   }
   
   public abstract void onCommand(Player p, String[] args);
   
   public String getCommand() {
      return this.command;
   }
   public String getUsage(){
   return usage;
   }
   
   public String getPermission() {
      return this.permission;
   }
   
   public int getArgumentLength() {
      return this.args;
   }
   
}
