package threadSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import threadsControl.ThreadControlRound;

public class RunnableClientSocket implements Runnable {

    private Socket clientSocket;
    private ThreadControlRound threadControlRound;

    public RunnableClientSocket(Socket clientSocket, ThreadControlRound threadControlRound){
        this.clientSocket = clientSocket;
        this.threadControlRound = threadControlRound;
    }

    @Override
    public void run(){
        try{
            BufferedReader input = null;
            PrintWriter output = null;
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(), true);
            while(true){
                String msg = "";
                msg = input.readLine();
                if(msg.equals("Ready")){

                }
                else if(msg.equals("FinalConnection")){
                    clientSocket.close();
                    System.out.println("Closed connection with " 
                                        + clientSocket.getInetAddress() 
                                        + ":" + clientSocket.getPort());
                    break;
                } else{
                    System.out.println(msg);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
