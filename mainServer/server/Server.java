package server;

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

import threadSocket.ThreadClientSocket;
import control.ControlRound;

public class Server extends Thread{

    private static ServerSocket serverSocket = null;
    private static List<SocketAddress> listIp = new ArrayList<SocketAddress>();
    private static List<ThreadClientSocket> listThreadClientSocket = new ArrayList<ThreadClientSocket>();
    private static ControlRound controlRound = new ControlRound();
    private static String msg;

    public void run(){

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
                msg = input.readLine();
                if(msg.equals("InitialConnection")){
                    String answer = "";
                    for(SocketAddress socketAddress : listIp){
                        answer += socketAddress.toString();
                        answer += ";";
                    }
                    output.println("Connection initialized;" + answer);
                    listIp.add(clientSocket.getRemoteSocketAddress());
                    ThreadClientSocket threadClient = new ThreadClientSocket(clientSocket, controlRound);
                    threadClient.start();
                    listThreadClientSocket.add(threadClient);
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public static void tellEveryoneToStart(){
        for(ThreadClientSocket client : listThreadClientSocket){
            client.tellToStart();
        }
    }

    public static void removeIp(SocketAddress socketAdress){
        listIp.remove(socketAdress);
    }

    public static void removeClient(ThreadClientSocket threadClientSocket){
        listThreadClientSocket.remove(threadClientSocket);
    }
}
