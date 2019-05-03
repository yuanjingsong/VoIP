import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sound.sampled.*;
import javax.swing.*;

/*
 * use a thread to listen to the UDPINPORT in case of someone call by using UDP
 * which means u should use a udp server at UDPINPORT to listen the data
 *  if u try to call someone then create a new thread to call him.
 */

public class IpClient {
    private static String ServerIp = "127.0.0.1";
    private static int SERVERTCPPORT = IpServer.getTCPPORT();
    private static int SERVERUDPPORT = IpServer.getUDPPORT();
    private static int TCPPORT = 8001;
    private static int TCPINPORT = 8002;
    private static int UDPPORT = 8003;
    private static int UDPINPORT = 8004;
    static int DEFAULT_SEND_MESSAGE_UDP_PORT = 8004;
    static int DEFAULT_SEND_MESSAGE_TCP_PORT = 8002;


    //UDPClient udpClient;
    ListenUDP listenUDP;
    ListenTCP listenTCP;
    ExecutorService threadPool;

    public IpClient() throws IOException {
        //this.udpClient = udpClient;
        //input = new Scanner(System.in);

        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(TCPINPORT);
            this.listenTCP = new ListenTCP(serverSocket);
        } catch (IOException e) {
            serverSocket = new ServerSocket(TCPINPORT + 1);
            this.listenTCP = new ListenTCP(serverSocket);
        }
        try {
            this.listenUDP = new ListenUDP(ServerIp, UDPINPORT, this);
        } catch (Exception e) {
            this.listenUDP = new ListenUDP(ServerIp, UDPINPORT + 1, this);
            System.out.println("now the listen udp port is " + (UDPINPORT + 1));
        }

        this.threadPool = Executors.newFixedThreadPool(6);

        threadPool.submit(listenUDP);
        threadPool.submit(listenTCP);

    }

    public static void main(String[] args) throws IOException, LineUnavailableException {
        //UDPClient udpClient = new UDPClient(UDPPORT);
        //client tcpClient = new client();

        //String otherIP = getOtherIp(name);

        IpClient ipClient = new IpClient();
        JFrame frame = new JFrame("GUI");

        GUI gui = new GUI(ipClient);
        frame.setContentPane(gui.root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        ipClient.listenUDP.setGui(gui);

    }


    /**
     * use this method to login
     * and update ip UDPINPORT TCPINPORT to server
     *
     * @throws IOException
     */

    public void login(String ip, String TCPINPORT, String UDPINPORT) throws IOException {
        Socket socket = new Socket(ServerIp, SERVERTCPPORT);
        socket.setSoTimeout(1000);
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(outputStream);
        String str = ip + "," + TCPINPORT + "," + UDPINPORT;
        pw.write(str);
        pw.flush();
        socket.shutdownOutput();
        outputStream.close();
        pw.close();
        socket.close();
    }

    /**
     * you can get the other one's IP
     * by his nickname
     * using this method
     *
     * @param name the one's nickName
     * @return return the other's IP
     */

    public static String getOtherIp(String name) {
        String iP = "";
        return iP;
    }


    /**
     * @param ip
     * @throws IOException use default port to call others by udp
     */

    public void callbyP2P(String ip) throws IOException {
        threadPool.submit(new SpeakUDP(ip, UDPINPORT));
        //threadPool.submit(new SpeakUDP(ip, UDPINPORT + 1));
    }

    public void callbyP2P(String ip, int port) {
        threadPool.submit(new SpeakUDP(ip, port));
    }

    /**
     * use default port to call others by tcp
     *
     * @param ip
     * @throws IOException
     */

    public void callbyTCP(String ip) throws IOException {
        //callbyTCP(ip, TCPINPORT);
        threadPool.submit(new SpeakTCP(ip, TCPINPORT));
    }


    /**
     * using TCP
     * ask server whether ip + port is alive
     * if this alive then u can call it
     * else close the collection
     * @param ip
     * @param port
     */
    public void checkIp(String ip, String port) {

    }

}

