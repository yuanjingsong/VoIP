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

public class IpServer {
    private static int TCPPORT = 7001;
    private static int UDPPORT = 7002;
    Socket socket;
    ExecutorService threadPool;
    ListenUDP listenUDP;
    ListenTCP listenTCP;
    static int getTCPPORT() {
        return  TCPPORT;
    }

    static int getUDPPORT() {
        return UDPPORT;
    }


    public IpServer() throws IOException {
        ServerSocket ServerSocket = new ServerSocket(TCPPORT);
        ExecutorService threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
    }

    public IpServer(Socket socket, ExecutorService threadPool) {
        this.socket =socket;
        this.threadPool = threadPool;
    }


    public static void main(String[] args) throws IOException {
        IpServer ipServer = new IpServer();
    }

    public String getIp(Socket socket) {
        InetAddress inetAddress = socket.getInetAddress();
        return inetAddress.getHostAddress();
    }

    public void UpdateIp() {

    }


}
