import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080);
        } catch (Exception e) {
            e.getMessage();
        }
        while(true){
            try {
                Socket clientSocket = serverSocket.accept();
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }
}
