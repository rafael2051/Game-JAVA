package threadSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import control.ControlRound;

public class RunnableClientSocket implements Runnable {

    private Socket clientSocket;
    private ControlRound controlRound;

    public RunnableClientSocket(Socket clientSocket, ControlRound controlRound){
        this.clientSocket = clientSocket;
        this.controlRound = controlRound;
    }

    @Override
    public void run(){
        try{
            BufferedReader input = null;
            PrintWriter output = null;
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(), true);
            controlRound.up_No_Players();
            while(true){
                String msg = "";
                msg = input.readLine();
                if(msg.equals("Ready")){
                    System.out.println(msg);
                    controlRound.up_Ready_Players();
                    if(controlRound.gameStart()){
                        output.println("StartTheGame");
                    }
                }
                else if(msg.equals("FinalConnection")){
                    clientSocket.close();
                    System.out.println("Closed connection with " 
                                        + clientSocket.getInetAddress() 
                                        + ":" + clientSocket.getPort());
                    break;
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
