import music.musicStuff;
import view.ChessGameFrame;
import music.BackgroundMusic;
import javax.swing.*;
import java.io.File;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            ChessGameFrame mainFrame = new ChessGameFrame(1000, 760);
            mainFrame.setVisible(true);

            BackgroundMusic click = new BackgroundMusic();
            new Thread(()->{
                click.playMusic("./click.wav"); //while中的true可换成参数来控制音乐的停止播放
            }).start();

            File file = new File(".");
            //菊次郎的夏天.wav
            String filepath = "";//./1.wav
            musicStuff musicObject = new musicStuff();
            musicObject.playMusic(filepath);
//            BackgroundMusic music1 = new BackgroundMusic();
//            new Thread(()->{
//                music1.playMusic("./菊次郎的夏天.wav");
//            }).start();
        });
    }
}