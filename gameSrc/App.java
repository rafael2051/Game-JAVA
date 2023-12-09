import game.apis.ApiPlayerClient;
import game.logica.janela.Janela;
import game.threads.TarefaControlOtherPlayers;
import game.threads.TarefaMoveLocalPlayer;
import game.threads.TarefaControlShoot;
import game.threads.TarefaMoveZombie;
import game.logica.player.Bullet;
import game.logica.player.BulletStandard;
import game.logica.player.Player;
import game.logica.zombie.Zombie;
import game.logica.zombie.ZombieStandard;
import server.GameServer;
import client.GameClient;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {

        Random random = new Random();

        Janela janela = new Janela(1000, 1000);
        ZombieStandard zombieStandard = new ZombieStandard(80, 80);

        BulletStandard bulletStandard = new BulletStandard(10, 2);
        Bullet bullet = new Bullet(0, 0, bulletStandard);

        GameClient gameClient = null;
        GameServer gameServer = null;

        Player localPlayer;

        while(true){

            if(janela.getStatus() == 0){
                while(true){
                    janela.changeMenuImage();
                    janela.checkEnterPressed();
                    if(janela.getStatus() == 1 ||
                        janela.getGameExit() == 1){
                        Thread.sleep(1000);
                        break;
                    }
                    janela.render();
                    Thread.sleep(40);
                }
            }

            if(janela.getGameExit() == 1){
                System.exit(0);
            }


            else if(janela.getStatus() == 1){
                gameClient = new GameClient();
                gameClient.start();
                janela.setGameClient(gameClient);
                while(true){
                    janela.checkEnterPressed();
                    janela.checkStartGame();
                    if(janela.getStatus() == 2){
                        Thread.sleep(1000);
                        break;
                    }
                    janela.render();
                    Thread.sleep(40);
                }
            }

            else if (janela.getStatus() == 2){
                gameServer = new GameServer();
                gameServer.start();
                String positions = ApiPlayerClient.getInstance().getPlayersPos();
                String[] playersPos = positions.split(";");
                int noPlayers = ApiPlayerClient.getInstance().getNoPlayers();
                localPlayer = null;
                for(int i = 0 ; i < noPlayers;i++){
                    String[] parameters = playersPos[i].split("/");
                    int id = Integer.parseInt(parameters[0]);
                    int posX = Integer.parseInt(parameters[1]);
                    int posY = Integer.parseInt(parameters[2]);
                    if(parameters[3].equals("true")){
                        localPlayer = new Player(id, posX, posY, 80, 80);
                        janela.addPlayer(localPlayer);
                    } else if(parameters[3].equals("false")){
                        janela.addPlayer(new Player(id, posX, posY, 80, 80));
                    }
                }
                gameClient.setLocalPlayer(localPlayer);
                Runnable tarefaMove = new TarefaMoveLocalPlayer(janela, localPlayer, bulletStandard);
                Thread threadMove = new Thread(tarefaMove);
                threadMove.start();
                Runnable tarefaMoveOtherPlayers = new TarefaControlOtherPlayers(janela, bulletStandard);
                Thread threadMoveOtherPlayers = new Thread(tarefaMoveOtherPlayers);
                threadMoveOtherPlayers.start();
                Runnable tarefaControlShoot = new TarefaControlShoot(janela, localPlayer, bulletStandard);
                Thread threadControlShoot = new Thread(tarefaControlShoot);
                threadControlShoot.start();
                Runnable tarefaMoveZombie = new TarefaMoveZombie(janela, zombieStandard);
                Thread threadMoveZombie = new Thread(tarefaMoveZombie);
                threadMoveZombie.start();
                while(true){
                    if(janela.getFortressHP() <= 0 || 
                        janela.players.stream().allMatch((p) -> p.getHP() <= 0)){
                        janela.finishGame();
                        janela.clean();
                        break;
                    }
                    janela.render();
                    Thread.sleep(40);
                }
            }

            else if(janela.getStatus() == 3){
                janela.render();
                Thread.sleep(40);
            }

        }
    }
}