import java.awt.*;

/**
 * Created by sarah_zhang on 3/16/17.
 */
public class Pillar {

    private int x, y, w, h;

    public Pillar(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(x, y, w, h);

        g2.setColor(Color.RED);
        g2.fillRect(x+w/2-6, y, 12, 10);
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

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }
}
