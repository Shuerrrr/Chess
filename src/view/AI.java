//package AI;
//
//import model.ChessPiece;
//import step.Step;
//import view.Chessboard;
//
//public class AI {
//    Chessboard chessBoard = new Chessboard();
//    int color;
//    public AI(int[][] chessBoard, int color)
//    {
//        for(int i=0; i<8; i++)
//            for(int j=0; j<8; j++)
//                this.chessBoard.chessboard[i][j]=chessBoard[i][j];
//            this.color = color;
//    }
//    public Step getBestStep()
//    {
//        Step step = new Step(0, 0, 0); int bestValue=-999999;
//        for(int i=0; i<8; i++)
//        {
//            for(int j=0; j<8; j++)
//            {
//                if(chessBoard.canClickGrid(i, j, color))
//                {
//                    Chessboard temp = new Chessboard(chessBoard.chessboard);
//                    temp.putChess(i, j, color);
//                    int v=-9999999;
//                    if(temp.gameOver())v=temp.getValue(color);
//                    else if(temp.canMove(color*-1)) v=getValue(temp, color*-1, 1, -9999999, 99999999);
//                    else if (temp.canMove(color)) v=getValue(temp, color, 1, -99999999, 99999999);
//                    if(v > bestValue)
//                    {
//                        bestValue = v;
//                        step = new Step(i, j, color);
//                    }
//                }
//            }
//        }
//        return step;
//    }
//
//
//    public int getValue(ChessBoard board, int color, int depth, int alpha, int beta)
//    {
//        if(depth == 5)
//        {
//            return board.getValue(this.color);
//        }
//        int bestValue=-999999; if(color != this.color)bestValue*=-1;
//        for(int i=0; i<8; i++)
//        {
//            for(int j=0; j<8; j++)
//            {
//                if(board.canClickGrid(i, j, color))
//                {
//                    ChessBoard temp = new ChessBoard(board.chessBoard);
//                    temp.putChess(i, j, color);
//                    int v=0;
//                    if(temp.gameOver())v=temp.getValue(this.color);
//                    else if(temp.canMove(color*-1)) v=getValue(temp, color*-1, depth+1, alpha, beta);
//                    else if (temp.canMove(color)) v=getValue(temp, color, depth+1, alpha, beta);
//                    if(color!=this.color && v < bestValue)
//                    {
//                        bestValue = v;
//                        beta = Math.min(beta, v);
//                    }
//                    else if(color == this.color && v > bestValue)
//                    {
//                        bestValue = v;
//                        alpha = Math.max(alpha, v);
//                    }
//                    if(alpha > beta)return bestValue;
//                }
//            }
//        }
//        return bestValue;
//    }
//}
