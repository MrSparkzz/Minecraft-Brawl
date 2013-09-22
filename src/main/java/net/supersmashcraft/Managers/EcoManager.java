package net.supersmashcraft.Managers;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * The Class EcoManager which handles money in the plugin.
 */
public class EcoManager {
   
   /** The economy variable which we use to hook into vault. */
   private static Economy econ;
   
   /**
    * Give money to a player.
    * 
    * @param p
    *           the player
    * @param cash
    *           the cash to give
    * @throws ClassNotFoundException
    */
   public static void giveMoney(Player p, double cash) throws ClassNotFoundException {
      if (econ == null) {
         if (!setupEconomy()) {
            Bukkit.getLogger().info("Could not setup economy!");
            throw new ClassNotFoundException("Vault");
         }
      }
      econ.depositPlayer(p.getName(), cash);
   }
   
   /**
    * Checks if the player has the money.
    * 
    * @param p
    *           the player
    * @param cash
    *           the cash to check for
    * @return true, if successful
    * @throws ClassNotFoundException
    */
   public static boolean hasMoney(Player p, double cash) throws ClassNotFoundException {
      if (econ == null) {
         if (!setupEconomy()) {
            Bukkit.getLogger().info("Could not setup economy!");
            throw new ClassNotFoundException("Vault");
         }
      }
      return econ.has(p.getName(), cash);
   }
   
   /**
    * Removes the money from the player.
    * 
    * @param p
    *           the player
    * @param cash
    *           the cash to remove
    * @throws ClassNotFoundException
    */
   public static void removeMoney(Player p, double cash) throws ClassNotFoundException {
      if (econ == null) {
         if (!setupEconomy()) {
            Bukkit.getLogger().info("Could not setup economy!");
            throw new ClassNotFoundException("Vault");
         }
      }
      econ.withdrawPlayer(p.getName(), cash);
   }
   
   /**
    * Sets up the economy variable so we can hook into vault.
    * 
    * @return true, if successful
    */
   private static boolean setupEconomy() {
      if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
         return false;
      }
      RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager()
               .getRegistration(Economy.class);
      if (rsp == null) {
         return false;
      }
      econ = rsp.getProvider();
      return econ != null;
   }
}
