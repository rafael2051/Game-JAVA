import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import game.logica.janela.Janela;
import game.logica.janela.TarefaMove;
import game.logica.zombie.ZombieStandard;

import javax.imageio.ImageIO;
import java.util.Random;

import java.io.IOException;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class App {
    public static void main(String[] args) throws Exception {
        double previousTime = System.currentTimeMillis();
        double currentTime = 0;
        double frameTime = 20; // time to update the frame
        double previousTimeSpawn = System.currentTimeMillis();
        double currentTimeSpawn = 0;
        double spawnTimeZombie = 2000; //amount a time for a new zombie to spawn
        Random random = new Random();
        // BufferedImage scenario = null;

        // try{
        //     scenario = ImageIO.read(new File("game/images/scenario/scenario_1.jpg"));
        // }
        // catch(IOException e){
        //     e.printStackTrace();
        // }

        // if(scenario == null){
        //     System.out.println("ERRO!");
        //     System.exit(1);
        // }

        ZombieStandard zombieStandard = new ZombieStandard(100, 100);
        
        Janela janela = new Janela(1000, 1000);

        janela.addPlayer(200, 200, 100, 100);

        janela.addZombie(random.nextInt(janela.getWidth()), random.nextInt(janela.getHeight()), zombieStandard);

        // JLabel label = new JLabel();
        // Image imgg = scenario.getScaledInstance(janela.getWidth(), janela.getHeight(), Image.SCALE_AREA_AVERAGING);
        // ImageIcon imageIcon = new ImageIcon(imgg);
        // label.setBounds(0, 0, janela.getWidth(), janela.getHeight());
        // label.setIcon(imageIcon);
        // janela.add(label);

        while(true){
            currentTime = System.currentTimeMillis() - previousTime;
            currentTimeSpawn = System.currentTimeMillis() - previousTimeSpawn;
            if(currentTimeSpawn >= spawnTimeZombie){
                previousTimeSpawn = System.currentTimeMillis();
                currentTimeSpawn = 0;
                janela.addZombie(random.nextInt(janela.getWidth()), random.nextInt(janela.getHeight()), zombieStandard);
            }
            if(currentTime >= frameTime){
                previousTime = System.currentTimeMillis();
                currentTime = 0;
                Runnable tarefaMove1 = new TarefaMove(janela, 0);
                Thread thread1 = new Thread(tarefaMove1);
                janela.moveZombie();
                thread1.start();
                janela.render();
            }
        }
    }
}
