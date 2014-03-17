 package me.Ad237.oitc;
 
 import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import me.Ad237.oitc.utils.LocationUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
 
 public class SignManager
   implements Runnable
 {
   private static SignManager instance = null;
   private OITQ plugin;
   
   public static SignManager getInstance()
   {
     if (instance == null) {
       instance = new SignManager();
     }
     return instance;
   }
   
   private HashMap<Sign, Arena> signs = new HashMap<Sign, Arena>();
   
   public void setup(OITQ plugin)
   {
     this.plugin = plugin;
     
     Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, 0L, 20L);
     
     FileConfiguration config = plugin.getConfig();
     if (config.getStringList("signs") == null) {
       return;
     }
     for (String name : config.getStringList("signs")) {
       try
       {
         String[] data = name.split("#");
         String arena = data[0];
         Location loc = LocationUtils.toLocation(data[1]);
         if ((arena == null) || (loc == null))
         {
           OITQ.info("Could not load sign: Invalid info.");
           return;
         }
         Block b = loc.getBlock();
         if (!(b.getState() instanceof Sign))
         {
           OITQ.info("Could not load sign: Block not an instance of a sign.");
           return;
         }
         Sign sign = (Sign)b.getState();
         this.signs.put(sign, ArenaManager.getInstance().getArena(arena));
       }
       catch (Exception e)
       {
         OITQ.info("Could not load sign: " + e.getMessage());
       }
     }
   }
   
   public boolean isArenaSign(Sign s)
   {
     return this.signs.containsKey(s);
   }
   
   public Arena getArena(Sign s)
   {
     return (Arena)this.signs.get(s);
   }
   
   public void removeSign(Sign s)
   {
     if (this.signs.get(s) == null)
     {
       this.signs.remove(s);
       return;
     }
     FileConfiguration config = this.plugin.getConfig();
     
     List<String> data = config.getStringList("signs");
     data.remove(((Arena)this.signs.get(s)).getName() + "#" + LocationUtils.toString(s.getBlock().getLocation()));
     config.set("signs", data);
     this.plugin.saveConfig();
     
     this.signs.remove(s);
   }
   
   public void addSign(Sign s, Arena a)
   {
     this.signs.put(s, a);
     
     FileConfiguration config = this.plugin.getConfig();
     
     List<String> data = new ArrayList<String>();
     if (config.getStringList("signs") != null) {
       data = config.getStringList("signs");
     }
     data.add(a.getName() + "#" + LocationUtils.toString(s.getBlock().getLocation()));
     config.set("signs", data);
     this.plugin.saveConfig();
   }
   
   public void run()
   {
     for (Entry<Sign, Arena> sign : this.signs.entrySet()) {
       if (sign.getValue() == null) {
         formatUnknownSign((Sign)sign.getKey());
       } else {
         ((Arena)sign.getValue()).formatSign((Sign)sign.getKey());
       }
     }
   }
   
   public void formatUnknownSign(Sign s)
   {
     s.setLine(0, OITQ.PREFIX);
     s.setLine(1, "Unknown");
     s.setLine(2, "Arena");
     s.setLine(3, ":(");
     s.update();
   }
 }