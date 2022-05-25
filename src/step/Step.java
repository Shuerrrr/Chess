package step;

import model.ChessPiece;

import java.awt.*;

public class Step {
    private int sid;
    private int rowIndex;
    private int columnIndex;
    private Color color;
    private static int stepCnt=1;
    private int COLOR;
    public Step(int rowIndex, int columnIndex, int COLOR)
    {
        this.rowIndex=rowIndex;
        this.columnIndex=columnIndex;
        this.sid=stepCnt++;
        this.COLOR=COLOR;
    }
    public int getSid(){return sid;}
    public Color getColor(){return color;}
    public void setColor(Color color){this.color=color;}
    public int getRowIndex(){return rowIndex;}
    public void setRowIndex(int rowIndex){this.rowIndex=rowIndex;}
    public int getColumnIndex(){return columnIndex;}
    public void setColumnIndex(int columnIndex){this.columnIndex=columnIndex;}
    public static int getStepCnt(){return stepCnt;}
    public String toString(){return ""+rowIndex+columnIndex+COLOR;}
}
