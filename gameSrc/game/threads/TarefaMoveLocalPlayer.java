package game.threads;

import game.logica.player.BulletStandard;

import game.logica.janela.Janela;

import game.logica.player.Player;

public class TarefaMoveLocalPlayer implements Runnable{

    private Janela janela;
    private Player localPlayer;
    private BulletStandard bulletStandard;

    public TarefaMoveLocalPlayer(Janela janela, Player localPlayer, BulletStandard bulletStandard){
        this.janela = janela;
        this.localPlayer = localPlayer;
        this.bulletStandard = bulletStandard;
    }
    @Override
    public void run() {
        while(true){
            if(janela.controleTecla[0] && 
                localPlayer.getPosY() > 20){
                localPlayer.walkUp();
                localPlayer.setWalking(true);
                localPlayer.updateNextImage();
            }
            else if(janela.controleTecla[1] &&
                localPlayer.getPosY() < janela.getHeight() - 130){
                localPlayer.walkDown();
                localPlayer.setWalking(true);
                localPlayer.updateNextImage();
            }
            else if(janela.controleTecla[2] &&
                localPlayer.getPosX() > 0){
                localPlayer.walkLeft();
                localPlayer.setWalking(true);
                localPlayer.updateNextImage();
            }
            else if(janela.controleTecla[3] &&
                localPlayer.getPosX() < janela.getWidth() - 100){
                localPlayer.walkRight();
                localPlayer.setWalking(true);
                localPlayer.updateNextImage();
            }
            else{
                localPlayer.setWalking(false);
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }  
}
