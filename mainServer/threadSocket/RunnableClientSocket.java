package threadSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RunnableClientSocket implements Runnable {

    private Socket clientSocket;

    public RunnableClientSocket(Socket clientSocket){
        this.clientSocket = clientSocket;
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
                if(msg.equals("FinalConnection")){
                    clientSocket.close();
                    break;
                }
                System.out.println(msg);
                if(clientSocket.isClosed()){
                    break;
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
