package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BishopChessComponent extends ChessComponent {
    private static Image BISHOP_WHITE;
    private static Image BISHOP_BLACK;

    private Image bishopImage;

    @Override
    public void loadResource() throws IOException {
        if (BISHOP_WHITE == null) {
            BISHOP_WHITE = ImageIO.read(new File("./images/bishop-white.png"));
        }

        if (BISHOP_BLACK == null) {
            BISHOP_BLACK = ImageIO.read(new File("./images/bishop-black.png"));
        }
    }


    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                bishopImage = BISHOP_WHITE;
            } else if (color == ChessColor.BLACK) {
                bishopImage = BISHOP_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener,int size){
        super(chessboardPoint,location,color,listener,size);
        initiateBishopImage(color);
    }


    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source=getChessboardPoint();
        if(source==destination){
            return false;
        }
        else if (Math.abs(source.getX()-destination.getX())==Math.abs(source.getY()-destination.getY())){
            int row=Math.min(source.getX(),destination.getX()),col=Math.min(source.getY(),destination.getY());
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
                for (int rowroad = row+1; rowroad < destination.getX(); rowroad++) {
                    if (!(chessComponents[rowroad][source.getY() - rowroad + row] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            }
        }
        else {
            return false;
        }
        return true;
    }

    //                for (int rowelse = Math.min(source.getX(),destination.getX())+1;
//                     rowelse < Math.max(source.getX(), destination.getX())+1; rowelse++) {
//                    if (!(chessComponents[rowelse][col+Math.abs(row-rowelse)] instanceof EmptySlotComponent)){//colelse（纵）等于列之差的绝对值加原纵值
//                        return false;
//                    }
//                }
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
            //g.drawOval(0,0,getWidth(),getHeight());
            this.setpath(false);
        }
        if(isEntered()){
            g.setColor(Color.pink);
            g.fillOval(0,0,getWidth(),getHeight());
           // g.drawOval(0,0,getWidth(),getHeight());
            this.setEntered(false);
        }
        g.drawImage(bishopImage,0,0,getWidth(),getHeight(),this);
    }
}
