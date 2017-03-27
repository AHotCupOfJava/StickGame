import java.awt.*;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;

/**
 * Created by clarissa_briasco on 3/23/17.
 */
public class Background {

    private int w, h;
    private ArrayList<QuadCurve2D> topHills, mediumHills, bottomHills;

    public Background(int width, int height){

        w = width;
        h = height;

        topHills = new ArrayList<QuadCurve2D>();
        mediumHills = new ArrayList<QuadCurve2D>();
        bottomHills = new ArrayList<QuadCurve2D>();

        topHills.add(new QuadCurve2D.Double(0, 325, 110, 270, 200, 325)); //hill 1
        topHills.add(new QuadCurve2D.Double(200, 325, 260, 370, 325, 325)); //hill 2
        topHills.add(new QuadCurve2D.Double(325, 325, 375, 300, 425, 325)); //hill 3
        topHills.add(new QuadCurve2D.Double(390, 300, w - 50, 400, w, 375)); //hill 4


    }

    public void draw(Graphics2D g2){
        g2.setColor(new Color(17, 196, 255));
        g2.fillRect(0, 0, w, h);

        g2.setColor(new Color(0, 198, 66));
        g2.fillRect(0, 325, w, 470);

        g2.fill(topHills.get(0));
        g2.fill(topHills.get(2));

        g2.setColor(new Color(17, 196, 255));
        g2.fill(topHills.get(1));
        g2.fill(topHills.get(3));

        g2.setColor(new Color(0, 237, 80));
        g2.fillRect(0, 450, w, 370);
    }

}
