import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import threadSocket.RunnableClientSocket;

public class Server {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        List<String> listIp = new ArrayList<String>();
        List<Thread> listThreadClientSocket = new ArrayList<Thread>();
        try {
            serverSocket = new ServerSocket(8080);
        } catch (Exception e) {
            e.getMessage();
        }
        while(true){
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Endereço IP " + clientSocket.getRemoteSocketAddress());
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                String msg = input.readLine();
                System.out.println(msg);
                if(msg.equals("InitialConnection")){
                    output.println("Conection initialized" + listIp);
                    listIp.add(clientSocket.getRemoteSocketAddress().toString());
                    RunnableClientSocket runnableClientSocket = new RunnableClientSocket(clientSocket);
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
