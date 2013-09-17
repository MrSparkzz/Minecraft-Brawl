package net.supersmashcraft;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.supersmashcraft.Arena.Arena;
import net.supersmashcraft.ClassUtils.DoubleJump;
import net.supersmashcraft.Commands.CommandJoin;
import net.supersmashcraft.Commands.MainCommand;
import net.supersmashcraft.Managers.ArenaManager;
import net.supersmashcraft.Managers.CreationManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
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
      
      this.registerListener(new DoubleJump());
      log.info("[SuperSmashCraft] v" + this.getDescription().getVersion() + " enabled.");
      
      for (String s : getConfig().getConfigurationSection("Arenas").getKeys(false)) {
         ArenaManager.addArena(CreationManager.createArena(s));
      }
   }
   
   @Override
   public void onDisable() {
      log.info("[SuperSmashCraft] v" + this.getDescription().getVersion() + " disabled.");
   }
   
   private void registerListener(final Listener listener) {
      pm.registerEvents(listener, this);
   }
}
