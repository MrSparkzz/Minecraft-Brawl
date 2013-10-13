package org.infernogames.mb.Managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.infernogames.mb.MBClass;
import org.infernogames.mb.MBPlugin;
import org.infernogames.mb.Arena.Arena;
import org.infernogames.mb.Arena.ArenaRegion.WarpType;
import org.infernogames.mb.Arena.ArenaSettings.ArenaSetting;
import org.infernogames.mb.Managers.DeathManager.DeathCause;
import org.infernogames.mb.Utils.IconMenu;
import org.infernogames.mb.Utils.Msg;
import org.infernogames.mb.Utils.IconMenu.Row;
import org.infernogames.mb.Utils.IconMenu.onClick;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 */
public class PlayerManager {
   
   public static boolean playerInArena(Player p) {
      return getPlayerArena(p) != null;
   }
   
   public static Arena getPlayerArena(Player p) {
      for (Arena arena : MBPlugin.arenaManager.iterator()) {
         if (arena.getPlayerManager().hasPlayer(p)) {
            return arena;
         }
      }
      return null;
   }
   
   public static void openMenu(final Player p, final Arena arena) {
      IconMenu menu = new IconMenu("Pick your class!", (ClassManager.classAmount() / 9) + 1, new onClick() {
         @Override
         public boolean click(Player clicker, IconMenu menu, Row row, int slot, ItemStack item) {
            if (item == null || item.getType() == Material.AIR) {
               return true;
            }
            arena.getPlayerManager().startPlayer(p, arena,
                     ClassManager.getRegisteredClass(item.getItemMeta().getDisplayName()));
            return false;
         }
      });
      for (MBClass c : ClassManager.getClasses()) {
         menu.addDynamicButton(new ItemStack(c.icon()), ChatColor.GREEN + c.name(), c.desc());
      }
      menu.open(p);
   }
   
   public static List<PlayerData> getAllPlayers() {
      List<PlayerData> players = new ArrayList<PlayerData>();
      for (Arena arena : MBPlugin.arenaManager.iterator()) {
         players.addAll(arena.getPlayerManager().players);
      }
      return players;
   }
   
   private List<PlayerData> players = new ArrayList<PlayerData>();
   
   public void startPlayer(Player p, Arena a, MBClass c) {
      players.add(new PlayerData(p, a, c));
      p.getInventory().clear();
      for (PotionEffect e : p.getPlayer().getActivePotionEffects()) {
         p.getPlayer().removePotionEffect(e.getType());
      }
      p.getInventory().setArmorContents(null);
      p.setGameMode(GameMode.ADVENTURE);
      
      p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 2));
      p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, 1));
      p.setCanPickupItems(false);
      
      p.teleport(a.getRegion().getWarp(WarpType.LOBBY));
   }
   
   public void stopPlayer(PlayerData p) {
      p.resetData();
      for (PotionEffect e : p.getPlayer().getActivePotionEffects()) {
         p.getPlayer().removePotionEffect(e.getType());
      }
      p.getPlayer().setFallDistance(0);
      p.getPlayer().teleport(p.a.getRegion().getWarp(WarpType.STOP));
      players.remove(p);
   }
   
   public boolean hasPlayer(Player p) {
      for (PlayerData data : players) {
         if (data.name.equalsIgnoreCase(p.getName())) {
            return true;
         }
      }
      return false;
   }
   
   public PlayerData getPlayer(Player p) {
      for (PlayerData data : players) {
         if (data.name.equalsIgnoreCase(p.getName())) {
            return data;
         }
      }
      return null;
   }
   
   public void broadcast(String msg) {
      for (PlayerData data : players) {
         Msg.msg(data.getPlayer(), msg);
      }
   }
   
   public List<PlayerData> getArenaPlayers() {
      return players;
   }
   
   public class PlayerData {
      public String name;
      
      public ItemStack[] inventory;
      public ItemStack[] armor;
      
      public float exp;
      public double health;
      public float exhaust;
      public double maxHealth;
      
      public GameMode mode;
      boolean pickup;
      private Scoreboard b = Bukkit.getScoreboardManager().getNewScoreboard();
      
      public Arena a;
      public MBClass c;
      public int lives;
      
      public PlayerData(Player p, Arena a, MBClass c) {
         name = p.getName();
         
         inventory = p.getInventory().getContents();
         armor = p.getInventory().getArmorContents();
         
         exp = p.getExp();
         health = p.getHealth();
         exhaust = p.getExhaustion();
         maxHealth = p.getMaxHealth();
         
         mode = p.getGameMode();
         pickup = p.getCanPickupItems();
         if (p.getScoreboard() != null) {
            b = p.getScoreboard();
         }
         
         lives = a.getSettings().getIntSetting(ArenaSetting.STARTING_LIVES);
         this.a = a;
         this.c = c;
      }
      
      public void resetData() {
         Player p = getPlayer();
         
         p.getInventory().setContents(inventory);
         p.getInventory().setArmorContents(armor);
         
         p.setExp(exp);
         p.setHealth(0.1);
         p.setMaxHealth(maxHealth);
         p.setHealth(health);
         
         p.setGameMode(mode);
         p.setCanPickupItems(pickup);
         p.setScoreboard(b);
      }
      
      public void removeLife(DeathCause cause) {
         Player p = getPlayer();
         lives--;
         p.setHealth(p.getMaxHealth());
         p.teleport(a.getRegion().getWarp(WarpType.SPAWN));
         c.setupPlayer(p);
      }
      
      public Player getPlayer() {
         return Bukkit.getPlayer(name);
      }
      
      @Override
      public String toString() {
         return name;
      }
   }
}
