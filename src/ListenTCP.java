/** this thread is to listen to the port (8002)
 * in case some body call
 * so if somebody use tcp call
 * this thread will run
 */

import javax.sound.sampled.LineUnavailableException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.BufferUnderflowException;

public class ListenTCP implements Runnable {
    ServerSocket serverSocket;
    //int Port;
    //String IP;

    public ListenTCP(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        Socket socket = null;
        boolean f = true;
        try {
            while(f) {
                socket =  serverSocket.accept();
                DataInputStream dIn = new DataInputStream(socket.getInputStream()) ;
                int length = dIn.readInt();
                if (length > 0) {
                    byte [] voiceData = new byte[length];
                    dIn.readFully(voiceData, 0, voiceData.length);
                    SoundUtil.playSound(voiceData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
