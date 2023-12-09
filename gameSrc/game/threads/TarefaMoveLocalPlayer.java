package game.threads;

import client.GameClient;
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
            if(janela.controleTecla[0] && 
                localPlayer.getPosY() > 20){
                localPlayer.walkUp();
                msg += "walk;up;" + localPlayer.getPosX() +
                        ";" + localPlayer.getPosY() + ";";
                localPlayer.setWalking(true);
                localPlayer.updateNextImage();
            }
            else if(janela.controleTecla[1] &&
                localPlayer.getPosY() < janela.getHeight() - 130){
                localPlayer.walkDown();
                msg += "walk;down;" + localPlayer.getPosX() +
                        ";" + localPlayer.getPosY() + ";";
                localPlayer.setWalking(true);
                localPlayer.updateNextImage();
            }
            else if(janela.controleTecla[2] &&
                localPlayer.getPosX() > 0){
                localPlayer.walkLeft();
                msg += "walk;left;" + localPlayer.getPosX() +
                        ";" + localPlayer.getPosY() + ";";
                localPlayer.setWalking(true);
                localPlayer.updateNextImage();
            }
            else if(janela.controleTecla[3] &&
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
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }  
}
