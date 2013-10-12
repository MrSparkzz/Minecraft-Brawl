package org.infernogames.mb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.infernogames.mb.Abilities.AbilityFireball;
import org.infernogames.mb.Abilities.AbilityFlash;
import org.infernogames.mb.Abilities.AbilityFloat;
import org.infernogames.mb.Abilities.MBListener;
import org.infernogames.mb.Arena.ArenaListener;
import org.infernogames.mb.Commands.CommandCreation;
import org.infernogames.mb.Commands.CommandDelete;
import org.infernogames.mb.Commands.CommandHelp;
import org.infernogames.mb.Commands.CommandJoin;
import org.infernogames.mb.Commands.CommandLeave;
import org.infernogames.mb.Commands.CommandModify;
import org.infernogames.mb.Commands.MainCommand;
import org.infernogames.mb.Interfaces.ArenaManager;
import org.infernogames.mb.Managers.AbilityManager;
import org.infernogames.mb.Managers.ClassManager;
import org.infernogames.mb.Managers.CreationManager;
import org.infernogames.mb.Managers.FileManager;
import org.infernogames.mb.Managers.MBArenaManager;
import org.infernogames.mb.Utils.DoubleJump;
import org.infernogames.mb.Utils.SignListener;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 *         The main class used by bukkit. Also starts the plugin.
 */
public class MBPlugin extends JavaPlugin {
   public static ArenaManager arenaManager;
   private static PluginManager pm = Bukkit.getServer().getPluginManager();
   private static MBPlugin instance;
   public Logger log;
   
   @Override
   public void onEnable() {
      FileManager.dataFolder = getDataFolder();
      arenaManager = new MBArenaManager();
      instance = this;
      log = getLogger();
      
      getCommand("mb").setExecutor(new MainCommand());
      
      MainCommand.registerCommand(new CommandJoin());
      MainCommand.registerCommand(new CommandCreation());
      MainCommand.registerCommand(new CommandLeave());
      MainCommand.registerCommand(new CommandDelete());
      MainCommand.registerCommand(new CommandModify());
      MainCommand.registerCommand(new CommandHelp());
      
      AbilityManager.registerAbility(new AbilityFireball());
      AbilityManager.registerAbility(new AbilityFlash());
      AbilityManager.registerAbility(new AbilityFloat());
      
      registerListener(new DoubleJump());
      registerListener(new ArenaListener());
      registerListener(new SignListener());
      registerListener(new MBListener());
      
      FileManager man = new FileManager("Arenas");
      FileConfiguration arenaConfig = man.getConfig();
      if (arenaConfig.isSet("Arenas")) {
         for (String s : arenaConfig.getConfigurationSection("Arenas").getKeys(false)) {
            log.info("Loaded Arena: " + s);
            arenaManager.addArena(CreationManager.createArena(s));
         }
      }
      FileManager cMan = new FileManager("Config");
      cMan.addDefault("Default_Lives", 5);
      cMan.saveConfig();
      
      loadClass("Mario");
      loadClass("Kirby");
      
      ClassManager.loadClasses();
      
      log.info("[MinecraftBrawl] v" + getDescription().getVersion() + " enabled.");
   }
   
   private void loadClass(String name) {
      saveResource("Classes/" + name + ".yml", false);
   }
   
   @Override
   public void saveResource(String resourcePath, boolean replace) {
      if (resourcePath == null || resourcePath.equals("")) {
         throw new IllegalArgumentException("ResourcePath cannot be null or empty");
      }
      
      resourcePath = resourcePath.replace('\\', '/');
      InputStream in = getResource(resourcePath);
      if (in == null) {
         throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in "
                  + getFile());
      }
      
      File outFile = new File(FileManager.dataFolder, resourcePath);
      int lastIndex = resourcePath.lastIndexOf('/');
      File outDir = new File(FileManager.dataFolder, resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));
      
      if (!outDir.exists()) {
         outDir.mkdirs();
      }
      
      try {
         if (!outFile.exists() || replace) {
            OutputStream out = new FileOutputStream(outFile);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
               out.write(buf, 0, len);
            }
            out.close();
            in.close();
         }
      } catch (IOException ex) {
         log.log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, ex);
      }
   }
   
   @Override
   public void onDisable() {
      log.info("[MinecraftBrawl] v" + this.getDescription().getVersion() + " disabled.");
   }
   
   public static void registerRunnable(BukkitRunnable r, long delay) {
      r.runTaskLater(instance, delay);
   }
   
   public static void registerRepeatedRunnable(BukkitRunnable r, long delay, long period) {
      r.runTaskTimer(instance, delay, period);
   }
   
   public static void registerAsyncTask(BukkitRunnable r) {
      r.runTaskAsynchronously(instance);
   }
   
   public static void registerListener(final Listener listener) {
      pm.registerEvents(listener, instance);
   }
   
   public static String getVersion() {
      return instance.getDescription().getVersion();
   }
}
