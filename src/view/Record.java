package view;

import model.ChessComponent;

public class Record {
    public ChessComponent chess1;
    public ChessComponent chess2;
    public ChessboardPoint ch1;
    public ChessboardPoint ch2;
    public Record(ChessComponent chess1,ChessComponent chess2,ChessboardPoint ch1,ChessboardPoint ch2){
        this.chess1=chess1;
        this.chess2=chess2;
        this.ch1=ch1;
        this.ch2=ch2;
    }

    public void setChess1(ChessComponent chess1) {
        this.chess1 = chess1;
    }

    public void setChess2(ChessComponent chess2) {
        this.chess2 = chess2;
    }

    public void setCh1(ChessboardPoint ch1) {
        this.ch1 = ch1;
    }

    public void setCh2(ChessboardPoint ch2) {
        this.ch2 = ch2;
    }

    public ChessComponent getChess1() {
        return chess1;
    }

    public ChessComponent getChess2() {
        return chess2;
    }

    public ChessboardPoint getCh1() {
        return ch1;
    }

    public ChessboardPoint getCh2() {
        return ch2;
    }
}
