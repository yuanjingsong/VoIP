import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import javax.sound.sampled.*;

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
    private static int DEFAULT_SEND_MESSAGE_UDP_PORT = 8004;
    private static int DEFAULT_SEND_MESSAGE_TCP_PORT = 8002;

    //UDPClient udpClient;
    ListenUDP listenUDP;
    ListenTCP listenTCP;


    public IpClient() throws IOException {
        //this.udpClient = udpClient;

        try {
            this.listenUDP = new ListenUDP(ServerIp, UDPINPORT);
        }catch (Exception e) {
            this.listenUDP = new ListenUDP(ServerIp,UDPINPORT + 1);
            System.out.println("now the listen udp port is " + (UDPINPORT + 1) );
        }
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(TCPINPORT);
            this.listenTCP = new ListenTCP(serverSocket);
        } catch (IOException e) {
            serverSocket = new ServerSocket(TCPINPORT + 1);
            this.listenTCP = new ListenTCP(serverSocket);
        }

    }

    public static void main(String[] args) throws IOException, LineUnavailableException {
        //UDPClient udpClient = new UDPClient(UDPPORT);
        client tcpClient = new client();

        // name is the who u want to call
        String name =  "";
        String dialUP = "phone";
        String usingTCP = "tcp";

        //String otherIP = getOtherIp(name);
        String otherIP = "127.0.0.1";

        IpClient ipClient = new IpClient();

        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        threadPool.submit(ipClient.listenUDP);
        threadPool.submit(ipClient.listenTCP);

        Scanner in = new Scanner(System.in);
        String dial = in.nextLine();
        if (dialUP.equals(dial)) {
            ipClient.callbyP2P(otherIP);
        }else if (usingTCP.equals(dial)) {
            ipClient.callbyTCP(otherIP);
        }

    }


    /**
     *  use this method to login
     *  and update ip UDPINPORT TCPINPORT when login
     *  @param name
     *  @param pass
     *  @throws IOException
     */

    public void login(String name, String pass) throws IOException {
        Socket socket = new Socket(ServerIp, SERVERTCPPORT);
        socket.setSoTimeout(1000);
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(outputStream);
        String str = name + "," + pass;
        pw.write(str);
        pw.flush();
        socket.shutdownOutput();
        outputStream.close();
        pw.close();
        socket.close();
    }

    /**
     *  you can get the other one's IP
     *  by his nickname
     *  using this method
     *  @param name  the one's nickName
     * @return return the other's IP
     */

    public static String getOtherIp(String name) {
        String iP= "";
       return  iP;
    }


    /**
      * @param ip
     * @throws IOException
     * use default port to call
     */

    public void callbyP2P(String ip) throws IOException {
        callbyP2P(ip, UDPINPORT);
    }


    /**
     *
     * @param ip
     * @param port
     * @throws IOException
     *
     *  IP is the other one's ip
     *  using UDP
     *  default UDP Port is UDPPORT
     *
     */


    public void callbyP2P(String ip, int port) throws IOException{
        System.out.println(ip + " " + port);
        byte [] voiceData;
        while (true) {
            voiceData = SoundUtil.getSound();
            System.out.println(voiceData);
            sendDataUsingUDP(ip, voiceData);
        }

    }


    public void callbyTCP(String ip) throws IOException {
       callbyTCP(ip, TCPINPORT);
    }


    public void callbyTCP(String ip, int port) throws IOException {
        System.out.println(ip + " " + port);
        byte [] voiceData;
        while (true) {
            voiceData = SoundUtil.getSound();
            sendDataUsingTCP(ip, voiceData);
        }
    }

    /**
     * send the msg using udp method
     * and specify udp port
     * @param ip
     * @param port
     * @param data
     * @throws IOException
     */


    public void sendDataUsingUDP(String ip, int port ,byte[] data) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress targetClient = InetAddress.getByName(ip);
        DatagramPacket packet = new DatagramPacket(data, data.length, targetClient, port);
        socket.send(packet);
    }


    /**
     * send the msg using udp method
     * and port is DEFAULT_SEND_MESSAGE_UDP_PORT (8804)
     * @param ip
     * @param data
     * @throws IOException
     */

    public void sendDataUsingUDP(String ip ,byte [] data) throws IOException {
        sendDataUsingUDP(ip, DEFAULT_SEND_MESSAGE_UDP_PORT , data);
    }


    /**
     * send the msg using tcp method
     * and port is DEFAULT_SEND_MESSAGE_TCP_PORT (8002)
     * call method sendDataUsingTCP(String ip, int port, byte[] voiceData)
     * @param ip
     * @param voiceData
     * @throws IOException
     */

    public void sendDataUsingTCP(String ip, byte [] voiceData) throws IOException {
        sendDataUsingTCP(ip, DEFAULT_SEND_MESSAGE_TCP_PORT, voiceData);
    }

    /**
     * send the msg using tcp method
     * and specify the TCP port
     * @param ip
     * @param port
     * @param voiceData
     * @throws IOException
     */


    public void sendDataUsingTCP(String ip, int port, byte [] voiceData) throws  IOException{
        Socket socket = new Socket(ip, port);
        socket.setSoTimeout(1000);
        DataOutputStream dOUt = new DataOutputStream(socket.getOutputStream());
        dOUt.writeInt(voiceData.length);
        dOUt.write(voiceData);

    }

}
