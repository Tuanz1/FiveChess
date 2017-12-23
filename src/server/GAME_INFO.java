package server;

/**
 * 游戏信息
 */
public enum GAME_INFO {
    CONNECT_SERVER(0,"连接中"),
    GAME_START(10, "游戏开始"),
    PLAYER1_CONNECTED(1,"玩家一连接"),
    PLAYER2_CONNECTED(2, "玩家二连接"),
    CONTINUE(3, "继续游戏"),
    UNDO(4, "玩家悔棋"),
    PLAYER1_WIN(5, "玩家一获胜"),
    PLAYER2_WIN(6, "玩家二获胜"),
    DRAW(7, "平局"),
    RESTART(8, "新游戏");
    /**
     * 发送的游戏信息代码
     */
    private int code;
    /**
     * 游戏信息解释
     */
    private String description;

    GAME_INFO(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
