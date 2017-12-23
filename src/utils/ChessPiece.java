package utils;

/**
 * 记录游戏棋子信息
 */
public class ChessPiece {
    private ChessPieceType type;
    private Position position;

    public ChessPiece(ChessPieceType type, Position position) {
        this.type = type;
        this.position = position;
    }

    public ChessPieceType getType() {
        return type;
    }

    public void setType(ChessPieceType type) {
        this.type = type;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
