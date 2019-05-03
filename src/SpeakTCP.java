import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SpeakTCP implements Runnable {

    String ip;
    int port;

    public SpeakTCP(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println(ip + " " + port + "using TCP");
        byte[] voiceData;
        while (true) {
            voiceData = SoundUtil.getSound();
            try {
                Network.sendDataUsingTCP(ip, port, voiceData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
