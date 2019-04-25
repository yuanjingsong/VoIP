import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.*;

public class UDPClient implements Runnable {

    private static final int TIMEOUT = 5000 ;
    private static final int MAXNUM = 5;
    int udpPort;
    DatagramSocket socket;

    public UDPClient(String host, int udpPort) throws UnknownHostException, SocketException {
        this.udpPort= udpPort;
        InetAddress address = InetAddress.getByName(host);
        this.socket = new DatagramSocket(udpPort, address);
    }

    @Override
    public void run() {

    }

}
