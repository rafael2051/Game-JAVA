package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import game.logica.janela.Janela;

public class GameServer extends Thread{

    private Janela janela;

    public GameServer(Janela janela){
        this.janela = janela;
    }       

    @Override
    public void run(){
    }
}
