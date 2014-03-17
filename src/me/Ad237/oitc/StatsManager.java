 package me.Ad237.oitc;
 
 import java.io.File;
 import java.io.IOException;
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.configuration.file.YamlConfiguration;
 
 public class StatsManager
 {
   private static StatsManager instance;
   private File statsFile;
   private FileConfiguration statsConfig;
   
   public static StatsManager getInstance()
   {
     if (instance == null) {
       instance = new StatsManager();
     }
     return instance;
   }
   
   public void setup(OITQ plugin)
   {
     if (!plugin.getDataFolder().exists()) {
       plugin.getDataFolder().mkdir();
     }
     this.statsFile = new File(plugin.getDataFolder(), "stats.yml");
     if (!this.statsFile.exists()) {
       try
       {
         this.statsFile.createNewFile();
       }
       catch (IOException e)
       {
         plugin.getLogger().info("Failed to create config: " + e.getMessage());
       }
     }
     this.statsConfig = YamlConfiguration.loadConfiguration(this.statsFile);
   }
   
   public void setStat(String player, String stat, int value)
   {
     this.statsConfig.set(player + "." + stat, Integer.valueOf(value));
     try
     {
       this.statsConfig.save(this.statsFile);
     }
     catch (IOException e)
     {
       e.printStackTrace();
     }
   }
   
   public int getStat(String player, String stat)
   {
     return this.statsConfig.getInt(player + "." + stat);
   }
 }