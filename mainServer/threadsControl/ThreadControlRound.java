package threadsControl;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class ThreadControlRound extends Thread{
    private Map<String, Boolean> statusPlayers; 

    public ThreadControlRound(Map<String, Boolean> statusPlayers){
        this.statusPlayers = statusPlayers;
    }

    public void run(){
        try{
            while(true){
                for(Map.Entry<String, Boolean> entrada : statusPlayers.entrySet()) {
                }
                try{
                Thread.sleep(20);
                } catch(Exception exception1){

                }
                try{
                Thread.sleep(20);
                } catch(Exception exception2){

                }
            }
        } catch(Exception exception3){
            
        }
    }
}
