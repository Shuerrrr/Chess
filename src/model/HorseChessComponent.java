package model;
import sun.net.www.content.image.gif;
import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
public class HorseChessComponent extends ChessComponent{
    private static Image HORSE_WHITE;
    private static Image HORSE_BLACK;
    private Image horseImage;
    public void loadResource() throws IOException{
        if(HORSE_WHITE==null){
            HORSE_WHITE=ImageIO.read(new File("./images/knight-white.png"));
        }
        if(HORSE_BLACK==null){
            HORSE_BLACK=ImageIO.read(new File("./images/knight-black.png"));
        }
    }
    private void initiateHorseImage(ChessColor color){
        try{
            loadResource();
            if (color==ChessColor.WHITE)
                horseImage=HORSE_WHITE;
            else if(color==ChessColor.BLACK)
                horseImage=HORSE_BLACK;
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public HorseChessComponent(ChessboardPoint chessboardPoint,Point location, ChessColor color, ClickController listener, int size){
        super(chessboardPoint,location,color,listener,size);
        initiateHorseImage(color);
    }
    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination){
        ChessboardPoint sourse=getChessboardPoint();
        if((destination.getX()- sourse.getX()==1||destination.getX()- sourse.getX()==-1)&&(destination.getY()-sourse.getY()==2||destination.getY()-sourse.getY()==-2));
        else if((destination.getX()- sourse.getX()==2||destination.getX()- sourse.getX()==-2)&&(destination.getY()-sourse.getY()==1||destination.getY()-sourse.getY()==-1));
        else
            return false;
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        if(isSelected()){
            g.setColor(Color.RED);
            g.fillOval(0,0,getWidth(),getHeight());
//            Image images = new ImageIcon("./gif.jpg.gif").getImage();
//            g.drawImage(images,0,0,getWidth(),getHeight(), (ImageObserver) Color.ORANGE);
        }
        if(isPathed()){
            g.setColor(Color.GREEN);
            g.fillOval(0,0,getWidth(),getHeight());
            //g.drawOval(0,0,getWidth(),getHeight());
            this.setEntered(false);
            //this.setpath(false);
        }
        if(isEntered()){
            g.setColor(Color.pink);
            g.fillOval(0,0,getWidth(),getHeight());
            //g.drawOval(0,0,getWidth(),getHeight());
            this.setEntered(false);
        }
        g.drawImage(horseImage,0,0,getWidth(),getHeight(),this);
    }
}
