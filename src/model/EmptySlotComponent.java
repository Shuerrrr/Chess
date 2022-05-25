package model;

import view.ChessboardPoint;
import controller.ClickController;

import java.awt.*;
import java.io.IOException;

/**
 * 这个类表示棋盘上的空位置
 */
public class EmptySlotComponent extends ChessComponent {

    public EmptySlotComponent(ChessboardPoint chessboardPoint, Point location, ClickController listener, int size) {
        super(chessboardPoint, location, ChessColor.NONE, listener, size);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        return false;
    }

    @Override
    public void loadResource() throws IOException {
        //No resource!
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        if(isPathed()){
            g.setColor(Color.GREEN);
            g.fillOval(30,30,getWidth()-60,getHeight()-60);
            //this.setpath(false);
        }
        if(isEntered()){
            g.setColor(Color.ORANGE);
            g.fillRect(0,0,getWidth(),getHeight());
            //g.drawOval(0,0,getWidth(),getHeight());
            this.setEntered(false);
        }
    }
}
