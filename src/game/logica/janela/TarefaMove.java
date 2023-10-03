package game.logica.janela;


public class TarefaMove implements Runnable{

    private Janela janela;
    private int index;

    public TarefaMove(Janela janela, int index){
        this.janela = janela;
        this.index = index;
    }
    @Override
    public void run() {
            if(janela.controleTecla[0]){
                janela.players.get(index).walkUp();
            }
            else if(janela.controleTecla[1]){
                janela.players.get(index).walkDown();
            }
            else if(janela.controleTecla[2]){
                janela.players.get(index).walkLeft();
            }
            else if(janela.controleTecla[3]){
                janela.players.get(index).walkRight();
        }
    }
   
}
