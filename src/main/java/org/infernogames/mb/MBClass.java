package org.infernogames.mb;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.infernogames.mb.Abilities.MBAbility;

public class MBClass {
   
   private List<ItemStack> items = new ArrayList<ItemStack>();
   protected String name;
   protected String desc;
   protected Material icon;
   protected int data;
   
   private final Material[] armor = new Material[] { Material.LEATHER_BOOTS, Material.LEATHER_LEGGINGS,
            Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET, Material.IRON_BOOTS, Material.IRON_LEGGINGS,
            Material.IRON_CHESTPLATE, Material.IRON_HELMET, Material.GOLD_BOOTS, Material.GOLD_LEGGINGS,
            Material.GOLD_CHESTPLATE, Material.GOLD_HELMET, Material.DIAMOND_BOOTS, Material.DIAMOND_LEGGINGS,
            Material.DIAMOND_CHESTPLATE, Material.DIAMOND_HELMET };
   
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
      
   }
   
   public void onPlayerDespawn(Player p) {
      
   }
   
   private List<MBAbility> abilities = new ArrayList<MBAbility>();
   
   public void addAbility(MBAbility a) {
      abilities.add(a);
   }
   
   public List<MBAbility> getAbilities() {
      return abilities;
   }
   
   public boolean hasAbility(String name){
      for(MBAbility a : abilities){
         if(a.name().equalsIgnoreCase(name)){
            return true;
         }
      }
      return false;
   }
   
   public MBAbility getAbility(String name){
      for(MBAbility a : abilities){
         if(a.name().equalsIgnoreCase(name)){
            return a;
         }
      }
      return null;
   }
   
   public void setupPlayer(final Player p) {
      p.getInventory().clear();
      final List<ItemStack> pArmor = new ArrayList<ItemStack>();
      for (final ItemStack item : items) {
         boolean isArmor = false;
         for (final Material a : armor) {
            if (a == item.getType()) {
               isArmor = true;
               pArmor.add(item);
               break;
            }
         }
         if (!isArmor) {
            p.getInventory().addItem(item);
         }
      }
      ItemStack[] items = new ItemStack[pArmor.size()];
      pArmor.toArray(items);
      p.getInventory().setArmorContents(items);
      onPlayerSpawn(p);
   }
   
   public void item(final Material ma) {
      final ItemStack i = new ItemStack(ma, 1);
      i.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
      items.add(i);
   }
   
   public void armor(final Material ma, final Color c) {
      final ItemStack i = new ItemStack(ma, 1);
      final LeatherArmorMeta m = (LeatherArmorMeta) i.getItemMeta();
      m.setColor(c);
      i.setItemMeta(m);
      items.add(i);
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