import javax.sound.sampled.*;

public class SoundUtil{
    static AudioFormat format = new AudioFormat(2560, 16, 2, true, true);
    /**
     *  u can use this method to
     *  transfer byte data array to voice
     * @param voiceData
     * @throws LineUnavailableException
     */
    static void playSound (byte[] voiceData) {
        DataLine.Info sourceInfo = new DataLine.Info(SourceDataLine.class, format);
        SourceDataLine sourceLine = null;
        try {
            sourceLine = (SourceDataLine) AudioSystem.getLine(sourceInfo);
            sourceLine.open(format);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        sourceLine.start();
        sourceLine.write(voiceData, 0, voiceData.length);
    }


    /**
     *  u can use this method to
     *  capture voice from microphone
     * @return byte[]
     * @throws LineUnavailableException
     */

    static byte[] getSound ()  {
        byte[] voiceData = new byte[1024] ;
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        TargetDataLine microPhone = null;
        try {
            microPhone = (TargetDataLine) AudioSystem.getLine(info);
            microPhone.open(format);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        int chuckSize = 1024;
        microPhone.start();
        while (true) {
           voiceData = new byte[microPhone.getBufferSize()/5] ;
           int num = microPhone.read(voiceData, 0, voiceData.length);
           if (num == chuckSize) {
               break;
           }
        }
        return voiceData;
    }
}
