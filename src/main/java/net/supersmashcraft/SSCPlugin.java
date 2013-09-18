package net.supersmashcraft;

import java.util.logging.Logger;

import net.supersmashcraft.Arena.ArenaListener;
import net.supersmashcraft.ClassUtils.DoubleJump;
import net.supersmashcraft.Classes.ClassBowser;
import net.supersmashcraft.Classes.ClassKirby;
import net.supersmashcraft.Commands.CommandCreation;
import net.supersmashcraft.Commands.CommandJoin;
import net.supersmashcraft.Commands.CommandLeave;
import net.supersmashcraft.Commands.MainCommand;
import net.supersmashcraft.Managers.ArenaManager;
import net.supersmashcraft.Managers.ClassManager;
import net.supersmashcraft.Managers.CreationManager;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 
 * @author Paul, Breezeyboy, Max_The_Link_Fan
 * 
 */
public class SSCPlugin extends JavaPlugin {
   public static SSCPlugin instance;
   public PluginManager pm = Bukkit.getServer().getPluginManager();
   public Logger log;
   
   @Override
   public void onEnable() {
      instance = this;
      log = Bukkit.getServer().getLogger();
      getCommand("ssc").setExecutor(new MainCommand());
      MainCommand.registerCommand(new CommandJoin());
      MainCommand.registerCommand(new CommandCreation());
      MainCommand.registerCommand(new CommandLeave());
      
      ClassManager.registerClass(new ClassKirby());
      ClassManager.registerClass(new ClassBowser());
      
      registerListener(new DoubleJump());
      registerListener(new ArenaListener());
      
      if (getConfig().isSet("Arenas")) {
         for (String s : getConfig().getConfigurationSection("Arenas").getKeys(false)) {
            log.info("Loaded Arena: " + s);
            ArenaManager.addArena(CreationManager.createArena(s));
         }
      }
      log.info("[SuperSmashCraft] v" + this.getDescription().getVersion() + " enabled.");
   }
   
   @Override
   public void onDisable() {
      log.info("[SuperSmashCraft] v" + this.getDescription().getVersion() + " disabled.");
   }
   
   private void registerListener(final Listener listener) {
      pm.registerEvents(listener, this);
   }
}
