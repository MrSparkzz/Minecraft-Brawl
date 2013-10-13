package org.infernogames.mb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.infernogames.mb.Abilities.MBAbility;

/**
 * 
 * @author Paul, Breezeyboy
 * 
 *         This is the main class for.. well classes. By using this class as a
 *         template, I can load classes through file and code.
 */
public class MBClass {
   
   private List<ItemStack> items = new ArrayList<ItemStack>();
   protected String name;
   protected String desc;
   protected Material icon;
   protected int data;
   
   public ItemStack boot = new ItemStack(Material.AIR), leg = new ItemStack(Material.AIR), chest = new ItemStack(
            Material.AIR), helm = new ItemStack(Material.AIR);
   
   public MBClass(final String name, final String desc, final Material icon) {
      this(name, desc, icon, 0);
   }
   
   public MBClass(final String name, final String desc, final Material icon, int data) {
      this.name = name;
      this.desc = desc;
      this.icon = icon;
      this.data = data;
   }
   
   public String name() {
      return name;
   }
   
   public String desc() {
      return desc;
   }
   
   public Material icon() {
      return icon;
   }
   
   public void onPlayerSpawn(Player p) {
      // Code to be overriden
   }
   
   public void onPlayerDespawn(Player p) {
      // Code to be overriden
   }
   
   private HashMap<MBAbility, String[]> abilities = new HashMap<MBAbility, String[]>();
   
   public void addAbility(MBAbility a, String[] args) {
      abilities.put(a, args);
   }
   
   public HashMap<MBAbility, String[]> getAbilities() {
      return abilities;
   }
   
   public boolean hasAbility(String name) {
      for (MBAbility a : abilities.keySet()) {
         if (a != null && a.name().equalsIgnoreCase(name)) {
            return true;
         }
      }
      return false;
   }
   
   public MBAbility getAbility(String name) {
      for (MBAbility a : abilities.keySet()) {
         if (a.name().equalsIgnoreCase(name)) {
            return a;
         }
      }
      return null;
   }
   
   public String[] getAbilityArguments(String name) {
      return abilities.get(getAbility(name));
   }
   
   public void setupPlayer(final Player p) {
      p.getInventory().clear();
      for (ItemStack item : items)
         p.getInventory().addItem(item);
      p.getInventory().setHelmet(helm);
      p.getInventory().setChestplate(chest);
      p.getInventory().setLeggings(leg);
      p.getInventory().setBoots(boot);
      onPlayerSpawn(p);
   }
   
   public void item(final Material ma) {
      final ItemStack i = new ItemStack(ma, 1);
      i.addUnsafeEnchantment(Enchantment.KNOCKBACK, 3);
      items.add(i);
   }
   
   public ItemStack armor(final Material ma, final Color c) {
      final ItemStack i = new ItemStack(ma, 1);
      final LeatherArmorMeta m = (LeatherArmorMeta) i.getItemMeta();
      m.setColor(c);
      i.setItemMeta(m);
      return i;
   }
   
   public ItemStack colorArmor(final Material ma, final Color c) {
      final ItemStack i = new ItemStack(ma, 1);
      final LeatherArmorMeta m = (LeatherArmorMeta) i.getItemMeta();
      m.setColor(c);
      i.setItemMeta(m);
      return i;
   }
   
   public void addItem(ItemStack... i) {
      for (ItemStack item : i) {
         items.add(item);
      }
   }
   
}