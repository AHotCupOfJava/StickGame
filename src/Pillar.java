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
        g2.fillRect(x+350, y, w, h);

    }

}
