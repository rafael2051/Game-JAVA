package client;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import game.logica.player.Player;

public class GameClient extends Thread{

    private PrintWriter output;
    private BufferedReader input;
    private Player localPlayer;
    private boolean mustClose;

    public GameClient(Player localPlayer){
        this.localPlayer = localPlayer;
        mustClose = false;
    }

    @Override
    public void run(){
        try{
            Socket clientSocket = new Socket("179.34.50.217", 34828);
            this.output = new PrintWriter(clientSocket.getOutputStream(), true);
            this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output.println("InitialConnection");
            String msg = input.readLine();
            System.out.println(msg);
            while(true){
                if(mustClose){
                    output.println("FinalConnection");
                    clientSocket.close();
                    break;
                }
                output.println(localPlayer.getPosX() + " " + localPlayer.getPosY());
                try{
                    Thread.sleep(20);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void closeClientSocket(boolean mustClose){
        this.mustClose = mustClose;
    }
}
