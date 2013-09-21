package net.supersmashcraft;

import java.util.logging.Logger;

import net.supersmashcraft.Arena.ArenaListener;
import net.supersmashcraft.ClassUtils.DoubleJump;
import net.supersmashcraft.ClassUtils.SignListener;
import net.supersmashcraft.Classes.ClassBowser;
import net.supersmashcraft.Classes.ClassFox;
import net.supersmashcraft.Classes.ClassKirby;
import net.supersmashcraft.Classes.ClassLink;
import net.supersmashcraft.Classes.ClassLuigi;
import net.supersmashcraft.Classes.ClassMario;
import net.supersmashcraft.Classes.ClassRoy;
import net.supersmashcraft.Commands.CommandCreation;
import net.supersmashcraft.Commands.CommandDelete;
import net.supersmashcraft.Commands.CommandJoin;
import net.supersmashcraft.Commands.CommandLeave;
import net.supersmashcraft.Commands.CommandModify;
import net.supersmashcraft.Commands.MainCommand;
import net.supersmashcraft.Managers.ArenaManager;
import net.supersmashcraft.Managers.ClassManager;
import net.supersmashcraft.Managers.CreationManager;
import net.supersmashcraft.Managers.FileManager;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 
 * @author Paul, Breezeyboy, Max_The_Link_Fan, chasechocolate
 * 
 */
public class SSCPlugin extends JavaPlugin {
   public static SSCPlugin instance;
   public PluginManager pm = Bukkit.getServer().getPluginManager();
   public Logger log;
   
   @Override
   public void onEnable() {
      FileManager.dataFolder = getDataFolder();
      instance = this;
      log = Bukkit.getServer().getLogger();
      
      getCommand("ssc").setExecutor(new MainCommand());
      MainCommand.registerCommand(new CommandJoin());
      MainCommand.registerCommand(new CommandCreation());
      MainCommand.registerCommand(new CommandLeave());
      MainCommand.registerCommand(new CommandDelete());
      MainCommand.registerCommand(new CommandModify());
      
      ClassManager.registerClass(new ClassKirby());
      ClassManager.registerClass(new ClassBowser());
      ClassManager.registerClass(new ClassRoy());
      ClassManager.registerClass(new ClassMario());
      ClassManager.registerClass(new ClassFox());
      ClassManager.registerClass(new ClassLink());
      ClassManager.registerClass(new ClassLuigi());
      
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
