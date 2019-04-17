import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class IpClient {
    private String ServerIp = "127.0.0.1";
    private int SERVERTCPPORT = IpServer.getTCPPORT();
    private int SERVERUDPPORT = IpServer.getUDPPORT();
    private int TCPPORT = 8001;
    private int UDPPORT = 8002;
    public static void main(String[] args) {

    }

    public void sendMyIp() throws IOException {
        Socket client = new Socket(ServerIp, SERVERTCPPORT);
        client.setSoTimeout(10000);
        boolean flag =  true;
        while (flag) {
            String str =
        }
        if (client != null){
            client.close();
        }
    }
}
