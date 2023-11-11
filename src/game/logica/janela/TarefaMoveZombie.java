package game.logica.janela;


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
            if((zombie.getPosX() >= 
                (janela.players.get(0).getPosX() + janela.players.get(0).getWidth() - 60) &&
                zombie.getPosX() <= 
                (janela.players.get(0).getPosX() + janela.players.get(0).getWidth() - 40)) &&
                (zombie.getPosY() >=
                janela.players.get(0).getPosY() - 20  &&
                zombie.getPosY() <=
                (janela.players.get(0).getPosY() + janela.players.get(0).getHeight()) - 40)){
                    zombie.attack();
                    zombie.setAttacking(true);
                    janela.players.get(0).gettingAttacked();
                }
            else {
                zombie.move();
                zombie.setAttacking(false);
            }
        }
    }   
}
