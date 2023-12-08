package game.threads;

import game.apis.ApiPlayerClient;
import game.logica.player.BulletStandard;

import game.logica.janela.Janela;

import game.logica.player.Player;

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
            for(Player player : janela.players){
                String message = apiPlayerClient.getNextMessage(player.getId());
                if(message.equals("default")){
                    player.setWalking(false);
                    continue;
                }
                String[] parametersMessage = message.split(";");
                if(parametersMessage[1].equals("walk")){
                    if(parametersMessage[2].equals("up") &&
                        player.getPosY() > 20){
                        player.walkUp();
                    } else if(parametersMessage[2].equals("down") &&
                            player.getPosY() < janela.getHeight() - 130){
                        player.walkDown();
                    } else if(parametersMessage[2].equals("left") &&
                            player.getPosY() > 0){
                        player.walkLeft();
                    } else if(parametersMessage[2].equals("right") &&
                            player.getPosX() < janela.getWidth() - 100){
                        player.walkRight();
                    }
                    player.setWalking(true);
                    player.updateNextImage();
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
