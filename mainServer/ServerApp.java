import server.Server;

public class ServerApp{
    public static void main(String[] args) {
        Thread server = Server.getInstance();
        server.start();
    }
}