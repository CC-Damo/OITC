 package me.Ad237.oitc.commands;
 
 import java.util.List;
 import me.Ad237.oitc.Arena;
 import me.Ad237.oitc.ArenaManager;
 import me.Ad237.oitc.OITQ;
 import me.Ad237.oitc.utils.LocationUtils;
 import org.bukkit.ChatColor;
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.entity.Player;
 
 public class SpawnCommand
   implements SubCommand
 {
   public String getName()
   {
     return "addspawn";
   }
   
   public String getPermission()
   {
     return "oitq.addspawn";
   }
   
   public String getUsage()
   {
     return "/oitq addspawn (Arena)";
   }
   
   public String getDescription()
   {
     return "Add a spawn to an arena!";
   }
   
   public String[] getAliases()
   {
     return new String[0];
   }
   
   public void onCommand(Player p, String[] args)
   {
     if (args.length == 0)
     {
       OITQ.sendMsg(p, ChatColor.RED + "Please specify an arena!");
       return;
     }
     Arena a = ArenaManager.getInstance().getArena(args[0]);
     if (a == null)
     {
       OITQ.sendMsg(p, ChatColor.RED + "The arena \"" + args[0] + "\" does the exist!");
       return;
     }
     a.addSpawn(p.getLocation());
     
     FileConfiguration config = OITQ.getInstance().getConfig();
     
     List<String> spawns = config.getStringList("arenas." + a.getName() + ".spawns");
     spawns.add(LocationUtils.toString(p.getLocation()));
     
     config.set("arenas." + a.getName() + ".spawns", spawns);
     
     OITQ.getInstance().saveConfig();
     
     OITQ.sendMsg(p, ChatColor.GREEN + "Spawn added!");
   }
 }