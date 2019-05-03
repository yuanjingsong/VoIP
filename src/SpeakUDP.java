/**
 * IP is the other one's ip
 * using UDP
 * <p>
 * default UDP Port is UDPPORT
 */

import java.io.IOException;

public class SpeakUDP implements Runnable {
    private String ip;
    private int port;

    private SpeakUDP(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println(ip + " " + port + " speakUDP");
        byte[] voiceData;
        while (true) {
            voiceData = SoundUtil.getSound();
            System.out.println(voiceData);
            try {
                Network.sendDataUsingUDP(ip, port, voiceData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
