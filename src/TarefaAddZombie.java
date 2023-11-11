import java.util.Random;
import game.logica.janela.Janela;
import game.logica.zombie.Zombie;
import game.logica.zombie.ZombieStandard;

public class TarefaAddZombie implements Runnable{
    private Janela janela;
    private ZombieStandard zombieStandard;

    public TarefaAddZombie(Janela janela, ZombieStandard zombieStandard){
        this.janela = janela;
        this.zombieStandard = zombieStandard;
    }
    
    @Override
    public void run() {
        Random random = new Random();
        double previousTimeSpawn = System.currentTimeMillis();
        double currentTimeSpawn = 0;
        double spawnTimeZombie = 1000; //amount a time for a new zombie to spawn
        while(true){
            currentTimeSpawn = System.currentTimeMillis() - previousTimeSpawn;
            if(currentTimeSpawn >= spawnTimeZombie){
                previousTimeSpawn = System.currentTimeMillis();
                currentTimeSpawn = 0;
                janela.addZombie(new Zombie(random.nextInt(50, janela.getWidth() - 100), random.nextInt(10, janela.getHeight() - 100), 
                                zombieStandard));
            }
        }
    }
    
 
}
