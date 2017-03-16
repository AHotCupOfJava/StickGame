import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by clarissa_briasco on 3/16/17.
 */
public class Stick {

    private Point begin;
    private int height;
    private Color color;

    public Stick(int x, int y){
        begin = new Point(x, y);
        height = 0;
        color = new Color(45, 22, 24);
    }

    /**
     * @param g2       thing you use to draw
     * @param tiltType 0 if horizontal, 1 if vertical, 2 if tilted
     * @param degrees  degrees to tilt if tilted; 0 if tilt is 0 or 1
     */
    public void draw(Graphics2D g2, int tiltType, double degrees) {

        g2.setColor(color);
        g2.setStroke(new BasicStroke(5));

        if (tiltType == 0) { //vertical
            g2.drawLine(begin.x, begin.y, begin.x, begin.y - height);
        } else if (tiltType == 1) { //horizontal
            g2.drawLine(begin.x, begin.y, begin.x + height, begin.y);
        } else if (tiltType == 2) { //tilted
            double angle = Math.atan(degrees);
            AffineTransform orig = g2.getTransform();
            g2.rotate(angle, begin.x, begin.y);
            g2.drawLine(begin.x, begin.y, begin.x + height, begin.y);
            g2.setTransform(orig);
        }

    }

    public void grow(){
        height += 3;
    }

}