 package me.Ad237.oitc.commands;
 
 import me.Ad237.oitc.Arena;
 import me.Ad237.oitc.ArenaManager;
 import me.Ad237.oitc.OITQ;
 import org.bukkit.ChatColor;
 import org.bukkit.entity.Player;
 
 public class ArenasCommand
   implements SubCommand
 {
   public String getName()
   {
     return "arenas";
   }
   
   public String getPermission()
   {
     return "oitq.arenas";
   }
   
   public String getUsage()
   {
     return "/oitq arenas";
   }
   
   public String getDescription()
   {
     return "Display a list of arenas!";
   }
   
   public String[] getAliases()
   {
     return new String[0];
   }
   
   public void onCommand(Player p, String[] args)
   {
     for (Arena a : ArenaManager.getInstance().getArenas()) {
       OITQ.sendMsg(p, ChatColor.GOLD + a.getName() + ChatColor.GRAY + " - " + a.getStage().getDisplayName() + ChatColor.GRAY + " - " + ChatColor.WHITE + "(" + a.getPlayers().size() + "/" + a.getMaxPlayers() + ")");
     }
   }
 }