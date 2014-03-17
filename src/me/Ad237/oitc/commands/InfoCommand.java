 package me.Ad237.oitc.commands;
 
 import me.Ad237.oitc.OITQ;
 import org.bukkit.ChatColor;
 import org.bukkit.entity.Player;
 
 public class InfoCommand
   implements SubCommand
 {
   public String getName()
   {
     return "info";
   }
   
   public String getPermission()
   {
     return null;
   }
   
   public String getUsage()
   {
     return "/oitq info";
   }
   
   public String getDescription()
   {
     return "Display info about the plugin!";
   }
   
   public String[] getAliases()
   {
     return new String[] { "about" };
   }
   
   public void onCommand(Player p, String[] args)
   {
     OITQ.sendMsg(p, ChatColor.AQUA + "OITC Version " + OITQ.getInstance().getDescription().getVersion() + " by Ad237");
   }
 }