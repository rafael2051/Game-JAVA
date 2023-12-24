package game.threads;

import client.GameClient;
import game.Keys;
import game.apis.ApiPlayerClient;
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
            String msg = "";
            msg += ApiPlayerClient.getInstance().getId() + ";";
            if(janela.controleTecla[Keys.UP] && 
                localPlayer.getPosY() > 20){
                localPlayer.walkUp();
                msg += "walk;up;" + localPlayer.getPosX() +
                        ";" + localPlayer.getPosY() + ";";
                localPlayer.setWalking(true);
                localPlayer.updateNextImage();
            }
            else if(janela.controleTecla[Keys.DOWN] &&
                localPlayer.getPosY() < janela.getHeight() - 130){
                localPlayer.walkDown();
                msg += "walk;down;" + localPlayer.getPosX() +
                        ";" + localPlayer.getPosY() + ";";
                localPlayer.setWalking(true);
                localPlayer.updateNextImage();
            }
            else if(janela.controleTecla[Keys.LEFT] &&
                localPlayer.getPosX() > 0){
                localPlayer.walkLeft();
                msg += "walk;left;" + localPlayer.getPosX() +
                        ";" + localPlayer.getPosY() + ";";
                localPlayer.setWalking(true);
                localPlayer.updateNextImage();
            }
            else if(janela.controleTecla[Keys.RIGHT] &&
                localPlayer.getPosX() < janela.getWidth() - 100){
                localPlayer.walkRight();
                msg += "walk;right;" + localPlayer.getPosX() +
                        ";" + localPlayer.getPosY() + ";";
                localPlayer.setWalking(true);
                localPlayer.updateNextImage();
            }
            else{
                msg += "donothing;;;;";
                localPlayer.setWalking(false);
            }
            try{
                GameClient.getInstance().sendMessage(msg);
            } catch(Exception e){
                e.printStackTrace();
            }
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }  
}
