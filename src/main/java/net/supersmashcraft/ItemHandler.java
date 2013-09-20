package net.supersmashcraft;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemHandler {
   
   @SuppressWarnings("deprecation")
   public static String toString(ItemStack i) {
      StringBuilder f = new StringBuilder();
      f.append("type=" + i.getTypeId() + ";");
      f.append("dura=" + i.getDurability() + ";");
      f.append("amount=" + i.getAmount() + ";");
      if (!i.getEnchantments().isEmpty()) {
         f.append("enchantments=");
         int in = 1;
         for (Entry<Enchantment, Integer> key : i.getEnchantments().entrySet()) {
            f.append(key.getKey().getId() + ":" + key.getValue());
            if (in != i.getEnchantments().size()) {
               f.append("&");
            }
            in++;
         }
         f.append(";");
      }
      if (i.hasItemMeta()) {
         ItemMeta m = i.getItemMeta();
         if (m.hasDisplayName()) {
            f.append("cName=" + m.getDisplayName() + ";");
         }
      }
      return f.toString();
   }
   
   @SuppressWarnings("deprecation")
   public static ItemStack fromString(String s) {
      ItemStack i;
      int type = 0;
      short dura = 0;
      int amount = 0;
      Map<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
      String cName = null;
      for (String d : s.split(";")) {
         String[] id = d.split("=");
         if (id[0].equalsIgnoreCase("type")) {
            type = Integer.parseInt(id[1]);
         } else if (id[0].equalsIgnoreCase("dura")) {
            dura = Short.parseShort(id[1]);
         } else if (id[0].equalsIgnoreCase("amount")) {
            amount = Integer.parseInt(id[1]);
         } else if (id[0].equalsIgnoreCase("enchantments")) {
            for (String en : id[1].split("&")) {
               String[] ench = en.split(":");
               enchants.put(Enchantment.getById(Integer.parseInt(ench[0])), Integer.parseInt(ench[1]));
            }
         } else if (id[0].equalsIgnoreCase("cName")) {
            cName = id[1];
         }
      }
      i = new ItemStack(type, amount);
      if (dura != 0) {
         i.setDurability(dura);
      }
      if (cName != null) {
         i.getItemMeta().setDisplayName(cName);
      }
      i.addUnsafeEnchantments(enchants);
      return i;
   }
}
