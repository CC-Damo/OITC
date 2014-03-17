 package me.Ad237.oitc.utils;
 
 import org.bukkit.ChatColor;
 
 public enum GameStage
 {
   LOBBY(ChatColor.GREEN + "Lobby"),  INGAME(ChatColor.RED + "In Game");
   
   private String display;
   
   private GameStage(String display)
   {
     this.display = display;
   }
   
   public String getDisplayName()
   {
     return this.display;
   }
 }