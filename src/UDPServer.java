import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;

public class UDPServer {
    static final int UDPSERVERPORT = 3000;
    public static void main(String[] args) throws IOException {
        byte[] buf = new byte[1024];
        DatagramSocket ds = new DatagramSocket(UDPSERVERPORT);
        DatagramPacket dp_receive = new DatagramPacket(buf, 1024);
        boolean f=  true;
        String send = "hello world";
        while(f) {
            ds.receive(dp_receive);
            System.out.println("Server received data from client ");
            String str_receive = new String(dp_receive.getData(),0, dp_receive.getLength())
                            +  "from " + dp_receive.getAddress().getHostAddress() + ":" + dp_receive.getPort();
            System.out.println(str_receive);
            DatagramPacket dp_send = new DatagramPacket(send.getBytes(), send.length(), dp_receive.getAddress(), UDPClient.UDPPORT);
            ds.send(dp_send);
            dp_receive.setLength(1024);
        }
        ds.close();
    }

}
