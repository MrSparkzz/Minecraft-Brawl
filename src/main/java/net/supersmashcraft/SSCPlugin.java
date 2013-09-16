package net.supersmashcraft;

import java.util.logging.Logger;

import net.supersmashcraft.ClassUtils.DoubleJump;
import net.supersmashcraft.Commands.CommandJoin;
import net.supersmashcraft.Commands.MainCommand;

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
      
      this.registerListener(new DoubleJump());
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
