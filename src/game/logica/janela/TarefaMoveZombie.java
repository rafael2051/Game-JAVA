package game.logica.janela;


import game.logica.player.Player;
import game.logica.zombie.Zombie;

public class TarefaMoveZombie implements Runnable{
    private Janela janela;

    public TarefaMoveZombie(Janela janela){
        this.janela = janela;
    }

    @Override
    public void run() {
        for(Zombie zombie : janela.zombies){
            if(zombie.isDead()){
                continue;
            }
            if(zombie.getPosX() <= 0){
                janela.zombies.remove(zombie);
                janela.decreasesHP();
                
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
                        System.out.println(player.getId());
                }
                else if(zombie.getPlayerAttacked() == player.getId()){
                    zombie.setAttacking(false);
                    zombie.resetPlayerAttacked();
                }
                if(!zombie.getAttacking()){
                    zombie.move();
                    zombie.setAttacking(false);
                }
            }

        }
    }   
}
