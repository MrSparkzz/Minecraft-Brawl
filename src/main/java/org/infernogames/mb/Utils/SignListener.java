package org.infernogames.mb.Utils;


import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.infernogames.mb.Arena.Arena;
import org.infernogames.mb.Managers.ArenaManager;
import org.infernogames.mb.Managers.SignManager;
import org.infernogames.mb.Managers.SignManager.SignType;

public class SignListener implements Listener {
   @EventHandler
   public void onSignChange(SignChangeEvent event) {
      if (event.getLine(0).startsWith("[SSC]")) {
         String part = event.getLine(0).replace("[SSC]", "");
         if (ArenaManager.arenaRegistered(part)) {
            Arena a = ArenaManager.getArena(part);
            event.setLine(0, ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "SSC" + ChatColor.DARK_GRAY + "]");
            event.setLine(1, a.getName());
            SignManager.addSign(a, event.getBlock(), SignType.STATUS);
         } else {
            event.getPlayer().sendMessage("No not correct!");
         }
      }
   }
}
