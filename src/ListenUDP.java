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
        try {
            byte [] buf = new byte[1024];
            DatagramPacket dp_receive = new DatagramPacket(buf, 1024);
            AudioFormat format = new AudioFormat(2560, 16, 2, true, true);
            DataLine.Info sourceInfo = new DataLine.Info(SourceDataLine.class, format);
            boolean f = true;
            while(f) {
                SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(sourceInfo);
                socket.receive(dp_receive);
                String line = new String (buf, 0, buf.length);
                System.out.println(line);
                //transferSound(buf);
                sourceLine.open(format);
                sourceLine.start();
                sourceLine.write(buf, 0, buf.length);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
