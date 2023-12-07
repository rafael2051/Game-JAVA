package control;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class ControlRound{
    private int no_players;
    private int ready_players;
    private static final ControlRound controlRound = new ControlRound();

    
    private ControlRound(){
        no_players = 0;
        ready_players = 0;
    }

    public static ControlRound getInstance(){
        return controlRound;
    }

    public boolean gameStart(){
        if(no_players > 0 && no_players == ready_players){
            return true;
        }
        else{
            return false;
        }
    }

    public int get_no_Players(){
        return no_players;
    }

    public int get_no_ready_players(){
        return ready_players;
    }

    public void up_No_Players(){
        no_players++;
    }

    public void up_Ready_Players(){
        ready_players++;
    }

    public void down_No_Players(){
        no_players--;
    }

    public void down_Ready_Players(){
        ready_players--;
    }
}
