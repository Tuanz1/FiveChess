package controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import utils.ChesspieceType;
import utils.Position;


public class FiveChessBoardPaneController {
    /**
     * 五子棋棋盘画布
     */
    @FXML
    Canvas canvas;
    @FXML
    public void initialize(){

    }

    /**
     * 绘制五子棋棋盘
     */
    public void drawChessBoard(){

    }

    /**
     * 绘制五子棋棋子
     * @param p 需要绘制的棋子坐标
     * @param type 需要绘制的棋子的类型
     */
    public void drawChesspiece(Position p, ChesspieceType type){

    }

    /**
     * 清除棋盘上的五子棋(悔棋 )
     * @param p 需要清楚的五子棋坐标
     */
    public void removeChesspiece(Position p){

    }


    public void MouseClickHandler(MouseEvent e){

    }
}
