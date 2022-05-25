package view;

import controller.GameController;
import model.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Welcome extends JPanel{
    //欢迎界面加个标签哈
    private JLabel welcome1;
    private JLabel welcome2;
    private JLabel Fig1;
    private ImageIcon fig1;
    public Welcome(int width, int height) {
        this.setSize(width, height);
        this.setVisible(true);
        //this.setLayout(new BorderLayout());

        this.welcome1 = new JLabel();
        this.welcome1.setLocation((int) (width*0.7), 60);
        this.welcome1.setSize((int) (width*1.5), height);
        this.welcome1.setFont(new Font("Times New Roman ", Font.HANGING_BASELINE+Font.BOLD, 50));
        this.welcome1.setText("Welcome To Play International chess !  ");
        welcome1.setForeground(Color.pink);
        add(welcome1);


        this.welcome2 = new JLabel();
        this.welcome2.setLocation((int) (width*0.27), 200);
        this.welcome2.setSize((int) (width*0.7), height);//好像没用。。
        this.welcome2.setFont(new Font("Times New Roman ", Font.ITALIC+Font.BOLD, 20));
        this.welcome2.setText("     Designed by SHU && MIAO");
        welcome2.setForeground(Color.ORANGE);
        add(welcome2);
        this.setOpaque(false); // label不透明度


        File file = new File(".");
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e){
            e.printStackTrace();
        }


        //this.fig1 = new ImageIcon("/Users/zlz/Desktop/1.webp");
        this.Fig1 = new JLabel(fig1);
        this.Fig1.setLocation((int) (width*0.7), 60);
        this.Fig1.setSize((int) (width), 2*height);
        add(Fig1);


    }
}
