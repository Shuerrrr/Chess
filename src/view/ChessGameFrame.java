package view;

import controller.ClickController;
import controller.GameController;
import model.ChessColor;
import music.BackgroundMusic;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame{

    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private ClickController clickController;
    public static GameController gameController;
    private Chessboard chessboard;
    private Welcome welcome;//开始界面
    private JLabel background;//开始界面背景
    private JLabel background1;//游戏界面背景
    private JLabel background3;
    private JLabel background2;//棋盘背景
    private JLabel background4;
    private ImageIcon Fig;
    private ImageIcon Fig2;
    public static JLabel statusLabel;//对局标签
    public JPanel total = new JPanel();
    public static JPanel timeLeft = new JPanel();
    private final Timer timer = new Timer();

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }
//    private void addLabel() {
//        statusLabel=new JLabel("White");
//        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
//        statusLabel.setSize(200, 60);
//        statusLabel.setFont(new Font("宋体", Font.BOLD, 40));
//        add(statusLabel);
//    }
//    public void changePlayer(String str){
//        statusLabel.setText(str);
//        statusLabel.repaint();
//    }

    public ChessGameFrame(int width, int height) {
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;
        this.getContentPane().setBackground(Color.blue);
        Image image = Toolkit.getDefaultToolkit().getImage("D:\\ChessDemo1\\1.jpg");
        setIconImage(image);
/**非常重要！ 设置窗口*/
        EventQueue.invokeLater(() -> {
            //不执行任何操作;要求程序在已注册的 WindowListener 对象的 windowClosing 方法中处理该操作
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    JOptionPane pane=new JOptionPane("tishi");
                    ImageIcon icon=new ImageIcon("./1.jpg","派大星尽力了");
                    pane.setIcon(icon);
                    JDialog dialog=pane.createDialog(pane,"再次确认");
                    dialog.setBackground(Color.BLUE);
                    dialog.setForeground(Color.blue);
                    //JButton button=
                    //dialog.isShowing();dialog.show();
                    JButton button=new JButton("确认");
                    button.setLocation(60,60);
                    //int result1 = JOptionPane.showConfirmDialog(null, "确认退出?", "确认", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
//                    button.addActionListener(e1 -> {
//                        if (resulit==pane.getOptions()[])
//                        System.exit(0);
//                    });
                    pane.add(button);
                    Object selectedValue=pane.getValue();

                    Object[]fruits={"和派大星说再见","再玩会"};
                    int result = JOptionPane.showConfirmDialog(null, "确认退出?", "退出界面",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
                    if(result == JOptionPane.OK_OPTION){
                        System.exit(0);
                    }
                }
            });
            setTitle("Chess Master");
            setVisible(true);
        });
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

//        addNewstart();
//        addChessboard();
//        addLabel();
//        //addHelloButton();
//        addLoadButton();
//        addhuiqiButton();
//		addPicture();
//    }
//    private void addNewstart() {

/**游戏正式界面*/
        JButton save = new JButton("Save");
        save.addActionListener(e -> {
            gameController.save();
        });
        Mousemotion(save,HEIGTH*1.06,HEIGTH/10+600 );
        save.setVisible(false);
        add(save);

        JButton button = new JButton("Review");
        Mousemotion(button,HEIGTH*1.06,HEIGTH / 10+240);
        button.setVisible(false);
        add(button);

        button.addActionListener(e -> {
            gameController.getChessboard().initChessboard();
            gameController.getChessboard().setCurrentColor(ChessColor.WHITE);
            gameController.getChessboard().repaint();
            timeTask timeTask=new timeTask();
            timeTask.timer1.schedule(timeTask,0,500);
            if(statusLabel.getText().equals("White")){
                gameController.getChessboard().setCurrentColor(ChessColor.WHITE);
            }else{
                gameController.getChessboard().setCurrentColor(ChessColor.BLACK);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
            chessboard.swapColor();
        });
/**
 * 添加计时器
 */

        timeLeft.setBounds(0,0,150,20);
        total.setBounds(0,0,150,20);
        timeLeft.setLocation((int) (HEIGTH*1.05), HEIGTH / 10+50);
        total.setLocation((int) (HEIGTH*1.05), HEIGTH / 10+50);
        add(timeLeft);
        add(total);
        timeLeft.setVisible(false);
        total.setVisible(false);
//        timeLeft.setBackground(Color.pink);
        total.setBackground(Color.WHITE);
        timer.schedule(new task(),0,500);
        //timer.schedule(new task(),0,500);

//        JPanel contentPane=new JPanel();
//        contentPane.setLocation(HEIGTH, HEIGHT / 10-10);
//        contentPane.setSize(100,20);
//        JProgressBar progressBar=new JProgressBar();
//        progressBar.setStringPainted(true);
//        progressBar.setLocation(WIDTH, HEIGHT / 10);
//        contentPane.add(progressBar);
//        add(contentPane);
//        contentPane.setVisible(true);


/**
 * 在游戏面板中添加标签
 */
            statusLabel=new JLabel();
            statusLabel=new JLabel("White‘s turn");
            statusLabel.setForeground(Color.pink);
            statusLabel.setLocation((int) (HEIGTH*1.05), HEIGTH / 10);
            statusLabel.setSize(350, 60);
            statusLabel.setFont(new Font("Times New Roman", Font.HANGING_BASELINE+Font.BOLD, 28));
            add(statusLabel);
            statusLabel.setVisible(false);
/**
 * 在游戏面板中添加棋盘
 */
            Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
            gameController = new GameController(chessboard);
            chessboard.setLocation(HEIGTH / 4, (int) (HEIGTH / 14.3));
            add(chessboard);
            this.chessboard=chessboard;
            chessboard.setVisible(false);
/**
 * 添加更换皮肤界面
 */
            JButton changeback=new JButton("Change back");
            Mousemotion(changeback,HEIGTH*1.06,HEIGTH / 10+120 );
            changeback.setVisible(false);
/**
 * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
 */

//问好 弹窗界面 “Hello button！”
//            JButton button = new JButton("Show Hello Here");
//            button.setVerticalTextPosition(SwingConstants.CENTER);//字体竖直在按钮中央,且在图片上
//            button.setHorizontalTextPosition(SwingConstants.CENTER);//字体水平在按钮中央
//            Icon cion2 = new ImageIcon("./test1.jpg");
//            button.setIcon(cion2);// 给按钮设置图片
//            button.setToolTipText("Hello World!");// 鼠标悬停提示
//            button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
//            button.setLocation((int) (HEIGTH*1.06), HEIGTH/10  + 120);
//            button.setSize(200, 60);
//            button.setFont(new Font("Times New Roman", Font.BOLD, 26));
//            button.setForeground(Color.MAGENTA);
//            button.setContentAreaFilled(false); //去除填充背景
//            button.setBorder(null);//去除边框
//            add(button);
//            button.setVisible(false);

//加载按钮//        loadGameBtn.setVerticalTextPosition(SwingConstants.CENTER);//字体竖直在按钮中央
////        loadGameBtn.setHorizontalTextPosition(SwingConstants.CENTER);//字体水平在按钮中央
//
//            //loadGameBtn.setIcon(new ImageIcon("./test1.jpg"));//背景图片
//            //loadGameBtn.setToolTipText("读取之前的存档");// 鼠标悬停提示
////        loadGameBtn.setText("Load");//设置文本
////        loadGameBtn.setLocation((int) (HEIGTH*1.04), HEIGTH / 10 + 240);
////        loadGameBtn.setSize(200, 60);
////        loadGameBtn.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 26));
////        loadGameBtn.setForeground(Color.MAGENTA);
////        loadGameBtn.setContentAreaFilled(false); //去除填充背景
////        loadGameBtn.setBorder(null);//去除边框
////        add(loadGameBtn);
//            JButton loadGameBtn = new JButton("Load");
//
//            loadGameBtn.addActionListener(e -> {
//                System.out.println("Click load");
//                /**
//                 * chooser
//                 */
//                JFileChooser chooser = new JFileChooser();
//                FileNameExtensionFilter filter = new FileNameExtensionFilter(
//                        "txt", "txt");
//                chooser.setFileFilter(filter);
//                Component parent = null;
//                int returnVal = chooser.showOpenDialog(parent);
//                if(returnVal == JFileChooser.APPROVE_OPTION) {
//                    System.out.println("You chose to open this file: " +
//                            chooser.getSelectedFile().getName());
//                }
//            });
//            Mousemotion(loadGameBtn,HEIGTH*1.06,HEIGTH / 10 + 240);
//            loadGameBtn.setVisible(false);
            //}
//悔棋
            JButton regretBtn=new JButton("Regret");
            regretBtn.addActionListener(e ->{
                System.out.println("click huiqi\n");
                chessboard.huiqi();
                timeLeft.repaint();
            });
            //setButton(regretBtn,HEIGTH*1.04,HEIGTH / 10 + 360);
//        regretBtn.setLocation((int) (HEIGTH*1.04), HEIGTH / 10 + 360);
//        regretBtn.setSize(200, 60);
//        regretBtn.setFont(new Font("Times New Roman", Font.BOLD, 26));
//        //regretBtn.setToolTipText("悔棋，可以悔无限次");// 鼠标悬停提示
//        regretBtn.setForeground(Color.MAGENTA);//给字体添加颜色
//        regretBtn.setContentAreaFilled(false); //去除填充背景
//        regretBtn.setBorder(null);//去除边框
//        add(regretBtn);
            Mousemotion(regretBtn,HEIGTH*1.06,HEIGTH / 10 + 360);
            regretBtn.setVisible(false);
//重开键
            JButton restart=new JButton("restart");
            restart.addActionListener(e ->{
                int result = JOptionPane.showConfirmDialog(null, "确认重新开始?", "确认",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if(result == JOptionPane.OK_OPTION){
                    System.out.println("click restart\n");
                    System.out.println("重新计时");
                    gameController.restart();
                    timeLeft.repaint();
                }
            });
            Mousemotion(restart,HEIGTH*1.06, HEIGTH / 10 + 480);
            restart.setVisible(false);
//副三
//返回主菜单
        JButton back = new JButton("");
        Icon cion = new ImageIcon("./back.jpg");
        back.setIcon(cion);// 给按钮设置图片
        back.setSize(100,94);
        back.setLocation(0,0);
        back.setContentAreaFilled(false);
        back.setBorder(null);
        add(back);
        back.setVisible(false);
//棋盘图片
//        restart.setLocation((int) (HEIGTH*1.04), HEIGTH / 10 + 480);
//        restart.setSize(200, 60);
//        restart.setFont(new Font("Times New Roman", Font.BOLD, 26));
//        //restart.setToolTipText("重新开始游戏");// 鼠标悬停提示
//
//        restart.setForeground(Color.MAGENTA);//给字体添加颜色
//        restart.setBackground(Color.red);
//
//        restart.setContentAreaFilled(false); //去除填充背景,即去除 restart.setBackground(Color.red);这一步
//        restart.setBorder(null);//去除边框
//        add(restart);

            background2 = new JLabel();
            ImageIcon icon1 = new ImageIcon("./123.png");
            background2.setIcon(icon1);
            //background.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
            //this.background2.setLocation(HEIGTH / 4, (int) (HEIGTH / 14.3));
            background2.setBounds(HEIGTH / 4, (int) (HEIGTH / 14.3),608,608);
            //this.background2.setSize((int) (this.getWidth()), this.getHeight());
            this.add(this.background2);
            background2.setVisible(false);

        background4 = new JLabel();
        ImageIcon icon3 = new ImageIcon("./chessboard.png");
        background4.setIcon(icon3);
        //background.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        //this.background4.setLocation(HEIGTH / 4, (int) (HEIGTH / 14.3));
        background4.setBounds(HEIGTH / 4, (int) (HEIGTH / 14.3),608,608);
        //this.background4.setSize((int) (this.getWidth()), this.getHeight());
        this.add(this.background4);
        background4.setVisible(false);

        ImageIcon icon2 = new ImageIcon("./木星.jpg");//./src\images\test.png
        this.background3 = new JLabel(icon2);
        //background.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        this.background3.setLocation(0, 0);
        this.background3.setSize((int) (this.getWidth()), this.getHeight());
        add(background3);
        background3.setVisible(false);
//游戏背景图片
            ImageIcon icon = new ImageIcon("./src\\images\\test.jpg");
            this.background1 = new JLabel(icon);
            //background.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
            this.background1.setLocation(0, 0);
            this.background1.setSize((int) (this.getWidth()), this.getHeight());
            add(background1);
            background1.setVisible(false);

//更换皮肤changback
            changeback.addActionListener(e ->{
                if (background1.isShowing()){
                    System.out.println("click background changed\n");
                    background3.setVisible(true);
                    background1.setVisible(false);
                    background2.setVisible(false);
                    background4.setVisible(true);
                }else {
                    background1.setVisible(true);
                    background3.setVisible(false);
                    background2.setVisible(true);
                    background4.setVisible(false);
                }
            });

/**
 * 开始界面
 * */

        welcome = new Welcome(1000,100);
        welcome.setLocation(-8,50);//决定位置
        add(welcome);
        this.welcome=welcome;

//


//游戏开始界面

        //主一
        JButton letsPlay = new JButton(("PLAY NOW!"));
        letsPlay.setForeground(Color.ORANGE);
        letsPlay.setVisible(true);
        letsPlay.setSize(295, 60);
        letsPlay.setLocation((int)(this.getWidth()*0.345), (int)(this.getHeight()*0.4));
        letsPlay.setBackground(null);
        letsPlay.setContentAreaFilled(false); //去除填充背景
        letsPlay.setBorder(null);//去除边框
        add(letsPlay);
        letsPlay.setFont(new Font("Times New Roman" , Font.CENTER_BASELINE, 50));

        // 为按钮添加鼠标事件
        Container c = this.getContentPane();
        letsPlay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                letsPlay.setContentAreaFilled(true);
                letsPlay.setBackground(Color.pink);
                BackgroundMusic click = new BackgroundMusic();
                new Thread(()->{
                    click.playMusic("./down.wav"); //while中的true可换成参数来控制音乐的停止播放
                }).start();
                // 鼠标进入改变按钮颜色
            }

            @Override
            public void mouseExited(MouseEvent e) {
                letsPlay.setBackground(null);
                letsPlay.setContentAreaFilled(false);
                // 鼠标离开改变按钮颜色
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                BackgroundMusic click = new BackgroundMusic();
                new Thread(()->{
                    click.playMusic("D:\\ChessDemo1\\enter.wav"); //while中的true可换成参数来控制音乐的停止播放
                }).start();
            }
        });
        c.add(letsPlay);

        //主二
        JButton AiPlay = new JButton(("PLAY with AI!"));
        AiPlay.setForeground(Color.ORANGE);
        AiPlay.setVisible(true);
        AiPlay.setSize(240, 30);
        AiPlay.setLocation((int)(this.getWidth()*0.37), (int)(this.getHeight()*0.56));
        AiPlay.setBackground(null);
        AiPlay.setContentAreaFilled(false); //去除填充背景
        AiPlay.setBorder(null);//去除边框
        add(AiPlay);

        AiPlay.setFont(new Font("Times New Roman" , Font.BOLD, 36));

        Container c1 = this.getContentPane();
        AiPlay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                AiPlay.setContentAreaFilled(true);
                AiPlay.setBackground(Color.pink);
                BackgroundMusic click = new BackgroundMusic();
                new Thread(()->{
                    click.playMusic("./down.wav"); //while中的true可换成参数来控制音乐的停止播放
                }).start();
                // 鼠标进入改变按钮颜色
            }

            @Override
            public void mouseExited(MouseEvent e) {
                AiPlay.setBackground(null);
                AiPlay.setContentAreaFilled(false);
                // 鼠标离开改变按钮颜色
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                BackgroundMusic click = new BackgroundMusic();
                new Thread(()->{
                    click.playMusic("D:\\ChessDemo1\\enter.wav"); //while中的true可换成参数来控制音乐的停止播放
                }).start();
            }
        });
            c1.add(AiPlay);

//加载按钮
        JButton loadGameBtn = new JButton("Load");
        loadGameBtn.setForeground(Color.ORANGE);
        loadGameBtn.setVisible(true);
        loadGameBtn.setSize(240, 30);
        loadGameBtn.setLocation((int)(this.getWidth()*0.37), (int)(this.getHeight()*0.65));
        loadGameBtn.setBackground(null);
        loadGameBtn.setContentAreaFilled(false); //去除填充背景
        loadGameBtn.setBorder(null);//去除边框
        add(loadGameBtn);

        loadGameBtn.setFont(new Font("Times New Roman" , Font.BOLD, 36));

        loadGameBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loadGameBtn.setContentAreaFilled(true);
                loadGameBtn.setBackground(Color.pink);
                BackgroundMusic click = new BackgroundMusic();
                new Thread(()->{
                    click.playMusic("./down.wav"); //while中的true可换成参数来控制音乐的停止播放
                }).start();
                // 鼠标进入改变按钮颜色
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loadGameBtn.setBackground(null);
                loadGameBtn.setContentAreaFilled(false);
                // 鼠标离开改变按钮颜色
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                BackgroundMusic click = new BackgroundMusic();
                new Thread(()->{
                    click.playMusic("D:\\ChessDemo1\\enter.wav"); //while中的true可换成参数来控制音乐的停止播放
                }).start();
            }
        });
        c1.add(loadGameBtn);

//非常重要！！-设置首页图片
        this.Fig2 = new ImageIcon("./1.png");
        Fig2.setImage(Fig2.getImage().getScaledInstance(200, 100, 0));
        this.Fig = new ImageIcon("./test.jpg");//背景图
        this.background = new JLabel(Fig);
        this.background.setLocation(0, 0);
        this.background.setSize((int) (this.getWidth()), this.getHeight());
        this.add(this.background);
/**进入游戏界面时
 *
 */
        loadGameBtn.addActionListener(e -> {
            System.out.println("Click load");
            /**
             * chooser
             */
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "txt", "txt");
            chooser.setFileFilter(filter);
            Component parent = null;
            int returnVal = chooser.showOpenDialog(parent);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                System.out.println("You chose to open this file: " +
                        chooser.getSelectedFile().getName());
                gameController.loadGameFromFile(chooser.getSelectedFile().getName());

                letsPlay.setVisible(false);
                AiPlay.setVisible(false);
                welcome.setVisible(false);
                background.setVisible(false);
                loadGameBtn.setVisible(false);

                total.setVisible(true);
                timeLeft.setVisible(true);
                statusLabel.setVisible(true);
                button.setVisible(true);
                save.setVisible(true);
                restart.setVisible(true);
                regretBtn.setVisible(true);
                background2.setVisible(true);
                background1.setVisible(true);
                background3.setVisible(true);
                chessboard.setVisible(true);
                back.setVisible(true);
                changeback.setVisible(true);
            }
        });
/**
 * AI待修改
 * JPanel panel = new JPanel();
 *         // 添加面板
 *         add(panel);
 *         /*
 *          * 调用用户定义的方法并添加组件到面板
 *
 *placeComponents(panel);
 */
        AiPlay.addActionListener(e -> {
            System.out.println("Start Playing\n");
            BackgroundMusic click = new BackgroundMusic();
            new Thread(()->{
                click.playMusic("D:\\ChessDemo1\\enter.wav"); //while中的true可换成参数来控制音乐的停止播放
            }).start();

            letsPlay.setVisible(false);
            AiPlay.setVisible(false);
            welcome.setVisible(false);
            background.setVisible(false);

            total.setVisible(true);
            timeLeft.setVisible(true);

            ChessGameFrame.timeLeft.setSize(150,20);
            ChessGameFrame.timeLeft.repaint();
            statusLabel.setVisible(true);
            save.setVisible(true);
            button.setVisible(true);
            restart.setVisible(true);
            regretBtn.setVisible(true);
            loadGameBtn.setVisible(true);
            background2.setVisible(true);
            background1.setVisible(true);
            background3.setVisible(true);
            chessboard.setVisible(true);
            back.setVisible(true);
            changeback.setVisible(true);
        });

        letsPlay.addActionListener(e -> {
            System.out.println("Start Playing\n");
            BackgroundMusic click = new BackgroundMusic();
            new Thread(()->{
                click.playMusic("D:\\ChessDemo1\\enter.wav"); //while中的true可换成参数来控制音乐的停止播放
            }).start();

            letsPlay.setVisible(false);
            AiPlay.setVisible(false);
            welcome.setVisible(false);
            background.setVisible(false);
            loadGameBtn.setVisible(false);
            //Progress play=new Progress(progressBar);

//            progressBar.getMinimum();
//            new Progress(progressBar).start();
//            progressBar.setVisible(true);

            total.setVisible(true);
            timeLeft.setVisible(true);

            ChessGameFrame.timeLeft.setSize(150,20);
            ChessGameFrame.timeLeft.repaint();
            statusLabel.setVisible(true);
            save.setVisible(true);
            button.setVisible(true);
            restart.setVisible(true);
            regretBtn.setVisible(true);
            background2.setVisible(false);
            background1.setVisible(true);
            background3.setVisible(false);
            chessboard.setVisible(true);
            back.setVisible(true);
            changeback.setVisible(true);
        });

        back.addActionListener(e1 -> {
            int result = JOptionPane.showConfirmDialog(null, "确认重新开始?", "确认",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if(result == JOptionPane.OK_OPTION){
                letsPlay.setVisible(true);
                AiPlay.setVisible(true);
                welcome.setVisible(true);
                background.setVisible(true);
                loadGameBtn.setVisible(true);
//                progressBar.setVisible(false);
//                progressBar.setValue(0);
                //new Progress1(progressBar);
                //new Progress(progressBar).interrupt();
                total.setVisible(false);
                timeLeft.setVisible(false);

                //timer.cancel();
                statusLabel.setVisible(false);
                save.setVisible(false);
                button.setVisible(false);
                restart.setVisible(false);
                regretBtn.setVisible(false);
                background3.setVisible(false);
                background2.setVisible(false);
                background1.setVisible(false);
                background4.setVisible(false);
                chessboard.setVisible(false);
                back.setVisible(false);
                changeback.setVisible(false);
                gameController.restart();

                System.out.println("click back");
            }
        });
    }

    class task extends TimerTask{
        @Override
        public void run() {
            if (timeLeft.getWidth()==0){
                chessboard.swapColor();
            }
            if (timeLeft.getWidth()<=100&&timeLeft.getWidth()>50){
                timeLeft.setBackground(Color.CYAN);
            } else if (timeLeft.getWidth()<=50){
                timeLeft.setBackground(Color.red);
            }else {
                timeLeft.setBackground(Color.pink);
            }
            timeLeft.setSize(timeLeft.getWidth()-1,timeLeft.getHeight());
            timeLeft.repaint();
        }
    }
    //设置按钮鼠标形式
    public void Mousemotion(JButton c,double width,double height){
        c.setLocation((int) (width), (int) height);
        c.setSize(160, 60);
        c.setFont(new Font("Times New Roman", Font.BOLD, 26));
        //c.setToolTipText("悔棋，可以悔无限次");// 鼠标悬停提示
        c.setForeground(Color.MAGENTA);//给字体添加颜色
        c.setContentAreaFilled(false); //去除填充背景
        c.setBorder(null);//去除边框
        add(c);

        Container c1 = this.getContentPane();
        c.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                c.setContentAreaFilled(true);
                c.setBackground(Color.pink);
                BackgroundMusic click = new BackgroundMusic();
                new Thread(()->{
                    click.playMusic("./down.wav"); //while中的true可换成参数来控制音乐的停止播放
                }).start();
                // 鼠标进入改变按钮颜色
            }

            @Override
            public void mouseExited(MouseEvent e) {
                c.setBackground(null);
                c.setContentAreaFilled(false);
                // 鼠标离开改变按钮颜色
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                BackgroundMusic click = new BackgroundMusic();
                new Thread(()->{
                    click.playMusic("D:\\ChessDemo1\\enter.wav"); //while中的true可换成参数来控制音乐的停止播放
                }).start();
            }
        });
        c1.add(c);
    }
    public static class timeTask extends java.util.TimerTask{
        int time1=-4;
        Timer timer1=new Timer();

        @Override
        public void run() {
            time1++;
            if(time1>=gameController.getChessboard().step.size()){
                timer1.cancel();
            }else if(time1>=0){
                gameController.getChessboard().move(gameController.getChessboard().getChessComponents()[gameController.getChessboard().step.get(time1).charAt(0)-48][gameController.getChessboard().step.get(time1).charAt(1)-48],gameController.getChessboard().getChessComponents()[gameController.getChessboard().step.get(time1).charAt(2)-48][gameController.getChessboard().step.get(time1).charAt(3)-48]);
            }

        }
    }

//        public void run()
//        {
//            for(int i=0;i<progressValues.length;i++)
//            {
//                try
//                {
//                    Thread.sleep(3000);
//                }
//                catch(InterruptedException e)
//                {
//                    e.printStackTrace();
//                }
//                //设置进度条的值
//                progressBar.setValue(progressValues[i]);
//            }
//            progressBar.setIndeterminate(false);
//            progressBar.setString("升级完成！");
//            button.setEnabled(true);
//        }
//    }

    /**
     * JPanel contentPane=new JPanel();
     *         contentPane.setLocation(HEIGHT, HEIGHT / 10-10);
     *         contentPane.setSize(100,10);
     *         contentPane.setBackground(null);
     *         final JProgressBar progressBar=new JProgressBar();
     *         progressBar.setStringPainted(true);
     *         progressBar.setLocation(HEIGHT, HEIGHT / 10-10);
     *         new Thread(){
     *             public void run(){
     *                 for(int i=100;i>=0;i--){
     *                     try{
     *                         Thread.sleep(100);
     *                     }catch(InterruptedException e){
     *                         e.printStackTrace();
     *                     }
     *                     progressBar.setValue(i);
     *                 }
     *                 progressBar.setString("升级完成！");
     *             }
     *         }.start();
     *         contentPane.add(progressBar);
     *         add(contentPane);
     */
}

//for(int i=0;i<8;i++){
//            for(int j=0;j<8;j++){
//                JButton Button=new JButton("123456789");
//                Button.setFont(new Font("Times New Roman" , Font.CENTER_BASELINE, 50));
//                //Button.setVisible(true);
//                Button.setSize(76,76);
//                Button.setContentAreaFilled(false);
//                //Button.setBounds(null);
//                Button.setLocation(76*i+HEIGTH / 4, (int) (HEIGTH / 14.3)+76*j);
//                //Button.setVisible(true);
//                add(Button);
//
//                Container c2 = this.getContentPane();
//                Button.addMouseListener(new MouseAdapter() {
//                    @Override
//                    public void mouseEntered(MouseEvent e) {
//                        Button.setContentAreaFilled(true);
//                        Button.setBackground(Color.pink);
//                        BackgroundMusic click = new BackgroundMusic();
//                        new Thread(()->{
//                            click.playMusic("./down.wav"); //while中的true可换成参数来控制音乐的停止播放
//                        }).start();
//                        // 鼠标进入改变按钮颜色
//                    }
//
//                    @Override
//                    public void mouseExited(MouseEvent e) {
//                        Button.setBackground(null);
//                        Button.setContentAreaFilled(false);
//                        // 鼠标离开改变按钮颜色
//                    }
//
//                    @Override
//                    public void mouseClicked(MouseEvent e) {
//                        BackgroundMusic click = new BackgroundMusic();
//                        new Thread(()->{
//                            click.playMusic("D:\\ChessDemo1\\enter.wav"); //while中的true可换成参数来控制音乐的停止播放
//                        }).start();
//                    }
//                });
//                c2.add(Button);
//            }
//        }