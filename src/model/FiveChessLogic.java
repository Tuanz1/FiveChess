package model;

import javafx.geometry.Pos;
import utils.ChessPiece;
import utils.ChessPieceType;
import utils.GameStatus;
import utils.Position;

import java.util.Stack;

/**
 * 五子棋服务器程序
 * 接受来自两个客户端的信息
 * 进行游戏逻辑的判定
 * @author Tuanzi
 */
public class FiveChessLogic {
    /**
     * 棋盘大小
     */
    private int chessboardSize = 20;
    /**
     * 五子棋棋盘映射数组
     */
    private ChessPieceType chessboardMap[][]  = new ChessPieceType[chessboardSize][chessboardSize];
    /**
     * 历史记录队列
     */
    private Stack<ChessPiece> history = new Stack<>();

    private GameStatus gameStatus = GameStatus.BLACK_TURN;

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    /**
     * 下棋
     * @param p 坐标
     * @return 是否可以下棋
     */
    public void play(Position p){
        // 落子

        ChessPiece chessPiece = new ChessPiece(null, p);
        switch (gameStatus){
            case BLACK_TURN:
                chessPiece.setType(ChessPieceType.BLACK);
                setChessPiece(p, ChessPieceType.BLACK);
                break;
            case WHITE_TURN:
                chessPiece.setType(ChessPieceType.WHITE);
                setChessPiece(p, ChessPieceType.WHITE);
                break;
        }
        // 记录信息
        history.push(chessPiece);
        check(p);
        swithcPlayer();
    }
    /**
     * 悔棋
     */
    public Position undo(){
        if (history.peek() != null){
            swithcPlayer();
            Position last = history.pop().getPosition();
            setChessPiece(last, null);
            return last;
        }
        return null;
    }

    /**
     * 填充棋盘
     * @param p 棋子位置
     * @param type 填充类型（可以是null，表示空）
     */
    private void setChessPiece(Position p, ChessPieceType type){
        int x = p.getX();
        int y = p.getY();
        chessboardMap[x][y] = type;
    }
    /**
     * 棋盘该位置是否为空
     * @param p 位置
     * @return 是否为空
     */
    public boolean isChessboardEmpty(Position p){
        if(chessboardMap[p.getX()][p.getY()] == null){
            return true;
        }
        return false;
    }

    public boolean isGameOver(){
        switch (gameStatus){
            case PLAYER1_WIN:
            case PLAYER2_WIN:
            case DRAW:
                return true;
        }
        return false;
    }
    /**
     * 回合交替
     */
    private void swithcPlayer(){
        switch (gameStatus){
            case BLACK_TURN:
                this.gameStatus= GameStatus.WHITE_TURN;
                break;
            case WHITE_TURN:
                this.gameStatus = GameStatus.BLACK_TURN;
                break;
        }
    }

    private void check(Position p){
        int top, left, topLeft, topRight;
        top = count(p, 0, 1) + count(p, 0, -1);
        left = count(p, 1, 0) + count(p , -1, 0);
        topLeft = count(p, -1, 1) + count(p, 1, -1);
        topRight = count(p , 1, 1) + count(p, -1, -1);
        if(top == 4 || left == 4 || topLeft == 4 || topRight == 4){
            switch (gameStatus){
                case BLACK_TURN:
                    gameStatus = GameStatus.PLAYER1_WIN;
                    System.out.println("玩家一获胜");
                    break;
                case WHITE_TURN:
                    gameStatus = GameStatus.PLAYER2_WIN;
                    System.out.println("玩家二获胜");
                    break;
            }
        }
    }


    private int count(Position p, int kX, int kY){
        int count = 0;
        int x = p.getX();
        int y = p.getY();
        for(int i = 0; i < 4; i++){
            x = x + kX;
            y = y + kY;
            if( x < 0 || y < 0 || x > chessboardSize -1 || y > chessboardSize
                    -1)
                return count;
            if(chessboardMap[x][y] == chessboardMap[p.getX()][p.getY()])
                count++;
            else
                return count;
        }
        return count;
    }

}
