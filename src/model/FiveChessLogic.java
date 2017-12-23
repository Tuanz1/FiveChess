package model;

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
    private int chessboardSize = 19;
    /**
     * 五子棋棋盘映射数组
     */
    private ChessPieceType chessboardMap[][]  = new ChessPieceType[chessboardSize][chessboardSize];
    /**
     * 历史记录队列
     */
    private Stack<ChessPiece> history = new Stack<>();
    private GameStatus gameStatus = GameStatus.CONTINUE;

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    /**
     * 下棋
     * @param p 坐标
     * @return 是否可以下棋
     */
    public boolean play(Position p){
        if (!isChessboardEmpty(p))
            return false;
        // 落子
        ChessPiece chessPiece = new ChessPiece(null, p);
        switch (gameStatus){
            case BLACK_TURN:
                chessPiece = new ChessPiece(ChessPieceType.BLACK, p);
                break;
            case WHITE_TURN:
                chessPiece = new ChessPiece(ChessPieceType.WHITE, p);
                break;
            default:break;
        }
        // 记录信息
        history.push(chessPiece);
        check(p);
        return true;
    }
    /**
     * 悔棋
     */
    private void undo(){
        ChessPiece temp = history.peek();
        if (temp != null){
            Position p = temp.getPosition();
            setChessPiece(p, null);
            swithcPlayer();
            // 发送悔棋的坐标
        }
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
    private boolean isChessboardEmpty(Position p){
        if(chessboardMap[p.getX()][p.getY()] == null){
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
            default:
                return;
        }
    }

    private boolean check(Position p){
        int top, left, topLeft, topRight;
        top = count(p, 0, 1) + count(p, 0, -1);
        left = count(p, 1, 0) + count(p , -1, 0);
        topLeft = count(p, -1, 1) + count(p, 1, -1);
        topRight = count(p , 1, 1) + count(p, -1, -1);
        if(top == 4 || left == 4 || topLeft == 4 || topRight == 4){
            switch (gameStatus){
                case BLACK_TURN: gameStatus = GameStatus.PLAYER1_WIN;
                    break;
                case WHITE_TURN: gameStatus = GameStatus.PLAYER2_WIN;
                    break;
            }
            return true;
        }
        return false;
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
