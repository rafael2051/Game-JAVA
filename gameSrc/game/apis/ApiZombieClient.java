package game.apis;

import java.nio.Buffer;
import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.List;

public class ApiZombieClient {
    private static final ApiZombieClient instance = new ApiZombieClient();
    private List<ZombiePos> bufferZombiesPos;
    private boolean canRemove;

    private ApiZombieClient(){
        bufferZombiesPos = new LinkedList<ZombiePos>();
        canRemove = false;
    }

    public static ApiZombieClient getInstance(){
        return instance;
    }

    public void addZombiePosOnBuffer(int posX, int posY){
        bufferZombiesPos.add(new ZombiePos(posX, posY));
        canRemove = true;
    }

    public boolean checkIfMustAddZombie(){
        if(canRemove){
            canRemove = false;
            return true;
        }
        else{
            return false;
        }
    }

    public int[] getPosZombie(){
        int[] arrayPos = new int[2];
        ZombiePos zombiePos = bufferZombiesPos.remove(0);
        arrayPos[0] = zombiePos.getpos_X();
        arrayPos[1] = zombiePos.getPos_Y();
        return arrayPos;
    }


    private class ZombiePos{
        int pos_x;
        int pos_y;
        private ZombiePos(int pos_x, int pos_y) {
            this.pos_x = pos_x;
            this.pos_y = pos_y;
        }
        private int getpos_X(){
            return pos_x;
        }
        private int getPos_Y(){
            return pos_y;
        }
    }
}
