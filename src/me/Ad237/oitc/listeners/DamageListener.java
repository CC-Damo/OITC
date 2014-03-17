 package me.Ad237.oitc.listeners;
 
 import me.Ad237.oitc.Arena;
 import me.Ad237.oitc.ArenaManager;
 import me.Ad237.oitc.OITQ;
 import me.Ad237.oitc.utils.GameStage;
 import me.Ad237.oitc.utils.GameUtils;
 import net.minecraft.server.v1_7_R1.EntityPlayer;
 import net.minecraft.server.v1_7_R1.EnumClientCommand;
 import net.minecraft.server.v1_7_R1.PacketPlayInClientCommand;
 import org.bukkit.Bukkit;
 import org.bukkit.ChatColor;
 import org.bukkit.craftbukkit.v1_7_R1.entity.CraftPlayer;
 import org.bukkit.entity.Arrow;
 import org.bukkit.entity.Player;
 import org.bukkit.event.EventHandler;
 import org.bukkit.event.EventPriority;
 import org.bukkit.event.Listener;
 import org.bukkit.event.entity.EntityDamageByEntityEvent;
 import org.bukkit.event.entity.EntityDamageEvent;
 import org.bukkit.event.entity.PlayerDeathEvent;
 import org.bukkit.event.player.PlayerRespawnEvent;
 
 public class DamageListener
   implements Listener
 {
   @EventHandler
   public void onDamage(EntityDamageEvent event)
   {
     if ((event instanceof EntityDamageByEntityEvent)) {
       return;
     }
     if (!(event.getEntity() instanceof Player)) {
       return;
     }
     Player p = (Player)event.getEntity();
     Arena a = ArenaManager.getInstance().getArena(p);
     if (a == null) {
       return;
     }
     if ((a.getStage() == GameStage.LOBBY) || (event.getCause() != EntityDamageEvent.DamageCause.VOID))
     {
       event.setCancelled(true);
       return;
     }
   }
   
   @EventHandler
   public void onEntityDamage(EntityDamageByEntityEvent event)
   {
     if (!(event.getEntity() instanceof Player)) {
       return;
     }
     Player p = (Player)event.getEntity();
     Arena a = ArenaManager.getInstance().getArena(p);
     if (a == null) {
       return;
     }
     if (a.getStage() == GameStage.LOBBY)
     {
       event.setCancelled(true);
       return;
     }
     Player damager = null;
     if ((event.getDamager() instanceof Arrow))
     {
       event.setDamage(200.0D);
     }
     else if ((event.getDamager() instanceof Player))
     {
       damager = (Player)event.getDamager();
     }
     else
     {
       event.setCancelled(true);
       return;
     }
     if ((damager != null) && (ArenaManager.getInstance().getArena(damager) != a)) {
       event.setCancelled(true);
     }
   }
   
   @EventHandler
   public void onDeath(PlayerDeathEvent event)
   {
     final Player p = event.getEntity();
     Arena a = ArenaManager.getInstance().getArena(p);
     if (a != null)
     {
       event.setDeathMessage(null);
       event.getDrops().clear();
       event.setDroppedExp(0);
       Player killer = p.getKiller();
       if ((killer == null) || (!a.containsPlayer(killer)))
       {
         a.broadcast(ChatColor.GREEN + p.getName() + ChatColor.GOLD + " died!");
         return;
       }
       if (a.getStage() == GameStage.INGAME)
       {
         a.broadcast(ChatColor.GREEN + p.getName() + ChatColor.GOLD + " was killed by " + ChatColor.GREEN + killer.getName() + ChatColor.GOLD + "!");
         
         a.onKill(killer, p);
       }
       Bukkit.getScheduler().runTaskLater(OITQ.getInstance(), new Runnable()
       {
         public void run()
         {
           PacketPlayInClientCommand in = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
           EntityPlayer cPlayer = ((CraftPlayer)p).getHandle();
           cPlayer.playerConnection.a(in);
         }
       }, 5L);
     }
   }
   
   @EventHandler(priority=EventPriority.HIGHEST)
   public void onRespawn(PlayerRespawnEvent event)
   {
     final Player p = event.getPlayer();
     Arena a = ArenaManager.getInstance().getArena(p);
     if (a != null) {
       if ((a.getStage() == GameStage.LOBBY) || (a.getRandomSpawn() == null))
       {
         event.setRespawnLocation(a.getLobby());
       }
       else
       {
         event.setRespawnLocation(a.getRandomSpawn());
         Bukkit.getScheduler().runTaskLater(OITQ.getInstance(), new Runnable()
         {
           public void run()
           {
             GameUtils.equipPlayer(p);
           }
         }, 1L);
       }
     }
   }
 }