package org.infernogames.mb.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 */
public class Msg {
   
   public static String prefix = "&8[&aMB&8]&e ";
   
   public static void msg(final Player p, final String msg, boolean prefix) {
      p.sendMessage(c(msg, prefix));
   }
   
   public static void msg(Player p, String msg){
      msg(p, msg, true);
   }
   
   public static void warning(final Player player, final String msg) {
      player.sendMessage(c("&c" + msg, true));
   }
   
   public static void broadcast(String msg) {
      Bukkit.broadcastMessage(c(msg, true));
   }
   
   public static void opBroadcast(String msg) {
      for (Player op : Bukkit.getOnlinePlayers()) {
         if (op.hasPermission("ssc.showexceptions")) {
            msg(op, msg);
         }
      }
   }
   
   private static String c(String msg, boolean prefix) {
      if(prefix){
         return ChatColor.translateAlternateColorCodes('&', Msg.prefix + msg);
      } else {
         return ChatColor.translateAlternateColorCodes('&', "&e" + msg);
      }
   }
}
