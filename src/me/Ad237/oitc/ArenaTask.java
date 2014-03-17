 package me.Ad237.oitc;
 
 import me.Ad237.oitc.utils.GameStage;
 
 public class ArenaTask
   implements Runnable
 {
   public void run()
   {
     for (Arena a : ArenaManager.getInstance().getArenas()) {
       if (a.getStage() == GameStage.LOBBY)
       {
         if (a.getPlayers().size() >= a.getMinPlayers())
         {
           a.countdown -= 1;
           if (a.countdown <= 0) {
             a.startGame();
           }
         }
         else
         {
           a.countdown = 30;
         }
         a.updateScoreboard();
       }
     }
   }
 }
