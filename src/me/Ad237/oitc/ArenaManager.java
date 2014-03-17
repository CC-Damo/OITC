 package me.Ad237.oitc;
 
 import java.util.ArrayList;

import me.Ad237.oitc.utils.LocationUtils;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
 
 public class ArenaManager
 {
   private static ArenaManager instance = null;
   
   public static ArenaManager getInstance()
   {
     if (instance == null) {
       instance = new ArenaManager();
     }
     return instance;
   }
   
   private ArrayList<Arena> arenas = new ArrayList<Arena>();
   
   public void setup(OITQ plugin)
   {
     FileConfiguration config = plugin.getConfig();
     if (config.getConfigurationSection("arenas") == null) {
       return;
     }
     for (String name : config.getConfigurationSection("arenas").getKeys(false))
     {
       String path = "arenas." + name + ".";
       if (!config.getBoolean(path + "enabled"))
       {
         OITQ.info("Skipping arena \"" + name + "\" because it is disabled.");
       }
       else
       {
         int min = config.getInt(path + "min");
         int max = config.getInt(path + "max");
         Location lobby = LocationUtils.toLocation(config.getString(path + "lobby"));
         ArrayList<Location> spawns = new ArrayList<Location>();
         for (String loc : config.getStringList(path + "spawns"))
         {
           Location l = LocationUtils.toLocation(loc);
           if (l != null) {
             spawns.add(l);
           }
         }
         if (lobby == null) {
           OITQ.info(name + " could not be loaded. Missing info in the config.");
         }
         this.arenas.add(new Arena(name, lobby, spawns, min, max));
         OITQ.info(name + " has been loaded!");
       }
     }
   }
   
   public Arena getArena(String name)
   {
     for (Arena a : this.arenas) {
       if (a.getName().equalsIgnoreCase(name)) {
         return a;
       }
     }
     return null;
   }
   
   public Arena getArena(Player p)
   {
     for (Arena a : this.arenas) {
       if (a.containsPlayer(p)) {
         return a;
       }
     }
     return null;
   }
   
   public boolean isInGame(Player p)
   {
     return getArena(p) != null;
   }
   
   public ArrayList<Arena> getArenas()
   {
     return this.arenas;
   }
   
   public void createArena(String name, int min, int max, Location lobby)
   {
     this.arenas.add(new Arena(name, lobby, new ArrayList<Location>(), min, max));
     
     FileConfiguration config = OITQ.getInstance().getConfig();
     String path = "arenas." + name + ".";
     
     config.set(path + "enabled", Boolean.valueOf(true));
     config.set(path + "min", Integer.valueOf(min));
     config.set(path + "max", Integer.valueOf(max));
     config.set(path + "lobby", LocationUtils.toString(lobby));
     config.set(path + "spawns", new ArrayList<Object>());
     
     OITQ.getInstance().saveConfig();
   }
 }