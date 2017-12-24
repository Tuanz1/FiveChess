package controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.FiveChessLogic;
import utils.Position;


public class FiveChessBoardUIController {
    /**
     * 五子棋棋盘画布
     */
    @FXML
    Canvas chessboard;

    @FXML
    Button btnBack;
    @FXML
    Button btnRestart;
    @FXML
    TextArea logMessage;
    
    FiveChessLogic gameLogic;
    private Color colorChessboard = Color.valueOf("#FBE39B");
    private Color colorLine = Color.valueOf("#884B09");
    private Color colorMark = Color.valueOf("#000000");
    private GraphicsContext gc;
    //定义棋盘大小 20 * 20, 此处计算有差错，需要+1
    private int chessboardSize = 20;
    private double gapX, gapY;
    private double paddingBroad = 20;
    private double chessPieceSize;
    private String[] markX = new String[]{" ", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",  " "};
    private String[] markY = new String[]{" ","1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", " "};

    @FXML
    public void initialize(){
        //绘制五子棋棋盘
        drawChessboard();
        gameLogic = new FiveChessLogic();
    }

    /**
     * 绘制五子棋棋盘
     */
    private void drawChessboard() {
        // 绘制背景色
        gc = chessboard.getGraphicsContext2D();
        gc.setFill(colorChessboard);
        gc.fillRect(0, 0, chessboard.getWidth(), chessboard.getHeight());
        // 绘制线条
        gapX = (chessboard.getWidth() - paddingBroad * 2) / chessboardSize;
        gapY = (chessboard.getHeight() - paddingBroad * 2) / chessboardSize;
        chessPieceSize = gapX * 0.8;
        gc.setStroke(colorLine);
        for(int i = 0; i <= chessboardSize; i++){
            gc.strokeLine(i * gapX + paddingBroad, paddingBroad, i * gapX + paddingBroad, chessboard.getHeight() - paddingBroad);
            gc.strokeLine(paddingBroad, i * gapY + paddingBroad, chessboard.getWidth() - paddingBroad, i * gapY + paddingBroad);
        }

        gc.setFill(colorMark);
        gc.setFont(Font.font(paddingBroad / 2));
        for (int i = 0; i <= chessboardSize; i++) {
            gc.fillText(markX[i], i * gapX + paddingBroad - 5, chessboard.getHeight() - 5);
            gc.fillText(markY[i], 5, gapY * i + paddingBroad + 5);
        }
    }
    /**
     * 在棋盘中绘制一个棋子
     * @param p 棋子坐标
     */
    private void addChessPiece(Position p){
        double x = p.getX() * gapX + paddingBroad;
        double y = p.getY() * gapY + paddingBroad;
        switch (gameLogic.getGameStatus()){
            case BLACK_TURN:
                gc.setFill(Color.BLACK);
                gc.fillOval(x -chessPieceSize / 2, y - chessPieceSize / 2, chessPieceSize, chessPieceSize);
                break;
            case WHITE_TURN:
                gc.setFill(Color.WHITE);
                gc.fillOval(x - chessPieceSize / 2, y - chessPieceSize / 2, chessPieceSize, chessPieceSize );
                break;
        }
    }

    /**
     * 清除棋盘上的五子棋(悔棋 )
     * @param p 需要清楚的五子棋坐标
     */
    private void removeChessPiece(Position p){
        double x = p.getX() * gapX + paddingBroad;
        double y = p.getY() * gapY + paddingBroad;
        gc.setFill(colorChessboard);
        gc.fillRect(x - gapX / 2, y - gapY / 2, gapX , gapY);
        gc.setFill(colorLine);
        gc.strokeLine(x - gapX / 2, y, x + gapX / 2, y);
        gc.strokeLine(x, y - gapY / 2, x, y + gapY / 2);
    }

    @FXML
    protected void handleChessboardClicked(MouseEvent e) {
        Position p = adjustPostion(e.getX(), e.getY());
        if (!(p.getX() <= 0 || p.getY() <= 0 || p.getX() >= 20 || p.getY() >= 20)) {
            if (gameLogic.isChessboardEmpty(p) && !gameLogic.isGameOver()) {
                addChessPiece(p);
                gameLogic.play(p);
            }
        }
    }

    /**
     * 得到校准（换算）后的坐标
     * @param x 鼠标的横坐标
     * @param y 鼠标总纵坐标
     * @return 校正坐标
     */
    private Position adjustPostion(double x, double y){
        int tempX = (int)((x - paddingBroad + gapX / 2) / gapX);
        int tempY = (int)((y - paddingBroad + gapY / 2) / gapY);
        return new Position(tempX, tempY);
    }

    @FXML
    private void handleBtnBackClicked(){
        Position last = gameLogic.undo();
        if(last != null){
            removeChessPiece(last);
            switch (gameLogic.getGameStatus()){
                case BLACK_TURN: logMessage.appendText("[黑方悔棋]" + markX[last.getX()] + ", " + last.getY() + "\n");
                    break;
                case WHITE_TURN: logMessage.appendText("[白方悔棋]" + markX[last.getX()] + ", " + last.getY() + "\n");
                    break;
            }
        }
    }
    @FXML
    private void handleBtnRestartClicked(){
        initialize();
    }

}
