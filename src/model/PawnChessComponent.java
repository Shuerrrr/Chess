package model;
import view.ChessboardPoint;
import controller.ClickController;
import view.Chessboard;
import view.Record;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
public class PawnChessComponent extends ChessComponent {
    private static Image PAWN_WHITE;
    private static Image PAWN_BLACK;
    private Image pawnImage;
    public void loadResource() throws IOException{
        if(PAWN_WHITE==null){
            PAWN_WHITE=ImageIO.read(new File("./images/pawn-white.png"));
        }
        if(PAWN_BLACK==null){
            PAWN_BLACK=ImageIO.read(new File("./images/pawn-black.png"));
        }
    }
    private void initiatePawnImage(ChessColor color){
        try{
            loadResource();
            if(color==ChessColor.WHITE)
                pawnImage=PAWN_WHITE;
            else if (color==ChessColor.BLACK)
                pawnImage=PAWN_BLACK;
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size){
        super(chessboardPoint, location, color, listener, size);
        initiatePawnImage(color);
    }
    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if(chessColor.getColor()==ChessColor.BLACK.getColor()){
            if(source.getX()==1) {
                if (destination.getY() == source.getY() && (destination.getX() - source.getX() > 0 && destination.getX() - source.getX() <= 2)) {
                    int col = source.getY();
                    if(!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent))
                        return false;
                    for (int row = Math.min(source.getX(), destination.getX()) + 1;
                         row < Math.max(source.getX(), destination.getX()); row++) {
                        if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {//如果这个点是空点，则false，不是空点则是true
                            return false;
                        }
                    }
                }
                else if(destination.getX()- source.getX()==1&&Math.abs(destination.getY()- source.getY())==1){
                    int row=destination.getX();
                    int col=destination.getY();
                    if(chessComponents[row][col] instanceof EmptySlotComponent)
                        return false;
                }
                else
                    return false;
            }
            else{
                //过路兵判断
                if(this.chessboardPoint.getX()==4&&(chessComponents[this.chessboardPoint.getX()][this.chessboardPoint.getY()-1] instanceof PawnChessComponent||chessComponents[this.chessboardPoint.getX()][this.chessboardPoint.getY()+1] instanceof PawnChessComponent)){
                    if (Chessboard.huiqilist.getLast().getCh2().getX()==this.chessboardPoint.getX()&&(Chessboard.huiqilist.getLast().getCh2().getY()==this.chessboardPoint.getY()-1||Chessboard.huiqilist.getLast().getCh2().getY()==this.chessboardPoint.getY()+1)
                            &&Chessboard.huiqilist.getLast().getCh1().getX()==6){
                        if(destination.getX()==5&&destination.getY()==Chessboard.huiqilist.getLast().getCh1().getY())
                        {//chessComponents[4][destination.getY()]=new EmptySlotComponent(new ChessboardPoint(4,destination.getY()),new Point(4,destination.getY()),clickController,76);
                            // repaint();
                            return true;}
                    }
                }
                if (destination.getY() == source.getY() && destination.getX() - source.getX()==1) {
                    int col = source.getY();
                    if(!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent))
                        return false;
                    for (int row = Math.min(source.getX(), destination.getX()) + 1;
                         row < Math.max(source.getX(), destination.getX()); row++) {
                        if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {//如果这个点是空点，则false，不是空点则是true
                            return false;
                        }
                    }
                }
                else if(destination.getX()- source.getX()==1&&Math.abs(destination.getY()- source.getY())==1){
                    int row=destination.getX();
                    int col=destination.getY();
                    if(chessComponents[row][col] instanceof EmptySlotComponent)
                        return false;
                }
                else
                    return false;
            }

            return true;
        }
        else{//白棋
            if(source.getX()==6) {
                if (destination.getY() == source.getY() && (destination.getX() - source.getX() < 0 && destination.getX() - source.getX() >= -2)) {
                    int col = source.getY();
                    if(!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent))
                        return false;
                    for (int row = Math.min(source.getX(), destination.getX()) + 1;
                         row < Math.max(source.getX(), destination.getX()); row++) {
                        if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {//如果这个点是空点，则false，不是空点则是true
                            return false;
                        }
                    }
                }
                else if(destination.getX()- source.getX()==-1&&Math.abs(destination.getY()- source.getY())==1){
                    int row=destination.getX();
                    int col=destination.getY();
                    if(chessComponents[row][col] instanceof EmptySlotComponent)
                        return false;
                }
                else
                    return false;
            }
            else{
                //过路兵判断
                if(this.chessboardPoint.getX()==3&&(chessComponents[this.chessboardPoint.getX()][this.chessboardPoint.getY()-1] instanceof PawnChessComponent||chessComponents[this.chessboardPoint.getX()][this.chessboardPoint.getY()+1] instanceof PawnChessComponent)){
                    if (Chessboard.huiqilist.getLast().getCh2().getX()==this.chessboardPoint.getX()&&(Chessboard.huiqilist.getLast().getCh2().getY()==this.chessboardPoint.getY()-1||Chessboard.huiqilist.getLast().getCh2().getY()==this.chessboardPoint.getY()+1)
                            &&Chessboard.huiqilist.getLast().getCh1().getX()==1){
                        if(destination.getX()==2&&destination.getY()==Chessboard.huiqilist.getLast().getCh1().getY()){
                            //                        chessComponents[3][destination.getY()]=new EmptySlotComponent(new ChessboardPoint(3,destination.getY()),new Point(3,destination.getY()),clickController,76);
                            return true;}
                    }
                }
                if (destination.getY() == source.getY() && destination.getX() - source.getX()==-1) {
                    int col = source.getY();
                    if(!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent))
                        return false;
                    for (int row = Math.min(source.getX(), destination.getX()) + 1;
                         row < Math.max(source.getX(), destination.getX()); row++) {
                        if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {//如果这个点是空点，则false，不是空点则是true
                            return false;
                        }
                    }
                }
                else if(destination.getX()- source.getX()==-1&&Math.abs(destination.getY()- source.getY())==1){
                    int row=destination.getX();
                    int col=destination.getY();
                    if(chessComponents[row][col] instanceof EmptySlotComponent)
                        return false;
                }
                else
                    return false;
            }

            return true;
        }
    }
//    @Override
//    public Image getImage() {
//        return pawnImage;
//    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.fillOval(0, 0, getWidth() , getHeight());
        }
        if(isPathed()){
            g.setColor(Color.GREEN);
            g.fillOval(0,0,getWidth(),getHeight());
            //g.drawOval(0,0,getWidth(),getHeight());
            // this.setpath(false);
        }
        if(isEntered()){
            g.setColor(Color.pink);
            g.fillOval(0,0,getWidth(),getHeight());
            //g.drawOval(0,0,getWidth(),getHeight());
            this.setEntered(false);
        }
        g.drawImage(pawnImage, 0, 0, getWidth() , getHeight(), this);
    }
}
