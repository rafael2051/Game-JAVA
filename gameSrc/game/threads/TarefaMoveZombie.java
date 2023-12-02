package game.threads;


import game.logica.player.Player;
import game.logica.zombie.Zombie;
import game.logica.zombie.ZombieStandard;

import game.logica.janela.Janela;

import java.util.Random;

public class TarefaMoveZombie implements Runnable{
    private Janela janela;
    private ZombieStandard zombieStandard;
    private double previousTimeSpawn = System.currentTimeMillis();
    private double currentTimeSpawn = 0;
    private double spawnTimeZombie = 2000;


    public TarefaMoveZombie(Janela janela, ZombieStandard zombieStandard){
        this.janela = janela;
        this.zombieStandard = zombieStandard;
    }

    @Override
    public void run() {
        Random random = new Random();
        while(true){
            currentTimeSpawn = System.currentTimeMillis() - previousTimeSpawn;
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
            if(currentTimeSpawn >= spawnTimeZombie){
                previousTimeSpawn = System.currentTimeMillis();
                currentTimeSpawn = 0;
                janela.addZombie(new Zombie(random.nextInt(50, janela.getWidth() - 100), random.nextInt(10, janela.getHeight() - 100), 
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
