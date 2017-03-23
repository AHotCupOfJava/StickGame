import java.awt.*;
import java.awt.geom.QuadCurve2D;

/**
 * Created by clarissa_briasco on 3/23/17.
 */
public class Background {

    private int w, h;

    public Background(int width, int height){

        w = width;
        h = height;

    }

    public void draw(Graphics2D g2){
        g2.setColor(new Color(17, 196, 255));
        g2.fillRect(0, 0, w, h);

        g2.setColor(new Color(0, 198, 66));
        g2.fillRect(0, 325, w, 470);

        QuadCurve2D hill1 = new QuadCurve2D.Double(0, 325, 110, 270, 200, 325);
        g2.fill(hill1);
        QuadCurve2D hill3 = new QuadCurve2D.Double(325, 325, 425, 300, w, 375);
        g2.fill(hill3);

        g2.setColor(new Color(17, 196, 255));
        QuadCurve2D hill2 = new QuadCurve2D.Double(200, 325, 260, 370, 325, 325);
        g2.fill(hill2);

//        g2.fillArc(100, 200, 50, 50, 180, -180);
        g2.setColor(new Color(0, 237, 80));
        g2.fillRect(0, 450, w, 370);
    }

}
