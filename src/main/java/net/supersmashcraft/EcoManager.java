package net.supersmashcraft;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;


public class EcoManager {
   private static Economy econ;
   public static void giveMoney(Player p, double cash){
      if(econ == null){
         if(!setupEconomy()){
            Bukkit.getLogger().info("Could not setup economy!");
         }
      }
      econ.depositPlayer(p.getName(), cash);
   }
   public static boolean hasMoney(Player p, double cash){
      if(econ == null){
         if(!setupEconomy()){
            Bukkit.getLogger().info("Could not setup economy!");
         }
      }
      return econ.has(p.getName(), cash);
   }
   public static void removeMoney(Player p, double cash){
      if(econ == null){
         if(!setupEconomy()){
            Bukkit.getLogger().info("Could not setup economy!");
         }
      }
      econ.withdrawPlayer(p.getName(), cash);
   }
   private static boolean setupEconomy() {
      if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
          return false;
      }
      RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
      if (rsp == null) {
          return false;
      }
      econ = rsp.getProvider();
      return econ != null;
  }
}
