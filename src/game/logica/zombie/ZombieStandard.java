package game.logica.zombie;

import java.util.ArrayList;
import java.util.List;


import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import game.logica.janela.Janela;
import game.logica.janela.TarefaMove;
import javax.imageio.ImageIO;

import java.io.IOException;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class ZombieStandard {
    public static List <Image> images;
    protected static int width;
    protected static int height;
    public ZombieStandard(int width, int height){
        images = new ArrayList<Image>();
        List <BufferedImage> bufferedImages = new ArrayList<BufferedImage>();
        try{
            for(int i = 0;i < 17;i++){
                bufferedImages.add(ImageIO.read(new File("game\\images\\tds_zombie/export\\move\\skeleton-move_" + i + ".png"))); 
            }
        }
        catch(IOException e){
            System.out.println("ERROR!");
            System.exit(1);
        }
        if(bufferedImages.size() == 0){
            System.out.println("ERROR!");
            System.exit(1);
        }
        for(BufferedImage bufferedImage : bufferedImages){
            images.add(bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH));
        }
    }
}
