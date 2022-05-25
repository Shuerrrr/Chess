package view;


import controller.GameController;
import model.*;
import controller.ClickController;
import music.BackgroundMusic;
import view.ChessGameFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;
    public static LinkedList<Record> huiqilist=new LinkedList<>();
    public   ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    private GameController gameController;
    public ArrayList<String> step=new ArrayList<>();

    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);
        //initiateEmptyChessboard();
        initChessboard();
        // FIXME: Initialize chessboard for testing only.
//        initRookOnBoard(0, 0, ChessColor.BLACK);
//        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
//        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
//        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
//        initHorseOnBoard(0,1,ChessColor.BLACK);
//        initHorseOnBoard(0,CHESSBOARD_SIZE-2,ChessColor.BLACK);
//        initHorseOnBoard(CHESSBOARD_SIZE-1,1,ChessColor.WHITE);
//        initHorseOnBoard(CHESSBOARD_SIZE-1,CHESSBOARD_SIZE-2,ChessColor.WHITE);
//        for(int i=0;i<8;i++){
//            initPawnOnBoard(1,i,ChessColor.BLACK);
//            initPawnOnBoard(6,i,ChessColor.WHITE);
//        }
//        initKingOnBoard(0,4,ChessColor.BLACK);
//        initKingOnBoard(7,4,ChessColor.WHITE);
//        //象
//        initBishopOnBoard(0,5,ChessColor.BLACK);
//        initBishopOnBoard(0,2,ChessColor.BLACK);
//        initBishopOnBoard(7,2,ChessColor.WHITE);
//        initBishopOnBoard(7,5,ChessColor.WHITE);
//
//        //皇后
//        initQueenOnBoard(0,3,ChessColor.BLACK);
//        initQueenOnBoard(7,3,ChessColor.WHITE);
    }

    public void initChessboard(){
        initiateEmptyChessboard();
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
        initHorseOnBoard(0,1,ChessColor.BLACK);
        initHorseOnBoard(0,CHESSBOARD_SIZE-2,ChessColor.BLACK);
        initHorseOnBoard(CHESSBOARD_SIZE-1,1,ChessColor.WHITE);
        initHorseOnBoard(CHESSBOARD_SIZE-1,CHESSBOARD_SIZE-2,ChessColor.WHITE);
        for(int i=0;i<8;i++){
            initPawnOnBoard(1,i,ChessColor.BLACK);
            initPawnOnBoard(6,i,ChessColor.WHITE);
        }
        initKingOnBoard(0,4,ChessColor.BLACK);
        initKingOnBoard(7,4,ChessColor.WHITE);
        //象
        initBishopOnBoard(0,5,ChessColor.BLACK);
        initBishopOnBoard(0,2,ChessColor.BLACK);
        initBishopOnBoard(7,2,ChessColor.WHITE);
        initBishopOnBoard(7,5,ChessColor.WHITE);

        //皇后
        initQueenOnBoard(0,3,ChessColor.BLACK);
        initQueenOnBoard(7,3,ChessColor.WHITE);

    }
    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(ChessColor currentColor) {
        this.currentColor = currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) throws InterruptedException {
        if(chess1 instanceof PawnChessComponent&& Math.abs(chess2.getChessboardPoint().getY()-chess1.getChessboardPoint().getY())==1&&chess2 instanceof EmptySlotComponent){
            Record record=new Record(chess1,chessComponents[chess1.getChessboardPoint().getX()][chess2.getChessboardPoint().getY()],chess1.getChessboardPoint(), chess2.getChessboardPoint());
            huiqilist.add(record);
            initEmptyOnBoard(chess1.getChessboardPoint().getX(),chess2.getChessboardPoint().getY());
        }
        else if(chess1 instanceof KingChessComponent&&Math.abs(chess2.getChessboardPoint().getY()-chess1.getChessboardPoint().getY())>1){
            if(chess2.getChessboardPoint().getY()-chess1.getChessboardPoint().getY()==-2){
                Record record = new Record(chess1, chess2, chess1.getChessboardPoint(), chess2.getChessboardPoint());                huiqilist.add(record);
                initRookOnBoard(chess1.getChessboardPoint().getX(),chess1.getChessboardPoint().getY()-1,chess1.getChessColor());
                initEmptyOnBoard(chess1.getChessboardPoint().getX(),0);
            }
            if(chess2.getChessboardPoint().getY()-chess1.getChessboardPoint().getY()==2){
                Record record = new Record(chess1, chess2, chess1.getChessboardPoint(), chess2.getChessboardPoint());                huiqilist.add(record);
                initRookOnBoard(chess1.getChessboardPoint().getX(),chess1.getChessboardPoint().getY()+1,chess1.getChessColor());
                initEmptyOnBoard(chess1.getChessboardPoint().getX(),7);
            }
        }
        else {
            Record record = new Record(chess1, chess2, chess1.getChessboardPoint(), chess2.getChessboardPoint());
            huiqilist.add(record);}
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        //       donghua(chess1,chess2.getChessboardPoint());
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;
        //chess1.repaint();
        //chess2.repaint();
        step.add(String.format("%d%d%d%d",row2,col2,row1,col1));
    }
//    public void donghua(ChessComponent chess,ChessboardPoint destination) throws InterruptedException {
//       // JLabel img=new JLabel();
//        ImageIcon icon=new ImageIcon(chess.getImage());
//        img.setIcon(icon);
//        img.setBounds((destination.getX()-chess.getChessboardPoint().getX())*0/50+chess.getChessboardPoint().getX(),(destination.getY()-chess.getChessboardPoint().getY())*0/50+chess.getChessboardPoint().getY(),getWidth(),getHeight());
//        add(img);
//        img.setVisible(true);
//        for(int i=0;i<50;i++){
//            img.setBounds((destination.getX()-chess.getChessboardPoint().getX())*i/50+chess.getChessboardPoint().getX(),(destination.getY()-chess.getChessboardPoint().getY())*i/50+chess.getChessboardPoint().getY(),getWidth(),getHeight());
//            Thread.sleep(10);
//        }
//    //    img.setVisible(false);
//    }


    public void swap2ChessComponents(ChessComponent chess1, ChessComponent chess2) {
        //Record record=new Record(chess1,chess2,chess1.getChessboardPoint(),chess2.getChessboardPoint());
        //huiqilist.add(record);
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        //chess1.repaint();
        //chess2.repaint();
    }
//!!
    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public void swapColor() {
        ChessGameFrame.timeLeft.setSize(150,20);
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        if (currentColor == ChessColor.BLACK) {
            ChessGameFrame.statusLabel.setText("BLACK's turn");
        }
        else{
            ChessGameFrame.statusLabel.setText("WHITE's turn");
        }
    }

    //rook
    public void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    //horse
    public void initHorseOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new HorseChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initEmptyOnBoard(int row, int col) {
        ChessComponent chessComponent = new EmptySlotComponent(new ChessboardPoint(row, col), calculatePoint(row, col), clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void initchess(ChessComponent chess, ChessboardPoint chessboardPoint, ChessColor color) {
        if (chess instanceof BishopChessComponent) {
            initBishopOnBoard(chessboardPoint.getX(), chessboardPoint.getY(), color);
        }
        if (chess instanceof HorseChessComponent)
            initHorseOnBoard(chessboardPoint.getX(), chessboardPoint.getY(), color);
        if (chess instanceof KingChessComponent)
            initKingOnBoard(chessboardPoint.getX(), chessboardPoint.getY(), color);
        if (chess instanceof PawnChessComponent)
            initPawnOnBoard(chessboardPoint.getX(), chessboardPoint.getY(), color);
        if (chess instanceof QueenChessComponent)
            initQueenOnBoard(chessboardPoint.getX(), chessboardPoint.getY(), color);
        if (chess instanceof RookChessComponent)
            initRookOnBoard(chessboardPoint.getX(), chessboardPoint.getY(), color);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public void huiqi() {
        Record record;
        if (huiqilist.size() != 0)
            record = huiqilist.pollLast();
        else
            return;
        //
//        if (record.getChess2() instanceof EmptySlotComponent) {
//            initEmptyOnBoard(record.getCh2().getX(), record.ch2.getY());
//            initchess(record.getChess1(), record.getCh1(), record.chess1.getChessColor());
        if(record.chess1 instanceof KingChessComponent&&record.getCh2().getY()-record.getCh1().getY()==2){
            initchess(record.getChess1(), record.getCh1(), record.getChess1().getChessColor());
            //record.chess1 = chessComponents[record.ch1.getX()][record.ch2.getY()];
            initEmptyOnBoard(record.getCh2().getX(),record.getCh2().getY());

            swap2ChessComponents(chessComponents[record.getCh1().getX()][record.getCh1().getY()+1],chessComponents[record.getCh2().getX()][record.getCh2().getY()+1]);
        }
        else if(record.chess1 instanceof KingChessComponent&&record.getCh2().getY()-record.getCh1().getY()==-2){
            initchess(record.getChess1(), record.getCh1(), record.getChess1().getChessColor());
            //record.chess1 = chessComponents[record.ch1.getX()][record.ch2.getY()];
            initEmptyOnBoard(record.getCh2().getX(),record.getCh2().getY());
            swap2ChessComponents(chessComponents[record.getCh1().getX()][record.getCh1().getY()-1],chessComponents[record.getCh2().getX()][record.getCh2().getY()-2]);
        }
        else if (record.chess2 instanceof EmptySlotComponent||(record.chess1 instanceof PawnChessComponent&&record.getChess2().getChessboardPoint().getX()==record.getCh1().getX())){
            initchess(record.getChess1(), record.getCh1(), record.getChess1().getChessColor());
            //record.chess1 = chessComponents[record.ch1.getX()][record.ch2.getY()];
            initchess(record.getChess2(), record.chess2.getChessboardPoint(), record.chess2.getChessColor());
            initEmptyOnBoard(record.getCh2().getX(),record.getCh2().getY());
        }
        else {
            initchess(record.getChess1(), record.getCh1(), record.getChess1().getChessColor());
            //record.chess1 = chessComponents[record.ch1.getX()][record.ch2.getY()];
            initchess(record.getChess2(), record.chess2.getChessboardPoint(), record.chess2.getChessColor());
            //initEmptyOnBoard(record.getCh2().getX(),record.getCh2().getY());
            // record.chess2 = chessComponents[record.ch2.getX()][record.ch2.getY()];
        }
        swapColor();
        repaint();
    }

    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessData) {

//        int size=gameController.getChessboard().step.size();
//        if(size>0){
//            gameController.getChessboard().step.remove(gameController.getChessboard().step.get(size-1));
//        }
//        for(int i=0;i<gameController.getChessboard().step.size();i++){
//            gameController.getChessboard().move(gameController.getChessboard().getChessComponents()[gameController.getChessboard().step.get(i).charAt(0)-48][gameController.getChessboard().step.get(i).charAt(1)-48],gameController.getChessboard().getChessComponents()[gameController.getChessboard().step.get(i).charAt(2)-48][gameController.getChessboard().step.get(i).charAt(3)-48]);
//            gameController.getChessboard().swapColor();
//        }

        chessData.forEach(System.out::println);
        initiateEmptyChessboard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessData.get(i).charAt(j) == 'p')
                    initPawnOnBoard(i, j, ChessColor.BLACK);
                if (chessData.get(i).charAt(j) == 'r')
                    initRookOnBoard(i, j, ChessColor.BLACK);
                if (chessData.get(i).charAt(j) == 'h')
                    initHorseOnBoard(i, j, ChessColor.BLACK);
                if (chessData.get(i).charAt(j) == 'b')
                    initBishopOnBoard(i, j, ChessColor.BLACK);
                if (chessData.get(i).charAt(j) == 'q')
                    initQueenOnBoard(i, j, ChessColor.BLACK);
                if (chessData.get(i).charAt(j) == 'k')
                    initKingOnBoard(i, j, ChessColor.BLACK);
                if (chessData.get(i).charAt(j) == 'P')
                    initPawnOnBoard(i, j, ChessColor.WHITE);
                if (chessData.get(i).charAt(j) == 'R')
                    initRookOnBoard(i, j, ChessColor.WHITE);
                if (chessData.get(i).charAt(j) == 'H')
                    initHorseOnBoard(i, j, ChessColor.WHITE);
                if (chessData.get(i).charAt(j) == 'B')
                    initBishopOnBoard(i, j, ChessColor.WHITE);
                if (chessData.get(i).charAt(j) == 'Q')
                    initQueenOnBoard(i, j, ChessColor.WHITE);
                if (chessData.get(i).charAt(j) == 'K')
                    initKingOnBoard(i, j, ChessColor.WHITE);
            }
        }
        if (chessData.get(8).charAt(0) == 'U') {
            if (getCurrentColor() == ChessColor.WHITE)
                swapColor();
        }
        if (chessData.get(8).charAt(0) == 'D') {
            if (getCurrentColor() == ChessColor.BLACK)
                swapColor();
        }
        repaint();
    }


    public void move(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;
        swapColor();
        chess1.repaint();
        chess2.repaint();
    }


}
