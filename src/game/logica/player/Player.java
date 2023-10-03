package game.logica.player;


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

public class Player {
    private int pos_x;
    private int pos_y;
    private int width;
    private int height;
    public Image imagePlayer;

    public Player (int pos_x, int pos_y, int width, int height){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.width = width;
        this.height = height;
        BufferedImage player = null;

        try{
            player = ImageIO.read(new File("game\\images\\Top_Down_Survivor\\rifle\\move\\survivor-move_rifle_1.png"));
        }
        catch(IOException e){
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        }

        imagePlayer = player.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        // label = new JLabel();
        // Image imgg = player.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        // ImageIcon imageIcon = new ImageIcon(imgg);
        // label.setIcon(imageIcon);

    }

    public int getPosX() {
        return pos_x;
    }

    public int getPosY(){
        return pos_y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void walkRight(){
        pos_x += 10;
    }

    public void walkLeft(){
        pos_x -= 10;
    }

    public void walkUp(){
        pos_y -= 10;
    }

    public void walkDown(){
        pos_y += 10;
    }   
}
