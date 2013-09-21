package net.supersmashcraft.ClassUtils;

import net.supersmashcraft.Arena.Arena;
import net.supersmashcraft.Managers.ArenaManager;
import net.supersmashcraft.Managers.SignManager;
import net.supersmashcraft.Managers.SignManager.SignType;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

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
