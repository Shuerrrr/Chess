package controller;

import model.*;
import music.BackgroundMusic;
import step.CheatMode;
import step.Step;
import view.ChessGameFrame;
import view.Chessboard;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {
    private  Chessboard chessboard;
    public ChessGameFrame chessGameFrame;
    private Chessboard gamePanel;
    private CheatMode cheatMode = CheatMode.NoneCheat;
    private ArrayList<Step> stepList = new ArrayList<>();


    public void setChessGameFrame(ChessGameFrame chessGameFrame) {
        this.chessGameFrame = chessGameFrame;
    }

    public Chessboard getChessboard() {
        return chessboard;
    }

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public List<String> loadGameFromFile(String path) {
        try {
            if(!path.endsWith("txt")){
                JOptionPane.showConfirmDialog(null, "错误4：文件格式错误", "错误", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                return null;
            }
            List<String> chessData = Files.readAllLines(Paths.get(path));

            if(chessData.size()!=9){
                if(chessData.get(chessData.size()-1).length()!=1){
                    JOptionPane.showConfirmDialog(null, "错误1：棋盘非8x8", "错误", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    return null;
                }
                else{
                    JOptionPane.showConfirmDialog(null, "错误3：缺少下一步行棋方", "错误", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    return null;
                }
            }for(int i=0;i<8;i++) {
                int result = 0;
                if (chessData.get(i).length() != 8) {
                    result = JOptionPane.showConfirmDialog(null, "错误1：棋盘非8x8", "错误", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    return null;
                }
            }
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    if(chessData.get(i).charAt(j)!='p'&&chessData.get(i).charAt(j) != 'r'&&chessData.get(i).charAt(j) != 'h'
                            &&chessData.get(i).charAt(j) != 'b'&&chessData.get(i).charAt(j) != 'q'&&chessData.get(i).charAt(j) != 'k'
                            &&chessData.get(i).charAt(j) != 'P'&&chessData.get(i).charAt(j) != 'R'&&chessData.get(i).charAt(j) != 'H'
                            &&chessData.get(i).charAt(j) != 'B'&&chessData.get(i).charAt(j) != 'Q'&&chessData.get(i).charAt(j) != 'K'
                            &&chessData.get(i).charAt(j) != '_'){
                        JOptionPane.showConfirmDialog(null, "错误2：棋子并非六种之一，棋子并非黑白棋子", "错误", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    return null;}
                }
            }
            chessboard.loadGame(chessData);
            return chessData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void restart() {
        chessboard.setCurrentColor(ChessColor.WHITE);
        ChessGameFrame.statusLabel.setText("WHITE‘s turn");
        stepList.clear();
        Chessboard.huiqilist.clear();
        chessboard.initChessboard();
        chessboard.repaint();
        ChessGameFrame.timeLeft.setSize(150,20);
        ChessGameFrame.timeLeft.repaint();
        getChessboard().step.clear();//同时在保存游戏中保存回放存档
    }


    public void readFileData(String fileName, int stopTime) {
        if(fileName == null) return;
        if(!fileName.endsWith("txt"))
        {
            System.out.println("error! 文件格式不是txt");
            return;
        }
        restart();
        MyTimer timer = new MyTimer(fileName);
        if(timer.checkFinish())return;
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    if(true)
                    {
                        System.out.println("error! Invalid Move!");
                        restart(); timer.cancel(); return;
                    }
                    timer.nextPiece();
                    if(timer.checkFinish()) timer.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, stopTime);
        cheatMode = CheatMode.NoneCheat;
        //loading = false;
    }

    public class MyTimer extends Timer {
        private int num = 0, count=0, length=0;
        char[] buf = new char[10010];
        private int[] row = new int[110], col = new int[110], COLOR = new int[110];
        MyTimer(String fileName) {
            try {
                FileReader input = new FileReader(fileName);
                length = input.read(buf);
                if (length == -1) {
                    System.out.println("error! 文件为空");
                    return;
                }
                if (length % 3 != 0) {
                    System.out.println("error! 数据长度必须为3的倍数");
                    return;
                }
                for (int i = 0; i < length; i++) {
                    if (!Character.isDigit(buf[i])) {
                        System.out.println("error! 数据必须全为数字");
                        return;
                    }
                }
                for (int i = 0; i < length / 3; i++) {
                    row[num] = buf[i * 3] - '0';
                    col[num] = buf[i * 3 + 1] - '0';
                    COLOR[num++] = buf[i * 3 + 2] - '0';
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        boolean checkFinish(){return count == num;}
        int getRow(){return row[count];}
        int getCol(){return col[count];}
        int getColor(){return COLOR[count];}
        void nextPiece(){count++;}
    }

    public List<String> save(){
        FileOutputStream fop=null;
        File file;
        char[][] chr=new char[8][8];
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(chessboard.getChessComponents()[i][j].getChessColor()== ChessColor.BLACK){
                    if(chessboard.getChessComponents()[i][j] instanceof PawnChessComponent)
                        chr[i][j]='p';
                    else if (chessboard.getChessComponents()[i][j] instanceof RookChessComponent)
                        chr[i][j]='r';
                    else if (chessboard.getChessComponents()[i][j] instanceof HorseChessComponent)
                        chr[i][j]='h';
                    else if (chessboard.getChessComponents()[i][j] instanceof BishopChessComponent)
                        chr[i][j]='b';
                    else if (chessboard.getChessComponents()[i][j] instanceof QueenChessComponent)
                        chr[i][j]='q';
                    else if (chessboard.getChessComponents()[i][j] instanceof KingChessComponent)
                        chr[i][j]='k';
                }
                else if(chessboard.getChessComponents()[i][j].getChessColor()==ChessColor.WHITE){
                    if(chessboard.getChessComponents()[i][j] instanceof PawnChessComponent)
                        chr[i][j]='P';
                    else if (chessboard.getChessComponents()[i][j] instanceof RookChessComponent)
                        chr[i][j]='R';
                    else if (chessboard.getChessComponents()[i][j] instanceof HorseChessComponent)
                        chr[i][j]='H';
                    else if (chessboard.getChessComponents()[i][j] instanceof BishopChessComponent)
                        chr[i][j]='B';
                    else if (chessboard.getChessComponents()[i][j] instanceof QueenChessComponent)
                        chr[i][j]='Q';
                    else if (chessboard.getChessComponents()[i][j] instanceof KingChessComponent)
                        chr[i][j]='K';
                }
                else
                    chr[i][j]='_';
            }
        }

        try {
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            file=new File("./"+df.format(System.currentTimeMillis()).toString()+".txt");
            if(!file.exists())
                file.createNewFile();
            FileWriter fw=new FileWriter(file);
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    fw.write(chr[i][j]);
                }
                fw.write("\r\n");
            }
            if(chessboard.getCurrentColor()==ChessColor.WHITE)
                fw.write("U");
            else
                fw.write("D");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
