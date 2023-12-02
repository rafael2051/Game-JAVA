package game.threads;

import game.logica.player.Player;
import game.logica.player.Bullet;
import game.logica.zombie.Zombie;
import game.logica.player.BulletStandard;

import game.logica.janela.Janela;

import java.util.ConcurrentModificationException;

public class TarefaControlShoot implements Runnable{

    private Player localPlayer;
    private Janela janela;
    private BulletStandard bulletStandard;
    
    public TarefaControlShoot(Janela janela, Player localPlayer, BulletStandard bulletStandard){
        this.janela = janela;
        this.localPlayer = localPlayer;
        this.bulletStandard = bulletStandard;
    }

    @Override
    public void run(){
        boolean collision;
        while(true){
            try{
                if(janela.controleTecla[4] &&
                localPlayer.getNextImageReloading() == - 1){
                    localPlayer.updateNextImageShooting();
                    localPlayer.setShooting(true);
                }
                else{
                    localPlayer.setShooting(false);
                }
                if(janela.controleTecla[5] &&
                localPlayer.getNextImageReloading() == -1){

                    localPlayer.setMustReload(true);
                }
                for(int i = 0;i < 5;i++){
                    for(Bullet bullet : janela.bullets){
                        collision = false;
                        for(Zombie zombie : janela.zombies){
                            if(bullet.getPosX() >= zombie.getPosX() &&
                                bullet.getPosX() <= zombie.getPosX() + zombie.getHeight() &&
                                bullet.getPosY() >= zombie.getPosY() + 20  &&
                                bullet.getPosY() <= zombie.getPosY() + zombie.getHeight() - 20 &&
                                !zombie.isDead()){
                                    if(bullet.getMustRender()){
                                        zombie.gettingShooted();
                                    }
                                    bullet.setMustRender(false);
                                    if(zombie.getHP() <= 0){
                                        zombie.kill();
                                    }
                                    collision = true;
                                    break;
                                }
                        }
                        if(!collision){
                            bullet.move();
                        }
                    }
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if(localPlayer.getShooting() &&
                localPlayer.getAmmo() > 0
                && localPlayer.getMustReload() == false){

                    localPlayer.shoot();
                    janela.addBullet(localPlayer.getPosX() + 74, localPlayer.getPosY() + 59,
                                    bulletStandard);
                }
                else if (localPlayer.getMustReload() == true){
                    localPlayer.setMustReload(false);
                    localPlayer.reload();
                    localPlayer.updateNextImageReloading();
                }
            } catch(ConcurrentModificationException concurrentException){
                continue;
            }
        }
    }
}
