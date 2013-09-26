package org.infernogames.mb;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.infernogames.mb.Abilities.AbilityFireball;
import org.infernogames.mb.Abilities.AbilityFlash;
import org.infernogames.mb.Abilities.AbilityFloat;
import org.infernogames.mb.Arena.ArenaListener;
import org.infernogames.mb.Commands.CommandCreation;
import org.infernogames.mb.Commands.CommandDelete;
import org.infernogames.mb.Commands.CommandJoin;
import org.infernogames.mb.Commands.CommandLeave;
import org.infernogames.mb.Commands.CommandModify;
import org.infernogames.mb.Commands.MainCommand;
import org.infernogames.mb.Managers.AbilityManager;
import org.infernogames.mb.Managers.ArenaManager;
import org.infernogames.mb.Managers.ClassManager;
import org.infernogames.mb.Managers.CreationManager;
import org.infernogames.mb.Managers.FileManager;
import org.infernogames.mb.Utils.DoubleJump;
import org.infernogames.mb.Utils.SignListener;

/**
 * 
 * @author Paul, Breezeyboy, Max_The_Link_Fan, chasechocolate
 * 
 */
public class MBPlugin extends JavaPlugin {
   public static MBPlugin instance;
   public PluginManager pm = Bukkit.getServer().getPluginManager();
   public Logger log;
   
   @Override
   public void onEnable() {
      FileManager.dataFolder = getDataFolder();
      instance = this;
      log = Bukkit.getServer().getLogger();
      
      getCommand("mb").setExecutor(new MainCommand());
      
      MainCommand.registerCommand(new CommandJoin());
      MainCommand.registerCommand(new CommandCreation());
      MainCommand.registerCommand(new CommandLeave());
      MainCommand.registerCommand(new CommandDelete());
      MainCommand.registerCommand(new CommandModify());
      
      AbilityManager.registerAbility(new AbilityFireball());
      AbilityManager.registerAbility(new AbilityFlash());
      AbilityManager.registerAbility(new AbilityFloat());
      
      registerListener(new DoubleJump());
      registerListener(new ArenaListener());
      registerListener(new SignListener());
      
      FileManager man = new FileManager("Arenas");
      FileConfiguration arenaConfig = man.getConfig();
      if (arenaConfig.isSet("Arenas")) {
         for (String s : arenaConfig.getConfigurationSection("Arenas").getKeys(false)) {
            log.info("Loaded Arena: " + s);
            ArenaManager.addArena(CreationManager.createArena(s));
         }
      }
      FileManager cMan = new FileManager("Config");
      cMan.addDefault("Default_Lives", 5);
      cMan.saveConfig();
      
      loadClass("Mario");
      
      ClassManager.loadClasses();
      
      log.info("[SuperSmashCraft] v" + this.getDescription().getVersion() + " enabled.");
   }
   
   private void loadClass(String name) {
      saveResource("Classes/" + name + ".yml", false);
   }
   
   @Override
   public void onDisable() {
      log.info("[SuperSmashCraft] v" + this.getDescription().getVersion() + " disabled.");
   }
   
   private void registerListener(final Listener listener) {
      pm.registerEvents(listener, this);
   }
}
