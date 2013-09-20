package net.supersmashcraft.ClassUtils;

import net.supersmashcraft.Arena.Arena;
import net.supersmashcraft.Classes.SSCClass;
import net.supersmashcraft.Managers.ArenaManager;
import net.supersmashcraft.Managers.PlayerManager.PlayerData;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JoinUtils {
   
   public static boolean basicArenaChecks(Player p, String arena) {
      if (ArenaManager.arenaRegistered(arena)) {
         if (!ArenaManager.isPlayerInArena(p)) {
            if (p.hasPermission("scb.arena.join." + arena)) {
               if(!ArenaManager.getArena(arena).hasStarted()){
                  return true;
               } else {
                  Msg.warning(p, "That arena has already started!");
               }
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
      p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000, 1), true);
      if (c.name() == "Kirby") {
         p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000, 0), true);
      } else {
         p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000, 1), true);
      }
      c.setupPlayer(p);
   }
   
   public static String stopPlayer(Player p) {
      if (DoubleJump.kirby.containsKey(p.getName())) {
         DoubleJump.kirby.remove(p.getName());
      }
      Arena a = ArenaManager.getPlayerArena(p);
      a.getPlayerManager().getPlayerClass(p).onPlayerDespawn(p);
      String name = a.getName();
      PlayerData d = a.getPlayerManager().getPlayerData(p);
      a.getPlayerManager().removePlayer(p);
      d.reset();
      for (PotionEffect e : p.getActivePotionEffects()) {
         p.removePotionEffect(e.getType());
      }
      p.setFallDistance(0);
      p.teleport(a.getStop());
      return name;
   }
   
}
