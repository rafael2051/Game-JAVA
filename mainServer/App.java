import server.Server;

public class App{
    public static void main(String[] args) {
        Thread server = new Server();
        server.start();
    }
}