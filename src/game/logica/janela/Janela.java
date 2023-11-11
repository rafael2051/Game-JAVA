package game.logica.janela;


import java.awt.Graphics;
import java.awt.Color;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.logica.player.Bullet;
import game.logica.player.BulletStandard;

import game.logica.player.Player;
import game.logica.player.TarefaControlShootRate;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.logica.zombie.Zombie;
import javax.imageio.ImageIO;

import java.io.IOException;
import java.io.File;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class Janela extends JFrame{
    public JPanel tela;
    public List <Player> players;
    public boolean[] controleTecla = new boolean[6];
	public List <Zombie> zombies;
	public List <Bullet> bullets;
	private BulletStandard bulletStandard;
	private int fortressHP;
	private int damageTaken;

	private Image main_menu_start;
	private Image main_menu_exit;

	private Integer status;

	private Image scenario;
	private Image death_image;
    
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

				if(status == 0){
					g.drawImage(main_menu_start, 0, 0, rootPane);
				}

				else if(players.get(0).getHP() > 0 && fortressHP > 0){
					g.drawImage(scenario, 0, 0, rootPane);
					
					for(Zombie zombie : zombies){
						if(zombie.isDead()){
							if(zombie.isAboutTimeToRemove()){
								zombies.remove(zombie);
							}
							else{
								g.drawImage(zombie.zombieStandard.graveyard, 
											zombie.getPosX(),
											zombie.getPosY(), 
											rootPane);
							}
						}
						else if(zombie.getAttacking()){
							g.drawImage(zombie.zombieStandard.attack.get(zombie.getNextImageAttack()),
									zombie.getPosX(),
									zombie.getPosY(),
									rootPane);
						} 
						else{g.drawImage(zombie.zombieStandard.images.get(zombie.getNextImage()),
									zombie.getPosX(),
									zombie.getPosY(),
									rootPane);
						}
					}
					for(Player player : players){
						g.setColor(Color.green);
						g.drawString("PLAYER_1", player.getPosX(), player.getPosY() - 10);
						g.drawString("AMMO: " + player.getAmmo(), player.getPosX(), player.getPosY());
						g.drawString("HP: " + Integer.toString(player.getHP()), player.getPosX(), player.getPosY() + 10);
						if(player.getWalking()){
							g.drawImage(player.walk.get(player.getNextImage()),
										player.getPosX(),
										player.getPosY() + 20,
										rootPane);
						}
						if(player.getShooting() && player.getAmmo() > 0){
							g.drawImage(player.shoot.get(player.getNextImageShooting()),
										player.getPosX(),
										player.getPosY(),
										rootPane);
						}
						else if(player.getNextImageReloading() >= 0){
							player.updateNextImageReloading();
							g.drawImage(player.reload.get(player.getNextImageReloading()),
										player.getPosX(),
										player.getPosY(),
										rootPane);
						}
						else{
							g.drawImage(player.imagePlayer, player.getPosX(), player.getPosY(), rootPane);
						}
					}
					for(Bullet bullet : bullets){
						g.drawImage(bullet.bulletStandard.bullet,
									bullet.getPosX(),
									bullet.getPosY(),
									rootPane);
					}
				}
				else{
					try{
					Thread.sleep(1000);
					}
					catch(Exception e){
						e.printStackTrace();
					}
					g.drawImage(death_image, 0, 0, rootPane);
				}
            }
        };

		BufferedImage buffered_main_menu_start = null;
		BufferedImage buffered_main_menu_exit = null;

		try{
			buffered_main_menu_start = ImageIO.read(new File("game\\images\\main_menu\\start.jpg"));
			buffered_main_menu_exit = ImageIO.read(new File("game\\images\\main_menu\\exit.jpg"));
		} catch(IOException e){
			System.out.println("ERROR!");
			e.printStackTrace();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		main_menu_start = buffered_main_menu_start.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		main_menu_exit = buffered_main_menu_exit.getScaledInstance(width, height, Image.SCALE_SMOOTH);

		BufferedImage bufferedImage = null;
		BufferedImage bufferedImageDeath = null;

		try{
			bufferedImage = ImageIO.read(new File("game\\images\\scenario\\floor_1.jpg"));
			bufferedImageDeath = ImageIO.read(new File("game\\images\\scenario\\death_image2.jpg"));
		}catch (IOException e){
			System.out.println("ERROR!");
			e.printStackTrace();
			System.exit(1);
		}

		scenario = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		death_image = bufferedImageDeath.getScaledInstance(width, height, Image.SCALE_SMOOTH);

		if(scenario == null){
			System.out.println("ERROR!");
		}


		this.bulletStandard = new BulletStandard(5, 1);
		getContentPane().add(tela);
		setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
		fortressHP = 100;
		damageTaken = 10;
		
        setVisible(true);
        players = new ArrayList<Player>();
		zombies = new ArrayList<Zombie>();
		bullets = new ArrayList<Bullet>();

		status = 1;
    }

    public void render(){
        repaint();
    }

    public void addPlayer(Player player){
        players.add(player);
    }

	public void addZombie(Zombie zombie){
		zombies.add(zombie);
	}

	public void addBullet(int pos_x, int pos_y, BulletStandard bulletStandard){
		bullets.add(new Bullet(pos_x, pos_y, bulletStandard));
	}

	public void moveBullets(){
		for(Bullet bullet : bullets){
			bullet.move();
		}
	}

	public void movimentaZumbi(){
		for(Zombie zumbi : zombies){
			zumbi.move();
		}
	}

	public void decreasesHP(){
		fortressHP -= damageTaken;
	}

	public Integer getStatus(){
		return status;
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
			case KeyEvent.VK_S:
				// Atirar
				controleTecla[4] = pressionada;
				break;
			case KeyEvent.VK_R:
				// Carregar
				controleTecla[5] = pressionada;
				break;
		}
	}
}
