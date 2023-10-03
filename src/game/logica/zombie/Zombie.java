package game.logica.zombie;

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

public class Zombie {
    public ZombieStandard zombieStandard;
    private int pos_x;
    private int pos_y;
    private int next_image;

    public Zombie(int pos_x, int pos_y, ZombieStandard zombieStandard){
        this.pos_x = 1000;
        this.pos_y = pos_y;
        this.zombieStandard = zombieStandard;
        next_image = 0;
    }

    public int getPosX(){
        return pos_x;
    }

    public int getPosY(){
        return pos_y;
    }

    public int getNextImage(){
        return next_image;
    }

    public void move(){
        pos_x--;
        next_image++;
        if(next_image >= ZombieStandard.images.size()){
            next_image = 0;
        }
    }
}
