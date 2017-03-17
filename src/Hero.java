import java.awt.*;

/**
 * Created by sarah_zhang on 3/15/17.
 */
public class Hero {

    private int x, y;

    public Hero(int xx, int yy){
        x = xx;
        y = yy;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, 30, 50);
    }

}
