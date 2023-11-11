package game.logica.janela;

import game.logica.player.BulletStandard;

public class TarefaMove implements Runnable{

    private Janela janela;
    private int index;
    private BulletStandard bulletStandard;

    public TarefaMove(Janela janela, int index, BulletStandard bulletStandard){
        this.janela = janela;
        this.index = index;
        this.bulletStandard = bulletStandard;
    }
    @Override
    public void run() {
        if(janela.controleTecla[4] &&
            janela.players.get(index).getNextImageReloading() == - 1){
            janela.players.get(index).updateNextImageShooting();
            janela.players.get(index).setShooting(true);
        }
        else{
            janela.players.get(index).setShooting(false);
        }
        if(janela.controleTecla[5] &&
            janela.players.get(index).getNextImageReloading() == -1){

            janela.players.get(index).reload();
            janela.players.get(index).updateNextImageReloading();
        }
        if(janela.controleTecla[0] && 
            janela.players.get(index).getPosY() > 20){
            System.out.println(janela.players.get(index).getPosX() + " " + janela.players.get(index).getPosY());
            janela.players.get(index).walkUp();
            janela.players.get(index).setWalking(true);
            janela.players.get(index).updateNextImage();
        }
        else if(janela.controleTecla[1] &&
            janela.players.get(index).getPosY() < janela.getHeight() - 130){
            System.out.println(janela.players.get(index).getPosX() + " " + janela.players.get(index).getPosY());
            janela.players.get(index).walkDown();
            janela.players.get(index).setWalking(true);
            janela.players.get(index).updateNextImage();
        }
        else if(janela.controleTecla[2] &&
            janela.players.get(index).getPosX() > 0){
            System.out.println(janela.players.get(index).getPosX() + " " + janela.players.get(index).getPosY());
            janela.players.get(index).walkLeft();
            janela.players.get(index).setWalking(true);
            janela.players.get(index).updateNextImage();
        }
        else if(janela.controleTecla[3] &&
            janela.players.get(index).getPosX() < janela.getWidth() - 100){
            System.out.println(janela.players.get(index).getPosX() + " " + janela.players.get(index).getPosY());
            janela.players.get(index).walkRight();
            janela.players.get(index).setWalking(true);
            janela.players.get(index).updateNextImage();
        }
        else{
            janela.players.get(index).setWalking(false);
        }   
    }  
}
