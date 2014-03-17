 package me.Ad237.oitc.commands;
 
 import me.Ad237.oitc.Arena;
 import me.Ad237.oitc.ArenaManager;
 import me.Ad237.oitc.OITQ;
 import org.bukkit.ChatColor;
 import org.bukkit.entity.Player;
 
 public class JoinCommand
   implements SubCommand
 {
   public String getName()
   {
     return "join";
   }
   
   public String getPermission()
   {
     return "oitq.join";
   }
   
   public String getUsage()
   {
     return "/oitq join (Arena)";
   }
   
   public String getDescription()
   {
     return "Join an Arena!";
   }
   
   public String[] getAliases()
   {
     return new String[0];
   }
   
   public void onCommand(Player p, String[] args)
   {
     if (args.length == 0)
     {
       OITQ.sendMsg(p, ChatColor.RED + getUsage());
       return;
     }
     Arena a = ArenaManager.getInstance().getArena(args[0]);
     if (a == null)
     {
       OITQ.sendMsg(p, ChatColor.RED + "The arena \"" + args[0] + "\" does the exist!");
       return;
     }
     a.addPlayer(p);
   }
 }