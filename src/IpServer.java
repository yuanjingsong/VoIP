import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class IpServer implements Runnable {
    private static int TCPPORT = 7001;
    private static int UDPPORT = 7002;
    Socket socket;
    static int getTCPPORT() {
        return  TCPPORT;
    }

    static int getUDPPORT() {
        return UDPPORT;
    }

    public IpServer(Socket socket) {
        this.socket =socket;
    }

    public void Init() throws IOException {
        ServerSocket ServerSocket = new ServerSocket(TCPPORT);
        ExecutorService threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        Socket socket = null;
        boolean f = true;
        while (f) {
            socket = ServerSocket.accept();
            threadPool.submit(new Thread(new IpServer(socket)));
        }
    }

    public String getIp(Socket socket) {
        InetAddress inetAddress = socket.getInetAddress();
        return inetAddress.getHostAddress();
    }

    public void UpdateIp() {

    }

    @Override
    public void run() {
        try {
            BufferedReader buf = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        }catch (Exception e) {

        }
    }
}
