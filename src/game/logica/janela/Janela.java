package game.logica.janela;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;

import game.logica.player.Player;

import java.io.File;
import java.io.IOException;
import javax.swing.JLabel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import game.logica.zombie.Zombie;
import game.logica.zombie.ZombieStandard;


public class Janela extends JFrame{
    public JPanel tela;
    private JLabel label;
    protected List <Player> players;
    protected boolean[] controleTecla = new boolean[4];
	private List <Zombie> zombies;
    
    public Janela(int width, int height){

        this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				setaTecla(e.getKeyCode(), false);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				setaTecla(e.getKeyCode(), true);
			}
		});

        tela = new JPanel() {
            @Override
            public void paintComponent(Graphics g){
				g.setColor(Color.black);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
                for(Player player : players){
					g.drawImage(player.imagePlayer, player.getPosX(), player.getPosY(), rootPane);
                }
				for(Zombie zombie : zombies){
					g.drawImage(zombie.zombieStandard.images.get(zombie.getNextImage()),
								zombie.getPosX(),
								zombie.getPosY(),
								rootPane);
				}
            }
        };
		getContentPane().add(tela);
		setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setVisible(true);
        players = new ArrayList<Player>();
		zombies = new ArrayList<Zombie>();
    }

    public void render(){
        repaint();
    }

    public void addPlayer(int pos_x, int pos_y, int width, int height){
        players.add(new Player(pos_x, pos_y, width, height) );
    }

	public void addZombie(int pos_x, int pos_y, ZombieStandard zombieStandard){
		zombies.add(new Zombie(pos_x, pos_y, zombieStandard));
	}

	public void moveZombie(){
		for(Zombie zombie : zombies){
			zombie.move();
		}
	}

    private void setaTecla(int tecla, boolean pressionada) {
		switch (tecla) {
			case KeyEvent.VK_UP:
				// Seta para cima
				controleTecla[0] = pressionada;
				break;
			case KeyEvent.VK_DOWN:
				// Seta para baixo
				controleTecla[1] = pressionada;
				break;
			case KeyEvent.VK_LEFT:
				// Seta para esquerda
				controleTecla[2] = pressionada;
				break;
			case KeyEvent.VK_RIGHT:
				// Seta para direita
				controleTecla[3] = pressionada;
				break;
		}
	}
}
