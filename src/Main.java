import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main{
    static int SERVERPORT = 20006;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SERVERPORT);
        ExecutorService threadPool = Executors.newFixedThreadPool(20);
        Socket client = null;
        boolean f = true;
        int cnt = 0;
        while (f) {
            client = serverSocket.accept();
            cnt ++;
            System.out.println( cnt + "th client accept success");
            threadPool.submit(new Thread(new Server(client)));
        }
    }
}
