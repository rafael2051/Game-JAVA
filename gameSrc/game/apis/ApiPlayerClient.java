package game.apis;

import java.util.ArrayList;

/*
 * Interface between the gameClient thread and the
 * threads that manipulate the players
 */

public class ApiPlayerClient {
    private static final ApiPlayerClient apiPlayerClient = new ApiPlayerClient();
    private List<PlayerPos> listPlayerPos;

    private ApiPlayerClient(){
        listPlayerPos = new ArrayList<PlayerPos>();
    }

    private class PlayerPos{
        private int pos_x;
        private int pos_y;
        private int idPlayer;
        private boolean isLocalPlayer;
        private PlayerPos(int pos_x, int pos_y, int idPlayer, boolean isLocalPlayer){
            this.pos_x = pos_x;
            this.pos_y = pos_y;
            this.idPlayer = idPlayer;
            this.isLocalPlayer = isLocalPlayer;
        }
        private int getPos_X(){
            return pos_x;
        }
        private int getPos_Y(){
            return pos_y;
        }
        private boolean isLocalPlayer(){
            return isLocalPlayer;
        }

    }
}
