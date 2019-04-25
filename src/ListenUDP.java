import javax.sound.sampled.*;
import java.io.IOException;
import java.net.*;

/**
 * A class use to listen to a specify UDP port
 * if some one call and then delivery the data
 * and then transfer the data to the sound
 */
public class ListenUDP implements Runnable{
    int Port;
    DatagramSocket socket;
    public ListenUDP(String host, int port) throws UnknownHostException, SocketException {
        InetAddress socket = InetAddress.getByName(host);
        Port = port;
        this.socket = new DatagramSocket(port, socket);
        System.out.println("now ths listen udp port is : " + port);
    }

    @Override
    public void run() {
        byte [] buf = new byte[1024];
        DatagramPacket dp_receive = new DatagramPacket(buf, 1024);
        boolean f = true;
        while(f) {
            try {
                socket.receive(dp_receive);
            } catch (IOException e) {
                e.printStackTrace();
            }
            SoundUtil.playSound(buf);
        }
    }

}
