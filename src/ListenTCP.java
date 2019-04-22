import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ListenTCP implements Runnable {
    int Port;
    String IP;

    public ListenTCP(String IP, int port) {
        IP = IP;
        Port = port;
    }

    @Override
    public void run() {
        try {
            Socket listenTCP;
            BufferedReader dataRec;
            listenTCP = new Socket(this.IP, this.Port);
            listenTCP.setSoTimeout(10000);
            dataRec = new BufferedReader(new InputStreamReader(listenTCP.getInputStream()));
            byte[] voiceData;

            boolean flag = true;

            while (flag) {
                String input = dataRec.readLine();
                voiceData = input.getBytes();
                transferSound(voiceData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void transferSound(byte[] voiceData) {

        System.out.println("call transfer sound method");
        return;
    }
}
