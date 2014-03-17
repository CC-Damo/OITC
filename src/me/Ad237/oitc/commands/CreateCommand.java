 package me.Ad237.oitc.commands;
 
 import me.Ad237.oitc.Arena;
 import me.Ad237.oitc.ArenaManager;
 import me.Ad237.oitc.OITQ;
 import org.bukkit.ChatColor;
 import org.bukkit.entity.Player;
 
 public class CreateCommand
   implements SubCommand
 {
   public String getName()
   {
     return "create";
   }
   
   public String getPermission()
   {
     return "oitq.create";
   }
   
   public String getUsage()
   {
     return "/oitq create (Name) (Min Players) (Max Players)";
   }
   
   public String getDescription()
   {
     return "Create an arena!";
   }
   
   public String[] getAliases()
   {
     return new String[0];
   }
   
   public void onCommand(Player p, String[] args)
   {
     if (args.length != 3)
     {
       OITQ.sendMsg(p, ChatColor.RED + getUsage());
       return;
     }
     try
     {
       String name = args[0];
       int min = Integer.parseInt(args[1]);
       int max = Integer.parseInt(args[2]);
       
       Arena a = ArenaManager.getInstance().getArena(name);
       if (a != null)
       {
         OITQ.sendMsg(p, ChatColor.RED + "An arena with the name \"" + name + "\" already exists!");
         return;
       }
       ArenaManager.getInstance().createArena(name, min, max, p.getLocation());
       
       OITQ.sendMsg(p, ChatColor.GREEN + "Arena Created! To add spawns type \"/oitq addspawn " + name + "\".");
     }
     catch (Exception e)
     {
       OITQ.sendMsg(p, ChatColor.RED + "Invalid Arguments!");
     }
   }
 }