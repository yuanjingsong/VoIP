import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;

/**
 * A class use to listen to a specify UDP port
 * if some one call and then delivery the data
 * and then transfer the data to the sound
 */
public class ListenUDP implements Runnable {
    int Port;
    DatagramSocket socket;
    IpClient ipClient;
    GUI gui;

    //Scanner input;
    public ListenUDP(String host, int port, IpClient ipClient) throws UnknownHostException, SocketException {
        InetAddress socket = InetAddress.getByName(host);
        Port = port;
        this.socket = new DatagramSocket(port, socket);
        this.ipClient = ipClient;
        //this.input = input;
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void run() {

        byte[] buf = new byte[1024];
        DatagramPacket dp_receive = new DatagramPacket(buf, 1024);
        boolean f = true;
        boolean isON = false;

        while (f) {
            try {
                socket.receive(dp_receive);
                if (!isON) {

                    int confirm = GUI.showOptionDialog();
                    if (confirm == 1) {
                        isON = true;
                    } else {
                        return;
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(buf);
            SoundUtil.playSound(buf);
            //speak with other
            this.ipClient.callbyP2P(dp_receive.getAddress().getHostAddress(), dp_receive.getPort());
        }
    }
}
