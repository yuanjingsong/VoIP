import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * A class use to listen to a specify UDP port
 * if some one call and then delivery the data
 * and then transfer the data to the sound
 */
public class ListenUDP implements Runnable{
    int Port;

    public ListenUDP(int port) {
        Port = port;
    }

    @Override
    public void run() {
        try {
            DatagramSocket ds = new DatagramSocket(this.Port);
            byte [] buf = new byte[1024];
            DatagramPacket dp_receive = new DatagramPacket(buf, 1024);
            boolean f = true;
            while(f) {
                ds.receive(dp_receive);
                transferSound(buf);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void transferSound(byte[] voiceData) {
        System.out.println("call transfert Sound");
        return ;
    }

}
