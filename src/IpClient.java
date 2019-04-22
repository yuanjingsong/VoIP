import java.io.*;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * use a thread to listen to the UDPINPORT in case of someone call by using UDP
 * use a thread to listen to the TCPINPORT in case of someone call by using TCP
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

    UDPClient udpClient;
    ListenUDP listenUDP;
    ListenTCP listenTCP;



    public IpClient(UDPClient udpClient, String otherIP) {
        this.udpClient = udpClient;

        try {
            this.listenUDP = new ListenUDP(UDPINPORT);
        }catch (Exception e) {
            this.listenUDP = new ListenUDP(UDPINPORT + 1);
            System.out.println("now the listen udp port is " + (UDPINPORT + 1) );
        }

        try {
            this.listenTCP = new ListenTCP(otherIP, TCPINPORT);
        }catch (Exception e) {
            this.listenTCP = new ListenTCP(otherIP, (TCPINPORT + 1));
            System.out.println("Now the listen tcp port is " + (TCPINPORT + 1) );
        }
    }

    public static void main(String[] args) throws IOException {
        UDPClient udpClient = new UDPClient(UDPPORT);
        client tcpClient = new client();

        // name is the who u want to call
        String name =  "";

        String otherIP = getOtherIp(name);
        IpClient ipClient = new IpClient(udpClient, otherIP);

        ExecutorService threadPool = (ThreadPoolExecutor)Executors.newFixedThreadPool(4);
        threadPool.submit(ipClient.listenUDP);
        threadPool.submit(ipClient.listenTCP);

        otherIP = "127.0.0.1";

        ipClient.CallbyP2P(otherIP);

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
     * you should use this method to get the voice
     * @return
     */
    public byte[] getVoice() {
        byte [] voiceData  = new byte[1024];

       return voiceData;
    }

    /**
     *
     * @param IP
     *  IP is the other one' s  ip
     *  using UDP
     * */

    public void CallbyP2P(String IP) throws IOException {
        Socket socket = new Socket(IP, UDPPORT);
        byte [] voiceData = getVoice();

    }



}
