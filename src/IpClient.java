
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

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


    public IpClient( String otherIP) throws SocketException, UnknownHostException {
        //this.udpClient = udpClient;

        try {
            this.listenUDP = new ListenUDP(ServerIp, UDPINPORT);
        }catch (Exception e) {
            this.listenUDP = new ListenUDP(ServerIp,UDPINPORT + 1);
            System.out.println("now the listen udp port is " + (UDPINPORT + 1) );
        }

    }

    public static void main(String[] args) throws IOException {
        //UDPClient udpClient = new UDPClient(UDPPORT);
        client tcpClient = new client();

        // name is the who u want to call
        String name =  "";

        //String otherIP = getOtherIp(name);
        String otherIP = "127.0.0.1";
        IpClient ipClient = new IpClient(otherIP);

        ExecutorService threadPool = (ThreadPoolExecutor)Executors.newFixedThreadPool(4);
        threadPool.submit(ipClient.listenUDP);

        Scanner in = new Scanner(System.in);
        String dial = in.nextLine();
        if ("phone".equals(dial)) {
            ipClient.callbyP2P(otherIP);
        }

    }

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
     * use this function to get voice data
      * @param format
     * @return
     */

    public byte [] getVoice(AudioFormat format) {
        byte [] voiceData = new byte[1024];
        try {
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            TargetDataLine microPhone = (TargetDataLine) AudioSystem.getLine(info);
            microPhone.open(format);

            int chuckSize = 1024;
            microPhone.start();

            while(true) {
                voiceData = new byte[microPhone.getBufferSize()/5];
                int num = microPhone.read(voiceData, 0, voiceData.length);
                System.out.println(num);
                if (num == chuckSize) {
                    break;
                    //sendDataUsingUDP("127.0.0.1", voiceData);
                }
            }

        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        return voiceData;

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


    public void callbyP2P(String ip, int port) throws IOException {
        System.out.println(ip + " " + port);
        byte [] voiceData;
        while (true) {
            AudioFormat format = new AudioFormat(2560, 16, 2, true, true);
            voiceData = getVoice(format);
            sendDataUsingUDP(ip, voiceData);
        }

    }

    /**
     * send the msg using udp method
     * and port is DEFAULT_SEND_MESSAGE_UDP_PORT (8804)
     * @param ip
     * @param data
     * @throws IOException
     */

    public void sendDataUsingUDP(String ip ,byte [] data) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress targetClient = InetAddress.getByName(ip);
        DatagramPacket packet = new DatagramPacket(data, data.length, targetClient, DEFAULT_SEND_MESSAGE_UDP_PORT);
        socket.send(packet);

    }


    /**
     * send the msg using tcp method
     * and port is DEFAULT_SEND_MESSAGE_TCP_PORT (8002)
     * @param ip
     * @param voiceData
     * @throws IOException
     */

    public void sendDataUsingTCP(String ip, byte [] voiceData) throws IOException {
        Socket socket = new Socket(ip, TCPINPORT);
        socket.setSoTimeout(1000);
        DataOutputStream dOUt = new DataOutputStream(socket.getOutputStream());
        dOUt.writeInt(voiceData.length);
        dOUt.write(voiceData);
   }
}
