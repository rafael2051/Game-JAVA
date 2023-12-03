import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;   

import threadSocket.RunnableClientSocket;
import control.ControlRound;

public class Server {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        List<SocketAddress> listIp = new ArrayList<SocketAddress>();
        List<Thread> listThreadClientSocket = new ArrayList<Thread>();
        ControlRound controlRound = new ControlRound();

        try {
            serverSocket = new ServerSocket(8080);
        } catch (Exception e) {
            e.getMessage();
        }
        while(true){
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection initialized with " + clientSocket.getInetAddress()
                                    + ":" + clientSocket.getPort());
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                String msg = input.readLine();
                if(msg.equals("InitialConnection")){
                    String answer = "";
                    for(SocketAddress socketAddress : listIp){
                        answer += socketAddress.toString();
                        answer += ";";
                    }
                    output.println("Connection initialized;" + answer);
                    listIp.add(clientSocket.getRemoteSocketAddress());
                    RunnableClientSocket runnableClientSocket = new RunnableClientSocket(clientSocket, controlRound);
                    Thread threadClient = new Thread(runnableClientSocket);
                    threadClient.start();
                    listThreadClientSocket.add(threadClient);
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public static void removeIp(String string){}
}
