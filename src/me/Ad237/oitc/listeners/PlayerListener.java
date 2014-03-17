 package me.Ad237.oitc.listeners;
 
 import me.Ad237.oitc.Arena;
import me.Ad237.oitc.ArenaManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
 
 public class PlayerListener
   implements Listener
 {
   @EventHandler
   public void onQuit(PlayerQuitEvent event)
   {
     Player p = event.getPlayer();
     Arena a = ArenaManager.getInstance().getArena(p);
     if (a != null) {
       a.removePlayer(p);
     }
   }
   
   @EventHandler
   public void onBreak(BlockBreakEvent event)
   {
     Player p = event.getPlayer();
     if (ArenaManager.getInstance().isInGame(p)) {
       event.setCancelled(true);
     }
   }
   
   @SuppressWarnings("deprecation")
@EventHandler
   public void onPlace(BlockPlaceEvent event)
   {
     Player p = event.getPlayer();
     if (ArenaManager.getInstance().isInGame(p))
     {
       event.setCancelled(true);
       p.updateInventory();
     }
   }
   
   @EventHandler
   public void onDrop(PlayerDropItemEvent event)
   {
     Player p = event.getPlayer();
     if (ArenaManager.getInstance().isInGame(p)) {
       event.setCancelled(true);
     }
   }
   
   @EventHandler
   public void onPickup(PlayerPickupItemEvent event)
   {
     Player p = event.getPlayer();
     if (ArenaManager.getInstance().isInGame(p)) {
       event.setCancelled(true);
     }
   }
   
   @EventHandler
   public void onHunger(FoodLevelChangeEvent event)
   {
     if ((event.getEntity() instanceof Player))
     {
       Player p = (Player)event.getEntity();
       if (ArenaManager.getInstance().isInGame(p)) {
         event.setCancelled(true);
       }
     }
   }
 }