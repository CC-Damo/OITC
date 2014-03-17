 package me.Ad237.oitc.listeners;
 
 import me.Ad237.oitc.Arena;
 import me.Ad237.oitc.ArenaManager;
 import me.Ad237.oitc.OITQ;
 import me.Ad237.oitc.SignManager;
 import org.bukkit.ChatColor;
 import org.bukkit.block.Block;
 import org.bukkit.block.Sign;
 import org.bukkit.entity.Player;
 import org.bukkit.event.EventHandler;
 import org.bukkit.event.Listener;
 import org.bukkit.event.block.Action;
 import org.bukkit.event.block.BlockBreakEvent;
 import org.bukkit.event.block.SignChangeEvent;
 import org.bukkit.event.player.PlayerInteractEvent;
 
 public class SignListener
   implements Listener
 {
   @EventHandler
   public void onBreak(BlockBreakEvent event)
   {
     Player p = event.getPlayer();
     Block b = event.getBlock();
     if ((b.getState() instanceof Sign))
     {
       Sign s = (Sign)b.getState();
       if (SignManager.getInstance().isArenaSign(s))
       {
         if (!p.hasPermission("oitq.signs"))
         {
           OITQ.sendMsg(p, ChatColor.RED + "You do not have permission to break signs.");
           event.setCancelled(true);
           return;
         }
         SignManager.getInstance().removeSign(s);
       }
     }
   }
   
   @EventHandler
   public void onPlace(SignChangeEvent event)
   {
     Player p = event.getPlayer();
     Sign s = (Sign)event.getBlock().getState();
     if ((event.getLine(0) != null) && (event.getLine(0).equalsIgnoreCase("OITQ")))
     {
       if (!p.hasPermission("oitq.signs"))
       {
         OITQ.sendMsg(p, ChatColor.RED + "You do not have permission to place signs.");
         event.setCancelled(true);
         return;
       }
       if (event.getLine(1) != null)
       {
         Arena a = ArenaManager.getInstance().getArena(event.getLine(1));
         if (a == null)
         {
           OITQ.sendMsg(p, ChatColor.RED + "Unknown Arena.");
           return;
         }
         SignManager.getInstance().addSign(s, a);
         OITQ.sendMsg(p, ChatColor.GREEN + "Sign created!");
       }
     }
   }
   
   @EventHandler
   public void onRightClick(PlayerInteractEvent event)
   {
     Player p = event.getPlayer();
     if ((event.getAction() == Action.RIGHT_CLICK_BLOCK) && 
       ((event.getClickedBlock().getState() instanceof Sign)))
     {
       Sign s = (Sign)event.getClickedBlock().getState();
       if (SignManager.getInstance().isArenaSign(s))
       {
         Arena a = SignManager.getInstance().getArena(s);
         if (a != null) {
           a.addPlayer(p);
         }
       }
     }
   }
 }