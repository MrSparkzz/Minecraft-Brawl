package org.infernogames.mb.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * 
 * @author Paul, Breezeyboy, Max_The_Link_Fan
 * 
 */
public class Msg {
   
   private static String prefix = "&8[&aMB&8]&e ";
   
   public static void msg(final Player player, final String msg) {
      player.sendMessage(c(msg));
   }
   
   public static void warning(final Player player, final String msg) {
      player.sendMessage(c("&c" + msg));
   }
   
   public static void broadcast(String msg) {
      Bukkit.broadcastMessage(c(msg));
   }
   
   public static void opBroadcast(String msg) {
      for (Player op : Bukkit.getOnlinePlayers()) {
         if (op.hasPermission("ssc.showexceptions")) {
            msg(op, msg);
         }
      }
   }
   
   private static String c(String msg) {
      return ChatColor.translateAlternateColorCodes('&', prefix + msg);
   }
}
