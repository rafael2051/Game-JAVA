import game.logica.janela.Janela;
import game.logica.janela.TarefaMove;
import game.logica.janela.TarefaMoveBullet;
import game.logica.janela.TarefaMoveZombie;
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

        GameServer server = new GameServer();
        server.setPriority(1);
        server.start();
        double previousTime = System.currentTimeMillis();
        double currentTime = 0;
        double frameTime = 40; // time to update the frame
        double previousTimeShootRate = System.currentTimeMillis();
        double currentTimeShootRate = System.currentTimeMillis();
        double frameTimeShootRate = 200; // time to control the shooting rate
        Random random = new Random();
        double previousTimeSpawn = System.currentTimeMillis();
        double currentTimeSpawn = 0;
        double spawnTimeZombie = 2000; // time for a new zombie to spawn

        Janela janela = new Janela(1000, 1000);
        ZombieStandard zombieStandard = new ZombieStandard(80, 80);

        BulletStandard bulletStandard = new BulletStandard(10, 2);
        Bullet bullet = new Bullet(0, 0, bulletStandard);

        Player localPlayer;

        janela.addZombie(new Zombie(janela.getWidth(), 300, zombieStandard));

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
                }
            }

            if(janela.getGameExit() == 1){
                System.exit(0);
            }

            else if (janela.getStatus() == 1){
                localPlayer = new Player(200, 200, 80, 80);
                janela.addPlayer(localPlayer);
                janela.addPlayer(new Player(300, 300, 80, 80));
                while(true){
                    currentTime = System.currentTimeMillis() - previousTime;
                    currentTimeShootRate = System.currentTimeMillis() - previousTimeShootRate;
                    currentTimeSpawn = System.currentTimeMillis() - previousTimeSpawn;
                    if(currentTimeShootRate >= frameTimeShootRate && 
                        localPlayer.getShooting() &&
                        localPlayer.getAmmo() > 0){

                        currentTimeShootRate = 0;
                        previousTimeShootRate = System.currentTimeMillis();
                        localPlayer.shoot();
                        janela.addBullet(localPlayer.getPosX() + 74, localPlayer.getPosY() + 59,
                                        bulletStandard);
                    }
                    if(currentTimeSpawn >= spawnTimeZombie){
                        previousTimeSpawn = System.currentTimeMillis();
                        currentTimeSpawn = 0;
                        janela.addZombie(new Zombie(random.nextInt(50, janela.getWidth() - 100), random.nextInt(10, janela.getHeight() - 100), 
                                        zombieStandard));
                    }
                    if(currentTime >= frameTime){
                        previousTime = System.currentTimeMillis();
                        currentTime = 0;
                        Runnable tarefaMove = new TarefaMove(janela, 0, bulletStandard);
                        Thread threadMove = new Thread(tarefaMove);
                        threadMove.start();
                        Runnable tarefaMoveBullet = new TarefaMoveBullet(janela);
                        Thread threadMoveBullet = new Thread(tarefaMoveBullet);
                        threadMoveBullet.start();
                        Runnable tarefaMoveZombie = new TarefaMoveZombie(janela);
                        Thread threadMoveZombie = new Thread(tarefaMoveZombie);
                        threadMoveZombie.start();
                        janela.render();
                    }
                    if(janela.getFortressHP() <= 0 || 
                        janela.players.stream().allMatch((p) -> p.getHP() <= 0)){
                        janela.finishGame();
                        janela.clean();
                        break;
                    }
                }
            }

            else if(janela.getStatus() == 2){
                janela.render();
            }

        }
    }
}