import game.logica.janela.Janela;
import game.threads.TarefaMovePlayer;
import game.threads.TarefaControlShoot;
import game.threads.TarefaMoveZombie;
import game.logica.player.Bullet;
import game.logica.player.BulletStandard;
import game.logica.player.Player;
import game.logica.zombie.Zombie;
import game.logica.zombie.ZombieStandard;
import server.GameServer;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {

        // GameServer server = new GameServer();
        // server.setPriority(1);
        // server.start();
        Random random = new Random();
        double previousTimeSpawn = System.currentTimeMillis();
        double currentTimeSpawn = 0;
        double spawnTimeZombie = 2000; // time for a new zombie to spawn

        Janela janela = new Janela(1000, 1000);
        ZombieStandard zombieStandard = new ZombieStandard(80, 80);

        BulletStandard bulletStandard = new BulletStandard(10, 2);
        Bullet bullet = new Bullet(0, 0, bulletStandard);

        Player localPlayer;

        while(true){

            if(janela.getStatus() == 0){
                while(true){
                    janela.changeMenuImage();
                    janela.checkEnterPressed();
                    if(janela.getStatus() == 1 ||
                        janela.getGameExit() == 1){
                        break;
                    }
                    janela.render();
                    Thread.sleep(40);
                }
            }

            if(janela.getGameExit() == 1){
                System.exit(0);
            }

            else if (janela.getStatus() == 1){
                localPlayer = new Player(200, 200, 80, 80);
                janela.addPlayer(localPlayer);
                janela.addPlayer(new Player(300, 300, 80, 80));
                Runnable tarefaMove = new TarefaMovePlayer(janela, 0, bulletStandard);
                Thread threadMove = new Thread(tarefaMove);
                threadMove.start();
                Runnable tarefaControlShoot = new TarefaControlShoot(janela, localPlayer, bulletStandard);
                Thread threadControlShoot = new Thread(tarefaControlShoot);
                threadControlShoot.start();
                Runnable tarefaMoveZombie = new TarefaMoveZombie(janela);
                Thread threadMoveZombie = new Thread(tarefaMoveZombie);
                threadMoveZombie.start();
                while(true){
                    currentTimeSpawn = System.currentTimeMillis() - previousTimeSpawn;
                    if(currentTimeSpawn >= spawnTimeZombie){
                        previousTimeSpawn = System.currentTimeMillis();
                        currentTimeSpawn = 0;
                        while(janela.getLockZombie() == 1){
                            continue;
                        }
                        janela.upLockZombie();
                        janela.addZombie(new Zombie(random.nextInt(50, janela.getWidth() - 100), random.nextInt(10, janela.getHeight() - 100), 
                                        zombieStandard));
                        janela.downLockZombie();
                    }
                    janela.render();
                    if(janela.getFortressHP() <= 0 || 
                        janela.players.stream().allMatch((p) -> p.getHP() <= 0)){
                        janela.finishGame();
                        janela.clean();
                        break;
                    }
                    Thread.sleep(40);
                }
            }

            else if(janela.getStatus() == 2){
                janela.render();
                Thread.sleep(40);
            }

        }
    }
}