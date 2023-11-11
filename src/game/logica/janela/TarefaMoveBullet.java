package game.logica.janela;

import game.logica.player.Bullet;
import game.logica.zombie.Zombie;

public class TarefaMoveBullet implements Runnable{
    private Janela janela;

    public TarefaMoveBullet(Janela janela){
        this.janela = janela;
    }

    @Override
    public void run() {
        boolean collision;
		for(Bullet bullet : janela.bullets){
            collision = false;
            for(Zombie zombie : janela.zombies){
                if(bullet.getPosX() >= zombie.getPosX() &&
                    bullet.getPosX() <= zombie.getPosX() + zombie.getHeight() &&
                    bullet.getPosY() >= zombie.getPosY() + 20  &&
                    bullet.getPosY() <= zombie.getPosY() + zombie.getHeight() - 20 &&
                    !zombie.isDead()){
                        janela.bullets.remove(bullet);
                        zombie.gettingShooted();
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
    }

    
}
