 package me.Ad237.oitc.commands;
 
 import me.Ad237.oitc.Arena;
 import me.Ad237.oitc.ArenaManager;
 import me.Ad237.oitc.OITQ;
 import org.bukkit.ChatColor;
 import org.bukkit.entity.Player;
 
 public class QuitCommand
   implements SubCommand
 {
   public String getName()
   {
     return "quit";
   }
   
   public String getPermission()
   {
     return "oitq.quit";
   }
   
   public String getUsage()
   {
     return "/oitq quit";
   }
   
   public String getDescription()
   {
     return "Leave an Arena!";
   }
   
   public String[] getAliases()
   {
     return new String[] { "leave", "back" };
   }
   
   public void onCommand(Player p, String[] args)
   {
     Arena a = ArenaManager.getInstance().getArena(p);
     if (a == null)
     {
       OITQ.sendMsg(p, ChatColor.RED + "You are not in a game!");
       return;
     }
     a.removePlayer(p);
   }
 }