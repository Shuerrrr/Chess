package model;
import view.Chessboard;
import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
public class KingChessComponent extends ChessComponent{
    private static Image KING_WHITE;
    private static Image KING_BLACK;
    private Image kingImage;
    public void loadResource() throws IOException {
        if (KING_WHITE == null) {
            KING_WHITE = ImageIO.read(new File("./images/king-white.png"));
        }

        if (KING_BLACK == null) {
            KING_BLACK = ImageIO.read(new File("./images/king-black.png"));
        }
    }
    private void initiateKingImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                kingImage = KING_WHITE;
            } else if (color == ChessColor.BLACK) {
                kingImage = KING_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateKingImage(color);
    }
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination){
        ChessboardPoint source=getChessboardPoint();
        if(Math.abs(destination.getY()-source.getY())<=1&&Math.abs(destination.getX()-source.getX())<=1&&!(destination.getX()== source.getX()&&destination.getY()== source.getY())){
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    if(chessComponents[i][j].chessColor.getColor()!=chessColor.getColor()&&!(chessComponents[i][j] instanceof PawnChessComponent)&&!(chessComponents[i][j] instanceof KingChessComponent)&&chessComponents[i][j].canMoveTo(chessComponents,destination)==true){
                        return false;
                    }
                    else if(chessComponents[i][j].chessColor.getColor()!=chessColor.getColor()&&chessComponents[i][j] instanceof PawnChessComponent){
                        if(chessComponents[i][j].chessColor.getColor()==ChessColor.BLACK.getColor()&&destination.getX()-new ChessboardPoint(i,j).getX()==1&&Math.abs(destination.getY()-new ChessboardPoint(i,j).getY())==1){
                            return false;
                        }
                        if(chessComponents[i][j].chessColor.getColor()==ChessColor.WHITE.getColor()&&destination.getX()-new ChessboardPoint(i,j).getX()==-1&&Math.abs(destination.getY()-new ChessboardPoint(i,j).getY())==1){
                            return false;
                        }
                    }
                    else if(chessComponents[i][j].chessColor.getColor()!=chessColor.getColor()&&chessComponents[i][j] instanceof KingChessComponent){
                        if(Math.abs(destination.getY()-new ChessboardPoint(i,j).getY())<=1&&Math.abs(destination.getX()-new ChessboardPoint(i,j).getX())<=1&&!(destination.getX()== new ChessboardPoint(i,j).getX()&&destination.getY()== new ChessboardPoint(i,j).getY())){
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        else if(destination.getY()+1<8&&destination.getY()-source.getY()==2&&chessComponents[source.getX()][destination.getY()+1] instanceof RookChessComponent&&destination.getX()== source.getX()){
            for(int i=0;i< Chessboard.huiqilist.size();i++){
                if((Chessboard.huiqilist.get(i).chess1 instanceof KingChessComponent&&Chessboard.huiqilist.get(i).chess1.chessColor==this.chessColor)||(Chessboard.huiqilist.get(i).chess1 instanceof RookChessComponent&&Chessboard.huiqilist.get(i).chess1.chessColor==this.chessColor))
                    return false;
            }
            if(!(chessComponents[source.getX()][source.getY()+1] instanceof EmptySlotComponent)||!(chessComponents[source.getX()][source.getY()+2] instanceof EmptySlotComponent))
                return false;
            else return true;
        }
        else if(destination.getY()-2>=0&&destination.getY()-source.getY()==-2&&chessComponents[source.getX()][destination.getY()-2] instanceof RookChessComponent&&destination.getX()== source.getX()){
            for(int i=0;i< Chessboard.huiqilist.size();i++){
                if((Chessboard.huiqilist.get(i).chess1 instanceof KingChessComponent&&Chessboard.huiqilist.get(i).chess1.chessColor==this.chessColor)||(Chessboard.huiqilist.get(i).chess1 instanceof RookChessComponent&&Chessboard.huiqilist.get(i).chess1.chessColor==this.chessColor))
                    return false;
            }
            if(!(chessComponents[source.getX()][source.getY()-1] instanceof EmptySlotComponent)||!(chessComponents[source.getX()][source.getY()-2] instanceof EmptySlotComponent)||!(chessComponents[source.getX()][source.getY()-3] instanceof EmptySlotComponent))
                return false;
            else return true;
        }
        else return false;
    }
//    @Override
//    public Image getImage() {
//        return kingImage;
//    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(kingImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }

    }
}
