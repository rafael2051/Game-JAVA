package game.apis;

import java.util.ArrayList;
import java.util.List;

/*
 * Interface between the gameClient thread and the
 * threads that manipulate the players
 */

public class ApiPlayerClient {
    private static final ApiPlayerClient apiPlayerClient = new ApiPlayerClient();
    private List<PlayerPosInitial> listPlayerPos;
    private int noPlayers;

    private List<String> bufferMessages;

    private boolean lockBuffer;

    private ApiPlayerClient(){
        listPlayerPos = new ArrayList<PlayerPosInitial>();
        lockBuffer = false;
    }

    public static ApiPlayerClient getInstance(){
        return apiPlayerClient;
    }

    public void addPlayersPos(int id, int posX, int posY, boolean isLocalPlayer){
        listPlayerPos.add(new PlayerPosInitial(id, posX, posY, isLocalPlayer));
    }

    public void setNoPlayers(int noPlayers){
        this.noPlayers = noPlayers;
    }

    public int getNoPlayers(){
        return noPlayers;
    }

    public String getPlayersPos(){
        String playersPos = "";
        for(PlayerPosInitial playerPos : listPlayerPos){
            playersPos += playerPos.getIdPlayer() + "/";
            playersPos += playerPos.getPos_X() + "/";
            playersPos += playerPos.getPos_Y() + "/";
            playersPos += playerPos.getIsLocalPlayer();
            playersPos += ";";
        }
        return playersPos;
    }

    public void addMessage(String message){
        while(lockBuffer){
            continue;
        }
        lockBuffer = true;
        bufferMessages.add(message);
        lockBuffer = false;
    }

    public String getNextMessage(int id){
        while (lockBuffer){
            continue;
        }
        lockBuffer = true;
        for(String message : bufferMessages){
            String[] parametersMessage = message.split(";");
            if(Integer.parseInt(parametersMessage[0]) == id){
                return message;
            }
        }
        return "default";
    }

    private class PlayerPosInitial{
        private int idPlayer;
        private int pos_x;
        private int pos_y;
        private boolean isLocalPlayer;
        private PlayerPosInitial( int idPlayer, int pos_x, int pos_y, boolean isLocalPlayer){
            this.idPlayer = idPlayer;
            this.pos_x = pos_x;
            this.pos_y = pos_y;
            this.isLocalPlayer = isLocalPlayer;
        }
        private int getIdPlayer(){
            return idPlayer;
        }
        private int getPos_X(){
            return pos_x;
        }
        private int getPos_Y(){
            return pos_y;
        }
        private boolean getIsLocalPlayer(){
            return isLocalPlayer;
        }

    }
}
