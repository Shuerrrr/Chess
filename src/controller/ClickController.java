package controller;
import model.*;
import music.BackgroundMusic;
import view.ChessGameFrame;
import view.Chessboard;
import view.ChessboardPoint;
import view.Record;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class ClickController {
    private int k=0;//长将
    private int t=0;//长将
    private int count=0;//重复
    public final Chessboard chessboard;
    protected ChessComponent first;
    public ChessComponent getFirst(){return first;}
    private ChessComponent checkcom;
    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }
    public void entered(ChessComponent chessComponent){
        chessComponent.setEntered(true);
        chessboard.repaint();
    }
    public void onClick(ChessComponent chessComponent) throws InterruptedException {
        ImageIcon icon=new ImageIcon("./1.jpg");
        BackgroundMusic click = new BackgroundMusic();
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;
                //path
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (handleSecond(chessboard.getChessComponents()[i][j])) {
                            System.out.printf("path chess [%d,%d]\n", chessboard.getChessComponents()[i][j].getChessboardPoint().getX(), chessboard.getChessComponents()[i][j].getChessboardPoint().getY());
                            chessboard.getChessComponents()[i][j].setpath(true);
                        }
                    }
                }
                chessboard.repaint();
                // first.repaint();
            }
        } else {
            new Thread(()->{
                click.playMusic("./click.wav"); //while中的true可换成参数来控制音乐的停止播放
            }).start();
            if (first == chessComponent) { // 再次点击取消选取
                chessComponent.setSelected(false);

                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        chessboard.getChessComponents()[i][j].setpath(false);
                    }
                }
                chessboard.repaint();
            } else if (handleSecond(chessComponent)) {
                //repaint in swap chess method.
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        chessboard.getChessComponents()[i][j].setpath(false);
                    }
                }
                if (kingcheck != null) {//走后若还将军则悔棋
                    if (!(first instanceof KingChessComponent && first.getChessColor() == kingcheck.getChessColor())) {
                        ChessboardPoint temp=new ChessboardPoint(kingcheck.getChessboardPoint().getX(),kingcheck.getChessboardPoint().getY());
                        chessboard.swapChessComponents(first, chessComponent);
                        chessboard.swapColor();
                        first.setSelected(false);
                        first = null;

                        if (ischecking2(chessboard.getChessComponents())) {
                            chessboard.huiqi();
                            kingcheck=chessboard.getChessComponents()[temp.getX()][temp.getY()];
                        }
                    } else {
                        ChessboardPoint temp=new ChessboardPoint(kingcheck.getChessboardPoint().getX(),kingcheck.getChessboardPoint().getY());
                        chessboard.swapChessComponents(first, chessComponent);
                        chessboard.swapColor();
                        first.setSelected(false);
                        //        first = null;
                        if (ischecking3(chessboard.getChessComponents(), first)){
                            chessboard.huiqi();
                            kingcheck=chessboard.getChessComponents()[temp.getX()][temp.getY()];
                        }

                        first = null;
                    }
                    //兵底判断
                    for(int i=0;i<8;i++){
                        if(chessboard.getChessComponents()[0][i] instanceof PawnChessComponent){
                            int ans=JOptionPane.showOptionDialog(null,"请选择棋子","请选择棋子",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,new String[]{"后","车","相","马"},"后");
                            if(ans==0){
                                chessboard.initQueenOnBoard(0,i,ChessColor.WHITE);
                            }
                            else if(ans==1)
                                chessboard.initRookOnBoard(0,i,ChessColor.WHITE);
                            else if(ans==2)
                                chessboard.initBishopOnBoard(0,i,ChessColor.WHITE);
                            else if(ans==3)
                                chessboard.initHorseOnBoard(0,i,ChessColor.WHITE);
                        }
                        else if(chessboard.getChessComponents()[0][i] instanceof PawnChessComponent){
                            int ans=JOptionPane.showOptionDialog(null,"请选择棋子","请选择棋子",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,new String[]{"后","车","相","马"},"后");
                            if(ans==0){
                                chessboard.initQueenOnBoard(0,i,ChessColor.BLACK);
                            }
                            else if(ans==1)
                                chessboard.initRookOnBoard(0,i,ChessColor.BLACK);
                            else if(ans==2)
                                chessboard.initBishopOnBoard(0,i,ChessColor.BLACK);
                            else if(ans==3)
                                chessboard.initHorseOnBoard(0,i,ChessColor.BLACK);
                        }
                    }
                    chessboard.repaint();
                } else {//若没自己将军那就随意走
                    chessboard.swapChessComponents(first, chessComponent);
                    chessboard.swapColor();
                    first.setSelected(false);
                    first = null;
                    t++;
                    if(t==2)
                        k=0;
                    //兵底判断
                    for(int i=0;i<8;i++){
                        if(chessboard.getChessComponents()[0][i] instanceof PawnChessComponent){
                            int ans=JOptionPane.showOptionDialog(null,"请选择棋子","请选择棋子",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,new String[]{"后","车","相","马"},"后");
                            if(ans==0){
                                chessboard.initQueenOnBoard(0,i,ChessColor.WHITE);
                            }
                            else if(ans==1)
                                chessboard.initRookOnBoard(0,i,ChessColor.WHITE);
                            else if(ans==2)
                                chessboard.initBishopOnBoard(0,i,ChessColor.WHITE);
                            else if(ans==3)
                                chessboard.initHorseOnBoard(0,i,ChessColor.WHITE);
                        }
                        else if(chessboard.getChessComponents()[7][i] instanceof PawnChessComponent){
                            int ans=JOptionPane.showOptionDialog(null,"请选择要变身棋子","兵底升变",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,new String[]{"后","车","相","马"},"后");
                            if(ans==0){
                                chessboard.initQueenOnBoard(7,i,ChessColor.BLACK);
                            }
                            else if(ans==1)
                                chessboard.initRookOnBoard(7,i,ChessColor.BLACK);
                            else if(ans==2)
                                chessboard.initBishopOnBoard(7,i,ChessColor.BLACK);
                            else if(ans==3)
                                chessboard.initHorseOnBoard(7,i,ChessColor.BLACK);
                        }
                    }
                    chessboard.repaint();
                }
                if(hqchecking(chessboard.getChessComponents(), chessboard.getCurrentColor())){
                    System.out.println("无子可走和棋");//结束
                    int result = JOptionPane.showConfirmDialog(null, "无子可走和棋", "和棋",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
                    if(result == JOptionPane.OK_OPTION){
                        ChessGameFrame.gameController.restart();
                    }
                }
                //重复和棋
                if(Chessboard.huiqilist.size()>4){
                    if(Chessboard.huiqilist.getLast().getChess1().getClass()==Chessboard.huiqilist.get(Chessboard.huiqilist.size()-5).getChess1().getClass()
                            &&Chessboard.huiqilist.getLast().getCh1().getX()==Chessboard.huiqilist.get(Chessboard.huiqilist.size()-5).getCh1().getX()&&Chessboard.huiqilist.getLast().getCh1().getY()==Chessboard.huiqilist.get(Chessboard.huiqilist.size()-5).getCh1().getY()
                            &&Chessboard.huiqilist.getLast().getCh2().getX()==Chessboard.huiqilist.get(Chessboard.huiqilist.size()-5).getCh2().getX()&&Chessboard.huiqilist.getLast().getCh2().getY()==Chessboard.huiqilist.get(Chessboard.huiqilist.size()-5).getCh2().getY()){
                        count++;
                        System.out.println(count);}
                    else
                        count=0;
                    if(count==9){
                        System.out.println("重复和棋");//结束
                        int result = JOptionPane.showConfirmDialog(null, "重复和棋", "和棋",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
                        if(result == JOptionPane.OK_OPTION){
                            ChessGameFrame.gameController.restart();
                        }
                    }
                }
                if (ischecking(chessboard.getChessComponents())) {//如果走后将军对方
                    //音效动画将军

                    if (kingcheck.getChessColor() != chessboard.getCurrentColor()) {
                        ChessboardPoint temp=new ChessboardPoint(kingcheck.getChessboardPoint().getX(),kingcheck.getChessboardPoint().getY());
                        chessboard.huiqi();
                        kingcheck=chessboard.getChessComponents()[temp.getX()][temp.getY()];
                        return;
                    }//若自己造成自己将军
                    System.out.println("jiangjunjiangjunjiangjunjiangjun");
                    int result = JOptionPane.showConfirmDialog(null, "您被将军了", "将军提示",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
                    k++;
                    t=0;
                    System.out.println(k);
                    if(k==15){
                        System.out.println("长将和棋");
                        int result1 = JOptionPane.showConfirmDialog(null, "长将和棋", "和棋",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
                        if(result1 == JOptionPane.OK_OPTION){
                            ChessGameFrame.gameController.restart();
                        }
                    }//结束
                    if (overwin(chessboard.getChessComponents())) {//结束
                        if (kingcheck.getChessColor() == ChessColor.BLACK) {
                            System.out.println("WHITE WIN!");
                            int result2 = JOptionPane.showConfirmDialog(null, "白方胜利！", "胜负已分",JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
                            new Thread(()->{
                                click.playMusic("./click.wav"); //while中的true可换成参数来控制音乐的停止播放
                            }).start();
                            if(result == JOptionPane.OK_OPTION){
                                ChessGameFrame.gameController.restart();
                            }
                        }
                        else if (kingcheck.getChessColor() == ChessColor.WHITE) {
                            System.out.println("BLACK WIN!");
                            int result2 = JOptionPane.showConfirmDialog(null, "黑方胜利！", "胜负已分",JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
                            new Thread(()->{
                                click.playMusic("./click.wav"); //while中的true可换成参数来控制音乐的停止播放
                            }).start();
                            if(result == JOptionPane.OK_OPTION){
                                ChessGameFrame.gameController.restart();
                            }
                        }
                    }
                }
            }
        }
    }



    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    protected boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    public boolean handleSecond(ChessComponent chessComponent) {
        return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
    }
    //将死
    private boolean overwin(ChessComponent[][] chessComponents) throws InterruptedException {
        ChessboardPoint temp=new ChessboardPoint(kingcheck.getChessboardPoint().getX(),kingcheck.getChessboardPoint().getY());//
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                for(int x=0;x<8;x++) {
                    for(int y=0;y<8;y++){
                        if (chessComponents[i][j].getChessColor()==kingcheck.getChessColor()&&chessComponents[x][y].getChessColor()!=kingcheck.getChessColor()&&chessComponents[i][j].canMoveTo(chessComponents,chessComponents[x][y].getChessboardPoint())){
                            temp=new ChessboardPoint(kingcheck.getChessboardPoint().getX(),kingcheck.getChessboardPoint().getY());
                            chessboard.swapChessComponents(chessComponents[i][j], chessComponents[x][y]);
                            chessboard.swapColor();
                            //chessboard.repaint();
                            if(ischecking2(chessboard.getChessComponents())){

                                chessboard.huiqi();
                                kingcheck=chessboard.getChessComponents()[temp.getX()][temp.getY()];}
                            else{
                                chessboard.huiqi();
                                return false;}
                        }
                    }
                }
            }
        }
        return true;
    }
    //无棋可动和棋
    private boolean hqchecking(ChessComponent[][] chessComponents,ChessColor color){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                for(int x=0;x<8;x++) {
                    for(int y=0;y<8;y++){
                        if(chessComponents[i][j].getChessColor()==color){
                            if(chessComponents[x][y].getChessColor() != color &&
                                    chessComponents[i][j].canMoveTo(chessboard.getChessComponents(),new ChessboardPoint(x,y)))
                                return false;
                        }
                    }}}}
        return true;
    }

    //走前将军
    ChessComponent kingcheck;
    private boolean ischecking(ChessComponent[][] chessComponents){
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++){
                if(chessComponents[i][j] instanceof KingChessComponent){
                    for(int x=0;x<8;x++){
                        for(int y=0;y<8;y++){
                            if(chessComponents[x][y].getChessColor()!=chessComponents[i][j].getChessColor()){
                                if(!(chessComponents[x][y] instanceof KingChessComponent)&&!(chessComponents[x][y] instanceof PawnChessComponent)&& chessComponents[x][y].canMoveTo(chessComponents,new ChessboardPoint(i,j))){
                                    kingcheck=chessComponents[i][j];
                                    return true;
                                }
                                else if(chessComponents[x][y] instanceof PawnChessComponent&&chessComponents[i][j].getChessColor()==ChessColor.BLACK&&new ChessboardPoint(x,y).getX()-new ChessboardPoint(i,j).getX()==1&&Math.abs(new ChessboardPoint(i,j).getY()-new ChessboardPoint(x,y).getY())==1){
                                    kingcheck=chessComponents[i][j];
                                    return true;
                                }
                                else if(chessComponents[x][y] instanceof PawnChessComponent&&chessComponents[i][j].getChessColor()==ChessColor.WHITE&&new ChessboardPoint(x,y).getX()-new ChessboardPoint(i,j).getX()==-1&&Math.abs(new ChessboardPoint(i,j).getY()-new ChessboardPoint(x,y).getY())==1){
                                    kingcheck=chessComponents[i][j];
                                    return true;}
                            }
                        }
                    }
                }
            }
        }
        kingcheck=null;
        return false;
    }
    //zouh
    private boolean ischecking2(ChessComponent[][] chessComponents){
        for(int x=0;x<8;x++){
            for(int y=0;y<8;y++){
                if(chessComponents[x][y].getChessColor()!=kingcheck.getChessColor()){
                    if(!(chessComponents[x][y] instanceof KingChessComponent)&&!(chessComponents[x][y] instanceof PawnChessComponent)&& chessComponents[x][y].canMoveTo(chessComponents,kingcheck.getChessboardPoint())){
                        return true;
                    }
                    else if(chessComponents[x][y] instanceof PawnChessComponent&&kingcheck.getChessColor()==ChessColor.BLACK&&kingcheck.getChessboardPoint().getX()-new ChessboardPoint(x,y).getX()==1&&Math.abs(kingcheck.getChessboardPoint().getY()-new ChessboardPoint(x,y).getY())==1){
                        return true;
                    }
                    else if(chessComponents[x][y] instanceof PawnChessComponent&&kingcheck.getChessColor()==ChessColor.WHITE&&kingcheck.getChessboardPoint().getX()-new ChessboardPoint(x,y).getX()==-1&&Math.abs(kingcheck.getChessboardPoint().getY()-new ChessboardPoint(x,y).getY())==1){
                        return true;}

                }
            }
        }
        return false;
    }
    private boolean ischecking3(ChessComponent[][] chessComponents,ChessComponent checkcom){
        for(int x=0;x<8;x++){
            for(int y=0;y<8;y++){
                if(chessComponents[x][y].getChessColor()!=kingcheck.getChessColor()){
                    if(!(chessComponents[x][y] instanceof KingChessComponent)&&!(chessComponents[x][y] instanceof PawnChessComponent)&& chessComponents[x][y].canMoveTo(chessComponents,checkcom.getChessboardPoint())){
                        return true;
                    }
                    else if(chessComponents[x][y] instanceof PawnChessComponent&&kingcheck.getChessColor()==ChessColor.BLACK&&checkcom.getChessboardPoint().getX()-new ChessboardPoint(x,y).getX()==1&&Math.abs(checkcom.getChessboardPoint().getY()-new ChessboardPoint(x,y).getY())==1){
                        return true;
                    }
                    else if(chessComponents[x][y] instanceof PawnChessComponent&&kingcheck.getChessColor()==ChessColor.WHITE&&checkcom.getChessboardPoint().getX()-new ChessboardPoint(x,y).getX()==-1&&Math.abs(checkcom.getChessboardPoint().getY()-new ChessboardPoint(x,y).getY())==1){
                        return true;}

                }
            }
        }
        return false;
    }
}
