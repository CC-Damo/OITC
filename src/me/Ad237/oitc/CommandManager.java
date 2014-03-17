 package me.Ad237.oitc;
 
 import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.Ad237.oitc.commands.ArenasCommand;
import me.Ad237.oitc.commands.CreateCommand;
import me.Ad237.oitc.commands.InfoCommand;
import me.Ad237.oitc.commands.JoinCommand;
import me.Ad237.oitc.commands.QuitCommand;
import me.Ad237.oitc.commands.SpawnCommand;
import me.Ad237.oitc.commands.StatsCommand;
import me.Ad237.oitc.commands.SubCommand;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
 
 public class CommandManager
   implements CommandExecutor
 {
   private ArrayList<SubCommand> cmds = new ArrayList<SubCommand>();
   
   protected CommandManager()
   {
     this.cmds.add(new JoinCommand());
     this.cmds.add(new QuitCommand());
     this.cmds.add(new ArenasCommand());
     this.cmds.add(new InfoCommand());
     this.cmds.add(new CreateCommand());
     this.cmds.add(new SpawnCommand());
     this.cmds.add(new StatsCommand());
   }
   
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
   {
     if (!(sender instanceof Player)) {
       return false;
     }
     Player p = (Player)sender;
     if ((args.length == 0) || (args[0].equalsIgnoreCase("help")) || (args[0].equalsIgnoreCase("?")))
     {
       OITQ.sendMsg(p, "------------[" + ChatColor.AQUA + "OITQ" + ChatColor.GRAY + "]------------");
       for (SubCommand cmd : this.cmds) {
         OITQ.sendMsg(p, cmd.getUsage() + " - " + cmd.getDescription());
       }
       return false;
     }
     SubCommand cmd = getCommand(args[0]);
     if (cmd == null)
     {
       OITQ.sendMsg(p, ChatColor.RED + "Unknown command. Type \"/oitQ ?\" for help.");
       return false;
     }
     if ((cmd.getPermission() != null) && (!p.hasPermission(cmd.getPermission())))
     {
       OITQ.sendMsg(p, ChatColor.RED + "You do not have permission to perform this command!");
       return false;
     }
     List<String> a = new ArrayList<String>(Arrays.asList(args));
     a.remove(0);
     args = (String[])a.toArray(new String[a.size()]);
     
     cmd.onCommand(p, args);
     
     return false;
   }
   
   public SubCommand getCommand(String name)
   {
     for (SubCommand cmd : this.cmds)
     {
       if (cmd.getName().equalsIgnoreCase(name)) {
         return cmd;
       }
       for (String a : cmd.getAliases()) {
         if (a.equalsIgnoreCase(name)) {
           return cmd;
         }
       }
     }
     return null;
   }
 }