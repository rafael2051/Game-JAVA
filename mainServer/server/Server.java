package server;

import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Map;

import threadSocket.ThreadClientSocket;
import control.ControlRound;

public class Server extends Thread{

    private static final Server server = new Server();
    private static ServerSocket serverSocket = null;
    private static List<SocketAddress> listIp = new ArrayList<SocketAddress>();
    private static List<ThreadClientSocket> listThreadClientSocket = new ArrayList<ThreadClientSocket>();
    private static ControlRound controlRound = ControlRound.getInstance();
    private static String msg;
    private static Thread zombieAdder;
    private static boolean gameRunning;

    private Server(){
        gameRunning = false;
    }

    public static Server getInstance(){
        return server;
    }

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
                    if(gameRunning){
                        output.println("You cannot play right now!");
                    } else{
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
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public static void setGameRunning(boolean gameRunningValue){
        gameRunning = gameRunningValue;
    }

    public void tellEveryoneToStart(){
        gameRunning = true;
        for(ThreadClientSocket client : listThreadClientSocket){
            client.tellToStart();
        }
        initZombieAdder();
    }

    private void initZombieAdder(){
        zombieAdder = new ZombieAdder();
        zombieAdder.start();
    }

    public void removeIp(SocketAddress socketAdress){
        listIp.remove(socketAdress);
    }

    public void removeClient(ThreadClientSocket threadClientSocket){
        listThreadClientSocket.remove(threadClientSocket);
    }

    private class ZombieAdder extends Thread{
        private Random random = new Random();
        private int pos_x;
        private int pos_y;
        public void run(){
            while(true) {
                if(!gameRunning){
                    break;
                }
                pos_x = 1000;
                pos_y = random.nextInt(10, 900);
                for(ThreadClientSocket client : listThreadClientSocket){
                    client.tellToAddZombie(pos_x, pos_y);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
