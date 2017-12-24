package server;

import model.FiveChessLogic;
import utils.Position;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 五子棋游戏会话
 */
class FiveGameSession implements Runnable{
    private Socket player1;
    private Socket player2;
    FiveChessLogic gameLogic = new FiveChessLogic();

    private DataInputStream player1Input;
    private DataInputStream player2Input;
    private DataOutputStream player1Output;
    private DataOutputStream player2Output;

    public FiveGameSession(Socket player1, Socket player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void run() {
        try{
            player1Input = new DataInputStream(player1.getInputStream());
            player2Input = new DataInputStream(player2.getInputStream());
            player1Output = new DataOutputStream(player1.getOutputStream());
            player2Output = new DataOutputStream(player2.getOutputStream());
            // 通知客户端游戏开始
            notifyClient(GAME_INFO.GAME_START);

            while (true){
                // 获得玩家一的输入，假设是玩家一先开始的
                Position p  = Position.getPostion(player1Input.readInt());
                // 这个p应该不用检测是否会出错吧
                gameLogic.play(p);
                if (isGameEnd()){
                    break;
                }
                // 客户端2同步写入
                sendMove(player2Output, p);

                p = Position.getPostion(player2Input.readInt());
                gameLogic.play(p);
                if (isGameEnd()){
                    break;
                }
                sendMove(player1Output, p);
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
   
    private boolean isGameEnd(){
        switch (gameLogic.getGameStatus()){
            case PLAYER1_WIN:
                notifyClient(GAME_INFO.PLAYER1_WIN);
                return true;
            case PLAYER2_WIN:
                notifyClient(GAME_INFO.PLAYER2_WIN);
                return true;
            case DRAW:
                notifyClient(GAME_INFO.DRAW);
                return true;
            default: return false;
        }
    }
    /**
     * 通知用户游戏信息
     * @param info 游戏信息
     */
    private void notifyClient(GAME_INFO info){
        try {
            System.out.println(info.getDescription());
            player1Output.writeInt(info.getCode());
            player2Output.writeInt(info.getCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMove(DataOutputStream output, Position p){
        int i = p.getX() * 100 + p.getY();
        try {
            output.writeInt(i);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
