package net.supersmashcraft.Arena;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.supersmashcraft.SSCPlugin;
import net.supersmashcraft.ClassUtils.JoinUtils;
import net.supersmashcraft.ClassUtils.Msg;
import net.supersmashcraft.Managers.CreationManager.Reward;
import net.supersmashcraft.Managers.CreationManager.Reward.RewardType;
import net.supersmashcraft.Managers.PlayerManager;
import net.supersmashcraft.Managers.PlayerManager.PlayerData;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Arena {
   
   private final PlayerManager pMan;
   private final String name;
   private final Location l1;
   private final Location l2;
   private final Location stop;
   private List<Location> spawns = new ArrayList<Location>();
   private ScoreboardManager manager = Bukkit.getScoreboardManager();
   private Reward reward;
   
   public Arena(final String name, final Location l1, final Location l2, final Location stop, Reward reward,
            final Location... spawns) {
      this.name = name;
      this.l1 = l1;
      this.l2 = l2;
      this.pMan = new PlayerManager(this);
      this.stop = stop;
      this.spawns = Arrays.asList(spawns);
      this.reward = reward;
      BukkitRunnable s = new BukkitRunnable() {
         @Override
         public void run() {
            refreshScoreboard();
         }
      };
      s.runTaskTimer(SSCPlugin.instance, 0, 40);
   }
   
   public Reward getReward() {
      return this.reward;
   }
   
   public String getName() {
      return this.name;
   }
   
   public PlayerManager getPlayerManager() {
      return this.pMan;
   }
   
   public Location getLocationOne() {
      return this.l1;
   }
   
   public Location getLocationTwo() {
      return this.l2;
   }
   
   public Location getStop() {
      return stop;
   }
   
   private boolean started = false;
   
   public boolean hasStarted(){
      return started;
   }
   public void start(){
      if(!hasStarted()){
         started = true;
      }
   }
   
   public Location getRandomSpawn() {
      return spawns.get(new Random().nextInt(spawns.size()));
   }
   
   public void refreshScoreboard() {
      if (pMan.getPlayers().size() == 1) {
         String name = "Example Player";
         for (PlayerData d : pMan.getPlayers()) {
            name = d.name;
         }
         for (Player p : Bukkit.getOnlinePlayers()) {
            Msg.msg(p, name + " just won on the arena " + this.name + "!");
         }
         Player p = Bukkit.getPlayer(name);
         JoinUtils.stopPlayer(p);
         if (reward.getType() == RewardType.Cash) {
            p.sendMessage("You got " + reward.getCash() + " cash!");
         } else {
            p.getInventory().addItem(reward.getReward());
         }
         started = false;
         return;
      }
      
      Scoreboard board = manager.getNewScoreboard();
      Objective objective = board.registerNewObjective("test", "dummy");
      objective.setDisplayName(ChatColor.GREEN + "Players  " + ChatColor.RED + "   Lives");
      objective.setDisplaySlot(DisplaySlot.SIDEBAR);
      for (PlayerData data : pMan.getPlayers()) {
         Player p = Bukkit.getPlayer(data.name);
         Score score = objective.getScore(p);
         score.setScore(data.lives);
         p.setScoreboard(board);
      }
   }
   
}
