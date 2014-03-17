package me.Ad237.oitc.commands;

import org.bukkit.entity.Player;

public abstract interface SubCommand
{
  public abstract String getName();
  
  public abstract String getPermission();
  
  public abstract String getUsage();
  
  public abstract String getDescription();
  
  public abstract String[] getAliases();
  
  public abstract void onCommand(Player paramPlayer, String[] paramArrayOfString);
}