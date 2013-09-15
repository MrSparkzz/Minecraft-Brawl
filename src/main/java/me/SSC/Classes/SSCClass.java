package me.SSC.Classes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class SSCClass {

	private ItemStack[] items;
	public String name;

	private Material[] armor = new Material[] { Material.LEATHER_BOOTS,
			Material.LEATHER_LEGGINGS, Material.LEATHER_CHESTPLATE,
			Material.LEATHER_HELMET, Material.IRON_BOOTS,
			Material.IRON_LEGGINGS, Material.IRON_CHESTPLATE,
			Material.IRON_HELMET, Material.GOLD_BOOTS, Material.GOLD_LEGGINGS,
			Material.GOLD_CHESTPLATE, Material.GOLD_HELMET,
			Material.DIAMOND_BOOTS, Material.DIAMOND_LEGGINGS,
			Material.DIAMOND_CHESTPLATE, Material.DIAMOND_HELMET };

	public SSCClass(String name, ItemStack... items) {
		this.items = items;
		this.name = name;
	}

	public void setupPlayer(Player p) {
		List<ItemStack> pArmor = new ArrayList<ItemStack>();
		for (ItemStack item : items) {
			boolean isArmor = false;
			for (Material a : armor) {
				if (a == item.getType()) {
					isArmor = true;
					pArmor.add(item);
					break;
				}
			}
			if(!isArmor){
				p.getInventory().addItem(item);
			}
		}
		p.getInventory().setArmorContents((ItemStack[]) pArmor.toArray());
	}
	
}
