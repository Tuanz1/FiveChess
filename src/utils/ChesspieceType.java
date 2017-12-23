package utils;

/**
 * 定义棋子类型的枚举变量
 */
public enum ChesspieceType {
    BLACK("黒棋"),
    WHITE("白棋");
    String description;
    ChesspieceType(String description){
        this.description = description;
    }
}
