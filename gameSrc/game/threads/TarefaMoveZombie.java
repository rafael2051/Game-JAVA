package game.threads;


import game.apis.ApiZombieClient;
import game.logica.player.Player;
import game.logica.zombie.Zombie;
import game.logica.zombie.ZombieStandard;

import game.logica.janela.Janela;

import java.util.Random;

public class TarefaMoveZombie implements Runnable{
    private Janela janela;
    private ZombieStandard zombieStandard;

    private ApiZombieClient apiZombieClient;
    private double currentTime;
    private double previousTime;
    private double spawnTime;


    public TarefaMoveZombie(Janela janela, ZombieStandard zombieStandard){
        this.janela = janela;
        this.zombieStandard = zombieStandard;
        apiZombieClient = ApiZombieClient.getInstance();
        previousTime = System.currentTimeMillis();
        currentTime = 0;
        spawnTime = 2000;
    }

    @Override
    public void run() {
        Random random = new Random();
        while(true){
            currentTime = System.currentTimeMillis() - previousTime;
            for(Zombie zombie : janela.zombies){
                if(zombie.isDead()){
                    continue;
                }
                if(zombie.getPosX() <= 0 && zombie.getMustRender()){
                    zombie.setMustRender(false);
                    janela.decreasesHP();
                    continue;
                }
                for(Player player : janela.players){
                    if((zombie.getPosX() >= 
                        (player.getPosX() + player.getWidth() - 60) &&
                        zombie.getPosX() <= 
                        (player.getPosX() + player.getWidth() - 40)) &&
                        (zombie.getPosY() >=
                        player.getPosY() - 20  &&
                        zombie.getPosY() <=
                        (player.getPosY() + player.getHeight()) - 40)){
                            zombie.attack(player.getId());
                            zombie.setAttacking(true);
                            player.gettingAttacked();
                    }
                    else if(zombie.getPlayerAttacked() == player.getId()){
                        zombie.setAttacking(false);
                        zombie.resetPlayerAttacked();
                    }
                }
                if(!zombie.getAttacking()){
                    zombie.move();
                    zombie.setAttacking(false);
                }
            }
            if(currentTime >= spawnTime && apiZombieClient.checkIfMustAddZombie()){
                previousTime = System.currentTimeMillis();
                currentTime = 0;
                int[] posZombie = apiZombieClient.getPosZombie();
                janela.addZombie(new Zombie(posZombie[0], posZombie[1],
                                zombieStandard));
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
                continue;
            }
        }
    }   
}
