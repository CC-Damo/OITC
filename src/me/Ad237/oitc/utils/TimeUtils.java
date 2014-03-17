 package me.Ad237.oitc.utils;
 
 public class TimeUtils
 {
   public static String toCountdown(int time)
   {
     int min = time / 60;
     int sec = time % 60;
     
     return min + ":" + (sec < 10 ? "0" : "") + sec;
   }
 }
