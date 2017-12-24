package utils;

/**
 * 坐标
 * @author Tuanzi
 */
public class Position {
    /**
     * 横坐标
     */
    private int x;
    /**
     * 纵坐标
     */
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int toInt(){
        return this.x * 100 + this.y;
    }

    public static Position getPostion(int m){
        int x = m / 100;
        int y = m % 100;
        return new Position(x, y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
