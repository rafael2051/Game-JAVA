package threadSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import server.Server;

import control.ControlRound;

public class ThreadClientSocket extends Thread {

    private Socket clientSocket;
    private ControlRound controlRound;

    public ThreadClientSocket(Socket clientSocket, ControlRound controlRound) {
        this.clientSocket = clientSocket;
        this.controlRound = controlRound;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = null;
            PrintWriter output = null;
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(), true);
            controlRound.up_No_Players();
            while (true) {
                String msg = "";
                msg = input.readLine();
                if (msg.equals("Ready")) {
                    System.out.println(
                            "Player with address" + clientSocket.getRemoteSocketAddress() + " is ready to play");
                    controlRound.up_Ready_Players();
                    if (controlRound.gameStart()) {
                        System.out.println("All the players are ready to play");
                        Server.getInstance().tellEveryoneToStart();
                    }
                } else if (msg.equals("FinalConnection")) {
                    clientSocket.close();
                    controlRound.down_No_Players();
                    controlRound.down_Ready_Players();
                    Server.getInstance().removeIp(clientSocket.getRemoteSocketAddress());
                    Server.getInstance().removeClient(this);
                    System.out.println("Closed connection with "
                            + clientSocket.getInetAddress()
                            + ":" + clientSocket.getPort());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tellToStart(int pos_x, int pos_y) {
        PrintWriter newOutput = null;
        System.out.println("Pos_x: " + pos_x + " Pos_y: " + pos_y);
        try {
            newOutput = new PrintWriter(clientSocket.getOutputStream(), true);
            newOutput.println("StartTheGame");
            newOutput.println("Initial Position;" + pos_x + ";" + pos_y);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tellToAddZombie(int pos_x, int pos_y) {
        PrintWriter newOutput = null;
        try {
            newOutput = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        newOutput.println("AddNewZombie" + ";" + pos_x + ";" + pos_y);
    }
}
