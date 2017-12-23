package server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 游戏服务器程序
 */
public class GameServer {
    public static void log(String s) {
        System.out.println(s);
    }

    /**
     * 服务器游戏逻辑
     */
    public static void main(String[] args) {

        new Thread(() -> {
            try {
                // 创建服务套接字
                ServerSocket serverSocket = new ServerSocket(8000);
                log("在端口8000创建套接字");

                while (true) {
                    log("等待玩家一连接...");
                    Socket player1 = serverSocket.accept();

                    log("玩家一已经连接");
                    log("玩家一信息:IP-" + player1.getInetAddress().getHostAddress());
                    // 通知玩家一已经连接主机
                    //player1.getOutputStream().write(GAME_INFO.PLAYER1_CONNECTED.getCode());

                    log("等待玩家二连接...");
                    Socket player2 = serverSocket.accept();

                    log("玩家二已经连接");
                    log("玩家二信息:IP-" + player2.getInetAddress().getHostAddress());
                    // 通知玩家一已经连接主机
                    //player1.getOutputStream().write(GAME_INFO.PLAYER2_CONNECTED.getCode());
                    new Thread(new FiveGameSession(player1, player2)).start();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }).start();
    }
}

