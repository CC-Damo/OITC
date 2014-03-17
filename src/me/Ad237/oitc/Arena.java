 package me.Ad237.oitc;
 
 import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import me.Ad237.oitc.utils.GameStage;
import me.Ad237.oitc.utils.GameUtils;
import me.Ad237.oitc.utils.PlayerData;
import me.Ad237.oitc.utils.TimeUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
 
 public class Arena
 {
   private String name;
   private GameStage stage = GameStage.LOBBY;
   protected int countdown = 30;
   private int min = 2;
   private int max = 8;
   private Location lobby;
   private ArrayList<Location> spawns = new ArrayList<Location>();
   public HashMap<String, PlayerData> players = new HashMap<String, PlayerData>();
   public HashMap<String, Integer> kills = new HashMap<String, Integer>();
   public HashMap<String, Integer> deaths = new HashMap<String, Integer>();
   
   protected Arena(String name, Location lobby, ArrayList<Location> spawns, int min, int max)
   {
     this.name = name;
     this.lobby = lobby;
     this.spawns = spawns;
     this.min = min;
     this.max = max;
   }
   
   public String getName()
   {
     return this.name;
   }
   
   public GameStage getStage()
   {
     return this.stage;
   }
   
   public ArrayList<Player> getPlayers()
   {
     ArrayList<Player> player = new ArrayList<Player>();
     for (String name : this.players.keySet()) {
       player.add(Bukkit.getPlayerExact(name));
     }
     return player;
   }
   
   public boolean containsPlayer(Player p)
   {
     return this.players.containsKey(p.getName());
   }
   
   public int getMinPlayers()
   {
     return this.min;
   }
   
   public int getMaxPlayers()
   {
     return this.max;
   }
   
   public Location getLobby()
   {
     return this.lobby;
   }
   
   public Location getRandomSpawn()
   {
     if (this.spawns.size() == 0) {
       return null;
     }
     Random rand = new Random();
     return (Location)this.spawns.get(rand.nextInt(this.spawns.size()));
   }
   
   public void broadcast(String msg)
   {
     for (Player p : getPlayers()) {
       OITQ.sendMsg(p, msg);
     }
   }
   
   public void addPlayer(Player p)
   {
     if (ArenaManager.getInstance().isInGame(p))
     {
       OITQ.sendMsg(p, ChatColor.RED + "You are already in a game!");
       return;
     }
     if (getStage() != GameStage.LOBBY)
     {
       OITQ.sendMsg(p, ChatColor.RED + "This game has already started!");
       return;
     }
     if (this.players.size() >= this.max)
     {
       OITQ.sendMsg(p, ChatColor.RED + "This game is full!");
       return;
     }
     this.players.put(p.getName(), new PlayerData(p));
     
     p.setHealth(20.0D);
     p.setFoodLevel(20);
     p.setLevel(0);
     p.setExp(0.0F);
     p.getInventory().clear();
     p.getInventory().setArmorContents(null);
     p.setGameMode(GameMode.ADVENTURE);
     p.teleport(this.lobby);
     
     broadcast(ChatColor.GREEN + p.getName() + ChatColor.GOLD + " joined the game! (" + this.players.size() + "/" + this.max + ")");
     
 
     updateScoreboard();
   }
   
   public void removePlayer(Player p)
   {
     if (!containsPlayer(p)) {
       return;
     }
     broadcast(ChatColor.GREEN + p.getName() + ChatColor.GOLD + " left the game!");
     ((PlayerData)this.players.get(p.getName())).restorePlayer();
     this.players.remove(p.getName());
     
     p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
     if ((getStage() == GameStage.INGAME) && (this.players.size() == 1))
     {
       StatsManager.getInstance().setStat(p.getName(), "kills", StatsManager.getInstance().getStat(p.getName(), "kills") + ((Integer)this.kills.get(p.getName())).intValue());
       
       StatsManager.getInstance().setStat(p.getName(), "deaths", StatsManager.getInstance().getStat(p.getName(), "deaths") + ((Integer)this.deaths.get(p.getName())).intValue());
       
 
       endGame((Player)getPlayers().get(0));
       return;
     }
     updateScoreboard();
   }
   
   public void startGame()
   {
     this.countdown = 30;
     if (this.spawns.size() == 0)
     {
       broadcast(ChatColor.RED + "The game could not be started because no spawns are registered with this arena!");
       return;
     }
     this.stage = GameStage.INGAME;
     broadcast(ChatColor.YELLOW + "The game has started with " + this.players.size() + " players!");
     
     int count = 0;
     for (Player p : getPlayers())
     {
       this.kills.put(p.getName(), Integer.valueOf(0));
       this.deaths.put(p.getName(), Integer.valueOf(0));
       if (count >= this.spawns.size()) {
         count = 0;
       }
       p.teleport((Location)this.spawns.get(count));
       count++;
       
       GameUtils.equipPlayer(p);
     }
     updateScoreboard();
   }
   
   public void endGame(Player winner)
   {
     broadcast(ChatColor.GOLD + "The game has ended!");
     if (winner == null)
     {
       broadcast(ChatColor.RED + "There was no winner!");
     }
     else
     {
       broadcast(ChatColor.GREEN + winner.getName() + ChatColor.GOLD + " wins!");
       
       StatsManager.getInstance().setStat(winner.getName(), "wins", StatsManager.getInstance().getStat(winner.getName(), "wins") + 1);
     }
     this.countdown = 30;
     this.stage = GameStage.LOBBY;
     
     Bukkit.getScheduler().runTaskLater(OITQ.getInstance(), new Runnable()
     {
       public void run()
       {
         for (Player p : Arena.this.getPlayers())
         {
           p.setHealth(20.0D);
           p.setFoodLevel(20);
           p.setLevel(0);
           p.setExp(0.0F);
           p.getInventory().clear();
           p.getInventory().setArmorContents(null);
           p.setGameMode(GameMode.ADVENTURE);
           p.teleport(Arena.this.lobby);
         }
       }
     }, 100L);
     for (Player p : getPlayers())
     {
       StatsManager.getInstance().setStat(p.getName(), "kills", StatsManager.getInstance().getStat(p.getName(), "kills") + ((Integer)this.kills.get(p.getName())).intValue());
       
       StatsManager.getInstance().setStat(p.getName(), "deaths", StatsManager.getInstance().getStat(p.getName(), "deaths") + ((Integer)this.deaths.get(p.getName())).intValue());
     }
     this.kills.clear();
     this.deaths.clear();
     updateScoreboard();
   }
   
   public void updateScoreboard()
   {
     Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
     Objective obj = board.registerNewObjective("scoreboard", "dummy");
     obj.setDisplaySlot(DisplaySlot.SIDEBAR);
     Objective kills;
     if (getStage() == GameStage.LOBBY)
     {
       obj.setDisplayName(getName() + " " + TimeUtils.toCountdown(this.countdown));
       obj.getScore(Bukkit.getOfflinePlayer(ChatColor.AQUA + "Players")).setScore(this.players.size());
       obj.getScore(Bukkit.getOfflinePlayer(ChatColor.AQUA + "Minimum")).setScore(this.min);
     }
     else
     {
       kills = board.registerNewObjective("kills", "dummy");
       kills.setDisplayName("Kills");
       kills.setDisplaySlot(DisplaySlot.BELOW_NAME);
       
       obj.setDisplayName(getName());
       for (Player p : getPlayers())
       {
         obj.getScore(p).setScore(((Integer)this.kills.get(p.getName())).intValue());
         kills.getScore(p).setScore(((Integer)this.kills.get(p.getName())).intValue());
       }
     }
     for (Player p : getPlayers()) {
       p.setScoreboard(board);
     }
   }
   
   public void onKill(Player p, Player victim)
   {
     p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.ARROW) });
     if (containsPlayer(p)) {
       this.kills.put(p.getName(), Integer.valueOf(((Integer)this.kills.get(p.getName())).intValue() + 1));
     }
     if ((victim != null) && (containsPlayer(victim))) {
       this.deaths.put(victim.getName(), Integer.valueOf(((Integer)this.deaths.get(victim.getName())).intValue() + 1));
     }
     if (((Integer)this.kills.get(p.getName())).intValue() >= 20) {
       endGame(p);
     }
     updateScoreboard();
   }
   
   public void addSpawn(Location loc)
   {
     this.spawns.add(loc);
   }
   
   public void formatSign(Sign s)
   {
     s.setLine(0, OITQ.PREFIX);
     s.setLine(1, getStage().getDisplayName());
     s.setLine(2, getName());
     s.setLine(3, this.players.size() + "/" + this.max);
     s.update();
   }
 }