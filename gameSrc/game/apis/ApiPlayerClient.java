package game.apis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;

/*
 * Interface between the gameClient thread and the
 * threads that manipulate the players
 */

public class ApiPlayerClient {
    private static final ApiPlayerClient apiPlayerClient = new ApiPlayerClient();
    private List<PlayerPosInitial> listPlayerPos;
    private int noPlayers;

    private int id;

    private List<String> bufferMessagesRcvd;
    private List<String> bufferMessagesToSend;

    private boolean lockBufferMsgsRcvd;
    private boolean lockBufferMsgsToSend;

    private ApiPlayerClient(){
        listPlayerPos = new ArrayList<PlayerPosInitial>();
        lockBufferMsgsRcvd = false;
        lockBufferMsgsToSend = false;
        bufferMessagesRcvd = new LinkedList<String>();
        bufferMessagesToSend = new LinkedList<String>();
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

    public void setId(int id){ this.id = id;}

    public int getId(){ return id;}

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

    public void addMessageReceived(String message) throws ConcurrentModificationException{
        while(lockBufferMsgsToSend){
            continue;
        }
        bufferMessagesRcvd.add(message);
    }

    public String getNextMessageReceived(int id) throws ConcurrentModificationException{
        lockBufferMsgsRcvd = true;
        for(String message : bufferMessagesRcvd){
            String[] parametersMessage = message.split(";");
            if(Integer.parseInt(parametersMessage[0]) == id){
                bufferMessagesRcvd.remove(message);
                return message;
            }
        }
        lockBufferMsgsRcvd = false;
        return "default";
    }

    public void addMessageToSend(String message) throws ConcurrentModificationException {
        while(lockBufferMsgsToSend){
            continue;
        }
        lockBufferMsgsToSend = true;
        bufferMessagesToSend.add(message);
        lockBufferMsgsToSend = false;
    }

    public boolean checkIfTheresMsgToSend(){
        return !bufferMessagesToSend.isEmpty();
    }

    public String getNextMessageToSend() throws ConcurrentModificationException {
        while (lockBufferMsgsToSend){
            continue;
        }
        lockBufferMsgsToSend = true;
        String msg = bufferMessagesToSend.remove(0);
        lockBufferMsgsToSend = false;
        return msg;
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
