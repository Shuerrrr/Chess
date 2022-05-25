package music;
import java.io.File;
import javax.sound.sampled.*;

    public class BackgroundMusic {


    public void playMusic(String path) {// 背景音乐播放
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));    //绝对路径
            AudioFormat aif = ais.getFormat();
            final SourceDataLine sdl;
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, aif);
            sdl = (SourceDataLine) AudioSystem.getLine(info);
            sdl.open(aif);
            sdl.start();
            FloatControl fc = (FloatControl) sdl.getControl(FloatControl.Type.MASTER_GAIN);
            double value = 0.1;
            float dB = (float) (Math.log(value == 0.0 ? 0.0001 : value) / Math.log(10.0) * 20.0);
            fc.setValue(dB);
            int nByte = 0;
            final int SIZE = 1024 * 64;
            byte[] buffer = new byte[SIZE];
            while (nByte != -1) {
                nByte = ais.read(buffer, 0, SIZE);
                sdl.write(buffer, 0, buffer.length);
            }
            sdl.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}