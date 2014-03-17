 package me.Ad237.oitc.commands;
 
 import me.Ad237.oitc.OITQ;
 import me.Ad237.oitc.StatsManager;
 import org.bukkit.ChatColor;
 import org.bukkit.entity.Player;
 
 public class StatsCommand
   implements SubCommand
 {
   public String getName()
   {
     return "stats";
   }
   
   public String getPermission()
   {
     return "oitq.stats";
   }
   
   public String getUsage()
   {
     return "/oitq stats";
   }
   
   public String getDescription()
   {
     return "Display your stats.";
   }
   
   public String[] getAliases()
   {
     return new String[0];
   }
   
   public void onCommand(Player p, String[] args)
   {
     OITQ.sendMsg(p, ChatColor.AQUA + "Your Stats:");
     OITQ.sendMsg(p, ChatColor.YELLOW + "Kills: " + ChatColor.GREEN + StatsManager.getInstance().getStat(p.getName(), "kills"));
     OITQ.sendMsg(p, ChatColor.YELLOW + "Deaths: " + ChatColor.GREEN + StatsManager.getInstance().getStat(p.getName(), "deaths"));
     OITQ.sendMsg(p, ChatColor.YELLOW + "Wins: " + ChatColor.GREEN + StatsManager.getInstance().getStat(p.getName(), "wins"));
   }
 }