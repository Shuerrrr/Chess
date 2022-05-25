package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class QueenChessComponent extends ChessComponent {
    private static Image Queen_WHITE;
    private static Image Queen_BLACK;

    private Image queenImage;


    @Override
    public void loadResource() throws IOException {
        if (Queen_WHITE == null) {
            Queen_WHITE = ImageIO.read(new File("./images/queen-white.png"));
        }

        if (Queen_BLACK == null) {
            Queen_BLACK = ImageIO.read(new File("./images/queen-black.png"));
        }
    }


    private void initiateQueenImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                queenImage = Queen_WHITE;
            } else if (color == ChessColor.BLACK) {
                queenImage = Queen_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener,int size){
        super(chessboardPoint,location,color,listener,size);
        initiateQueenImage(color);
    }


    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source=getChessboardPoint();
        if(source==destination){
            return false;
        }
        else if (Math.abs(source.getX()-destination.getX())==Math.abs(source.getY()-destination.getY())){
            int row=Math.min(source.getX(),destination.getX()),col=Math.min(source.getY(),destination.getY());
            if (source.getX()!=destination.getX()&&source.getY()!=destination.getY()){
            if (source.getX() > row && source.getY() > col) {
                for (int rowroad = row+1; rowroad < source.getX(); rowroad++) {
                    if (!(chessComponents[rowroad][col+rowroad-row]instanceof EmptySlotComponent)){
                        return false;
                    }

                }
            } else if (source.getX() > row && source.getY() == col) {
                for (int rowroad = row+1; rowroad < source.getX(); rowroad++) {
                    if (!(chessComponents[rowroad][destination.getY()-rowroad+row]instanceof EmptySlotComponent)){
                        return false;
                    }
                }
            } else if (source.getX() == row && source.getY() == col) {
                for (int rowroad = row+1; rowroad < destination.getX(); rowroad++) {
                    if (!(chessComponents[rowroad][col+rowroad-row]instanceof EmptySlotComponent)){
                        return false;
                    }
                }
            } else if (source.getX() == row && source.getY() > col) {
                for (int rowroad = row + 1; rowroad < destination.getX(); rowroad++) {
                    if (!(chessComponents[rowroad][source.getY() - rowroad + row] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            }

        }
        }else if (source.getX() == destination.getX()&&source.getY()!=destination.getY()) {
        int row1 = source.getX();
        for (int col1 = Math.min(source.getY(), destination.getY()) + 1;
             col1 < Math.max(source.getY(), destination.getY()); col1++) {
            if (!(chessComponents[row1][col1] instanceof EmptySlotComponent)) {
                return false;
            }
        }
        } else if (source.getY() == destination.getY()&&source.getX()!=destination.getX()) {
            int col1 = source.getY();
            for (int row1 = Math.min(source.getX(), destination.getX()) + 1;
                 row1 < Math.max(source.getX(), destination.getX()); row1++) {
                if (!(chessComponents[row1][col1] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        }
        else {
            return false;
        }
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        if (isSelected()) {
            g.setColor(Color.RED);
            g.fillOval(0,0,getWidth(),getHeight());
        }
        if(isPathed()){
            g.setColor(Color.GREEN);
            g.fillOval(0,0,getWidth(),getHeight());
           // this.setpath(false);
        }
        if(isEntered()){
            g.setColor(Color.pink);
            g.fillOval(0,0,getWidth(),getHeight());
            //g.drawOval(0,0,getWidth(),getHeight());
            this.setEntered(false);
        }
        g.drawImage(queenImage,0,0,getWidth(),getHeight(),this);
    }
}
