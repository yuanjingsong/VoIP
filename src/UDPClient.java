import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.*;

public class UDPClient {

    private static final int TIMEOUT = 5000 ;
    private static final int MAXNUM = 5;
    static int UDPPORT = 9000;
    public static void main(String args[]) throws IOException {
        byte [] buf = new byte[1024];
        DatagramSocket ds = new DatagramSocket(UDPPORT);
        InetAddress loc = InetAddress.getLocalHost();
        String str;
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        str = input.readLine();
        DatagramPacket dp_send = new DatagramPacket(str.getBytes(), str.length(), loc, UDPServer.UDPSERVERPORT);
        DatagramPacket dp_receive = new DatagramPacket(buf, 1024);
        ds.setSoTimeout(TIMEOUT);
        boolean receviedRes = false;
        int cnt = 0;
        while (!receviedRes && cnt < MAXNUM){
            ds.send(dp_send);
            try {
                ds.receive(dp_receive);
                if (! dp_receive.getAddress().equals(loc)) {
                    throw new IOException("Received packet from unknown sources");
                }else {
                    receviedRes = true;
                }
            }catch (InterruptedIOException e) {
                cnt ++;
                System.out.println("Time out resend msg");
            }
        }
        if (receviedRes) {
            System.out.println(new String(dp_receive.getData(), 0, dp_receive.getLength()));
            dp_receive.setLength(1024);
        }
    }
    public void ClientSendMsg (byte[] data, InetAddress loc, int PORT) throws IOException {
        DatagramPacket msg= new DatagramPacket(data, data.length, loc, PORT);
        DatagramSocket ds = new DatagramSocket(this.UDPPORT);
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
