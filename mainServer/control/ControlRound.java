package control;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class ControlRound{
    private int no_players;
    private int ready_players;

    public ControlRound(){
        no_players = 0;
        ready_players = 0;
    }

    public boolean gameStart(){
        if(no_players > 0 && no_players == ready_players){
            return true;
        }
        else{
            return false;
        }
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
