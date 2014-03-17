 package me.Ad237.oitc.utils;
 
 import org.bukkit.Bukkit;
 import org.bukkit.Location;
 import org.bukkit.World;
 
 public class LocationUtils
 {
   public static String toString(Location loc)
   {
     return loc.getWorld().getName() + "@" + loc.getX() + "@" + loc.getY() + "@" + loc.getZ() + "@" + loc.getPitch() + "@" + loc.getYaw();
   }
   
   public static Location toLocation(String s)
   {
     String[] parts = s.split("@");
     try
     {
       World w = Bukkit.getWorld(parts[0]);
       double x = Double.parseDouble(parts[1]);
       double y = Double.parseDouble(parts[2]);
       double z = Double.parseDouble(parts[3]);
       float pitch = Float.parseFloat(parts[4]);
       float yaw = Float.parseFloat(parts[5]);
       
       return new Location(w, x, y, z, pitch, yaw);
     }
     catch (Exception e) {}
     return null;
   }
 }