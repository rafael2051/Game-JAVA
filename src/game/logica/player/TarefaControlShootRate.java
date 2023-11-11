package game.logica.player;
import game.logica.janela.Janela;

public class TarefaControlShootRate implements Runnable{
    private double currentTime;
    private double previousTime;
    private double shootRate;
    private Janela janela;

    private BulletStandard bulletStandard;
    public TarefaControlShootRate(double shootRate, Janela janela,
                                BulletStandard bulletStandard){
        this.shootRate = shootRate;
        this.janela = janela;

        this.bulletStandard = bulletStandard;
    }

    @Override
    public void run() {
        double previousTime = System.currentTimeMillis();
        currentTime = 0;

        while(true){
            currentTime = System.currentTimeMillis() - previousTime;
            if(currentTime >= shootRate && janela.controleTecla[4]){
                System.out.println("threadControlShootRate and the value currentTime " + currentTime);
                currentTime = 0;
                previousTime = System.currentTimeMillis();
                janela.addBullet(janela.players.get(0).getPosX() + 74, janela.players.get(0).getPosY() + 59,
                                bulletStandard);
            }
        }
    }  
}