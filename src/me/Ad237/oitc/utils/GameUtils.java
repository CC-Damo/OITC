 package me.Ad237.oitc.utils;
 
 import org.bukkit.Material;
 import org.bukkit.entity.Player;
 import org.bukkit.inventory.ItemStack;
 
 public class GameUtils
 {
   public static void equipPlayer(Player p)
   {
     p.getInventory().clear();
     p.getInventory().setArmorContents(null);
     
     p.getInventory().setItem(0, new ItemStack(Material.WOOD_SWORD));
     p.getInventory().setItem(1, new ItemStack(Material.BOW));
     p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.ARROW) });
   }
 }
