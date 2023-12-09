package game.threads;

import game.apis.ApiPlayerClient;
import game.logica.player.BulletStandard;

import game.logica.janela.Janela;

import game.logica.player.Player;

import java.util.ConcurrentModificationException;

public class TarefaControlOtherPlayers implements Runnable {
    private Janela janela;
    private BulletStandard bulletStandard;
    private ApiPlayerClient apiPlayerClient;

    public TarefaControlOtherPlayers(Janela janela, BulletStandard bulletStandard){
        this.janela = janela;
        this.bulletStandard = bulletStandard;
        apiPlayerClient = ApiPlayerClient.getInstance();
    }
    @Override
    public void run() {
        while(true){
            try {
                for (Player player : janela.players) {
                    String message = apiPlayerClient.getNextMessageReceived(player.getId());
                    if (message.equals("default")) {
                        player.setWalking(false);
                        continue;
                    }
                    String[] parametersMessage = message.split(";");
                    if (parametersMessage[1].equals("walk")) {
                        System.out.println(message);
                        if (parametersMessage[2].equals("up") &&
                                player.getPosY() > 20) {
                            player.walkUp();
                            player.setWalking(true);
                            player.updateNextImage();
                        } else if (parametersMessage[2].equals("down") &&
                                player.getPosY() < janela.getHeight() - 130) {
                            player.walkDown();
                            player.setWalking(true);
                            player.updateNextImage();
                        } else if (parametersMessage[2].equals("left") &&
                                player.getPosY() > 0) {
                            player.walkLeft();
                            player.setWalking(true);
                            player.updateNextImage();
                        } else if (parametersMessage[2].equals("right") &&
                                player.getPosX() < janela.getWidth() - 100) {
                            player.walkRight();
                            player.setWalking(true);
                            player.updateNextImage();
                        }
                    }
                }
            } catch(ConcurrentModificationException e){
                e.printStackTrace();
                continue;
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
