import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class Network {
    /**
     * send the msg using udp method
     * and specify udp port
     *
     * @param ip
     * @param port
     * @param data
     * @throws IOException
     */


    public static void sendDataUsingUDP(String ip, int port, byte[] data) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress targetClient = InetAddress.getByName(ip);
        DatagramPacket packet = new DatagramPacket(data, data.length, targetClient, port);
        socket.send(packet);
    }


    /**
     * send the msg using udp method
     * and port is DEFAULT_SEND_MESSAGE_UDP_PORT (8804)
     *
     * @param ip
     * @param data
     * @throws IOException
     */

    public static void sendDataUsingUDP(String ip, byte[] data) throws IOException {
        sendDataUsingUDP(ip, IpClient.DEFAULT_SEND_MESSAGE_UDP_PORT, data);
    }


    /**
     * send the msg using tcp method
     * and port is DEFAULT_SEND_MESSAGE_TCP_PORT (8002)
     * call method sendDataUsingTCP(String ip, int port, byte[] voiceData)
     *
     * @param ip
     * @param voiceData
     * @throws IOException
     */

    public static void sendDataUsingTCP(String ip, byte[] voiceData) throws IOException {
        sendDataUsingTCP(ip, IpClient.DEFAULT_SEND_MESSAGE_TCP_PORT, voiceData);
    }

    /**
     * send the msg using tcp method
     * and specify the TCP port
     *
     * @param ip
     * @param port
     * @param voiceData
     * @throws IOException
     */


    public static void sendDataUsingTCP(String ip, int port, byte[] voiceData) throws IOException {
        Socket socket = new Socket(ip, port);
        socket.setSoTimeout(1000);
        DataOutputStream dOUt = new DataOutputStream(socket.getOutputStream());
        dOUt.writeInt(voiceData.length);
        dOUt.write(voiceData);

    }

}
