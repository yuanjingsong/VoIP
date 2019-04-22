import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.*;

public class UDPClient {

    private static final int TIMEOUT = 5000 ;
    private static final int MAXNUM = 5;
    int udpPort;

    public UDPClient(int udpPort) {
        this.udpPort= udpPort;
    }

    public void ClientSendMsg (byte[] data, InetAddress loc, int PORT) throws IOException {
        DatagramPacket msg= new DatagramPacket(data, data.length, loc, PORT);
        DatagramSocket ds = new DatagramSocket(this.udpPort);
        boolean receviedRes = false;
        int reSendTime = 0;
        byte[] receiveData = new byte[1024];
        while(!receviedRes && reSendTime < MAXNUM) {
            ds.send(msg);
            ClientReceiveMsg(receiveData, ds);
        }
    }
    public void ClientReceiveMsg (byte [] data, DatagramSocket ds) throws IOException {
        ds.receive(new DatagramPacket(data, data.length));
    }
}
