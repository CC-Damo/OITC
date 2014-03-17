 package me.Ad237.oitc.utils;
 
 import org.bukkit.Bukkit;
 import org.bukkit.GameMode;
 import org.bukkit.Location;
 import org.bukkit.entity.Player;
 import org.bukkit.inventory.ItemStack;
 
 public class PlayerData
 {
   private String name;
   private int level;
   private float exp;
   private Location loc;
   private ItemStack[] inv;
   private ItemStack[] armor;
   private GameMode gm;
   
   public PlayerData(Player p)
   {
     this.name = p.getName();
     
     this.level = p.getLevel();
     this.exp = p.getExp();
     this.loc = p.getLocation();
     this.inv = p.getInventory().getContents();
     this.armor = p.getInventory().getArmorContents();
     this.gm = p.getGameMode();
   }
   
   public void restorePlayer()
   {
     Player p = Bukkit.getPlayerExact(this.name);
     
     p.setHealth(20.0D);
     p.setFoodLevel(20);
     p.setLevel(this.level);
     p.setExp(this.exp);
     p.teleport(this.loc);
     p.getInventory().setContents(this.inv);
     p.getInventory().setArmorContents(this.armor);
     p.setGameMode(this.gm);
   }
 }
