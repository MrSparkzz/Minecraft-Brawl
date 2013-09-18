package net.supersmashcraft.ClassUtils;

import net.supersmashcraft.Arena.Arena;
import net.supersmashcraft.Classes.SSCClass;
import net.supersmashcraft.Managers.ArenaManager;
import net.supersmashcraft.Managers.PlayerManager.PlayerData;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class JoinUtils {
   
   public static boolean basicArenaChecks(Player p, String arena) {
      if (ArenaManager.arenaRegistered(arena)) {
         if (!ArenaManager.isPlayerInArena(p)) {
            if (p.hasPermission("scb.arena.join." + arena)) {
               return true;
            } else {
               Msg.warning(p, "You do not have permission to join that arena!");
            }
         } else {
            Msg.warning(p, "You are already in an arena!");
         }
      } else {
         Msg.warning(p, "That arena does not exist!");
      }
      return false;
   }
   
   public static void startPlayer(Player p, Arena a, SSCClass c) {
      a.getPlayerManager().addPlayer(p, c);
      p.teleport(a.getRandomSpawn());
      p.setGameMode(GameMode.ADVENTURE);
      c.setupPlayer(p);
   }
   
   public static String stopPlayer(Player p) {
      Arena a = ArenaManager.getPlayerArena(p);
      String name = a.getName();
      PlayerData d = a.getPlayerManager().getPlayerData(p);
      d.reset();
      p.teleport(a.getStop());
      a.getPlayerManager().removePlayer(p);
      return name;
   }
   
}
