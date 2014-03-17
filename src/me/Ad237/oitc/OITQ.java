 package me.Ad237.oitc;
 
 import java.io.IOException;
 import me.Ad237.oitc.listeners.DamageListener;
 import me.Ad237.oitc.listeners.PlayerListener;
 import me.Ad237.oitc.listeners.SignListener;
 import org.bukkit.Bukkit;
 import org.bukkit.ChatColor;
 import org.bukkit.entity.Player;
 import org.bukkit.plugin.java.JavaPlugin;
 
 public class OITQ
   extends JavaPlugin
 {
   public static String PREFIX = ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "OITQ" + ChatColor.GRAY + "]";
   
   public void onEnable()
   {
     saveDefaultConfig();
     
     Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
     Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
     Bukkit.getPluginManager().registerEvents(new SignListener(), this);
     
     ArenaManager.getInstance().setup(this);
     StatsManager.getInstance().setup(this);
     SignManager.getInstance().setup(this);
     
     getCommand("oitq").setExecutor(new CommandManager());
     
     Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new ArenaTask(), 0L, 20L);
     try
     {
       Metrics metrics = new Metrics(this);
       metrics.start();
     }
     catch (IOException e) {}
   }
   
   public void onDisable() {}
   
   public static OITQ getInstance()
   {
     return (OITQ)Bukkit.getPluginManager().getPlugin("OITQ");
   }
   
   public static void sendMsg(Player p, String msg)
   {
     p.sendMessage(PREFIX + " " + msg);
   }
   
   public static void info(String msg)
   {
     getInstance().getLogger().info(msg);
   }
 }