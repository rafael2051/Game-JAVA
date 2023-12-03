import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;   

import threadSocket.RunnableClientSocket;
import threadsControl.ThreadControlRound;

public class Server {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        List<String> listIp = new ArrayList<String>();
        List<Thread> listThreadClientSocket = new ArrayList<Thread>();
        Map<String, Boolean> statusPlayers = new HashMap<String, Boolean>();
        ThreadControlRound threadControlRound = new ThreadControlRound(statusPlayers);
        threadControlRound.start();
        try {
            serverSocket = new ServerSocket(8080);
        } catch (Exception e) {
            e.getMessage();
        }
        while(true){
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Conection initialized with " + clientSocket.getInetAddress()
                                    + ":" + clientSocket.getPort());
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                String msg = input.readLine();
                if(msg.equals("InitialConnection")){
                    output.println("Conection initialized" + listIp);
                    listIp.add(clientSocket.getRemoteSocketAddress().toString());
                    statusPlayers.put(clientSocket.getInetAddress() + ":"
                                        + clientSocket.getPort(), false);
                    RunnableClientSocket runnableClientSocket = new RunnableClientSocket(clientSocket, threadControlRound);
                    Thread threadClient = new Thread(runnableClientSocket);
                    threadClient.start();
                    listThreadClientSocket.add(threadClient);
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }
}
