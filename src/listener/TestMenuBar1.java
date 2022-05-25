package listener;

import java.awt.*;
import javax.swing.*;
public class TestMenuBar1 {
    public static void main(String arg[]) {
        createNewMenu ck=new createNewMenu("第一个窗口");
    }
}
class createNewMenu extends JFrame{
    public createNewMenu(String title) {
        setTitle(title);
        setBounds(200,200,500,500);
        setVisible(true);
        this.getContentPane().setBackground(Color.blue);
    }}

