package utils;

public enum GameStatus {
    BLACK_TURN("黑方回合"),
    WHITE_TURN("白方回合"),
    PLAYER1_WIN("玩家一获胜"),
    PLAYER2_WIN("玩家二获胜"),
    DRAW("平局"),
    CONTINUE("继续游戏");
    private String description;
    GameStatus(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
}
