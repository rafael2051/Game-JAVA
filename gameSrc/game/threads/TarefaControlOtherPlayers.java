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
    private Player player;

    public TarefaControlOtherPlayers(Janela janela, BulletStandard bulletStandard,
                                     Player player){
        this.janela = janela;
        this.bulletStandard = bulletStandard;
        apiPlayerClient = ApiPlayerClient.getInstance();
        this.player = player;
    }

    @Override
    public void run() {
        while(true){
            if(apiPlayerClient.getThereIsMessage(player.getId())){
                String msg = apiPlayerClient.getMessage(player.getId());
                apiPlayerClient.setThereIsMessage(false, player.getId());
                String[] parametersMessage = msg.split(";");
                    if(parametersMessage[1].equals("donothing")){
                        player.setWalking(false);
                    }

                    if (parametersMessage[1].equals("walk")) {
                        System.out.println(msg);
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
                        if(parametersMessage.length == 6) {
                            try {
                                int realPosX = Integer.parseInt(parametersMessage[3]);
                                int realPosY = Integer.parseInt(parametersMessage[4]);
                                if(player.getPosX() != realPosX || player.getPosY() != realPosY){
                                    player.setPosX(realPosX);
                                    player.setPosY(realPosY);
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if(parametersMessage[1].equals("shooting")){

                    }
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
