import java.awt.*;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;

/**
 * Created by clarissa_briasco on 3/23/17.
 */
public class Hills implements Background{

    private int w, h;
    private ArrayList<QuadCurve2D> topHills, mediumHills, bottomHills;
    private Color ltBlue1, ltBlue2, green1, green2, green3, green4;
    private boolean day;

    public Hills(int width, int height){

        w = width;
        h = height;

        topHills = new ArrayList<QuadCurve2D>();
        mediumHills = new ArrayList<QuadCurve2D>();
        bottomHills = new ArrayList<QuadCurve2D>();

        topHills.add(new QuadCurve2D.Double(0, 325, 110, 270, 200, 325)); //hill 1
        topHills.add(new QuadCurve2D.Double(200, 325, 260, 370, 325, 325)); //hill 2
        topHills.add(new QuadCurve2D.Double(325, 325, 375, 300, 425, 325)); //hill 3

        mediumHills.add(new QuadCurve2D.Double(0, 400, 40, 460, 100, 420)); //hill 1
        mediumHills.add(new QuadCurve2D.Double(100, 420, 160, 350, 250, 420)); //hill 2
        mediumHills.add(new QuadCurve2D.Double(250, 420, 290, 470, 370, 440)); //hill 3
        mediumHills.add(new QuadCurve2D.Double(370, 440, 450, 390, 520, 410)); //hill 4


        int random = (int)(Math.random()*2);
        if(random == 0){
            setDay(true);

            ltBlue1 = new Color(77, 207, 255);
            ltBlue2 = new Color(0, 117, 255);

            green1 = new Color(0, 198, 66);
            green2 = new Color(0, 135, 45);

            green3 = new Color(0, 237, 80);
            green4 = new Color(0, 136, 46);
        }
        if(random == 1){
            setDay(false);
            ltBlue1 = new Color(0, 0, 0);
            ltBlue2 = new Color(0, 13, 146);

            green1 = new Color(0, 117, 35);
            green2 = new Color(0, 81, 21);

            green3 = new Color(0, 157, 54);
            green4 = new Color(0, 95, 33);
        }

    }

    public void draw(Graphics2D g2){
        g2.setPaint(new GradientPaint(w/2, h/2, ltBlue2, w/2, 0, ltBlue1));
        g2.fillRect(0, 0, w, h);

        g2.setPaint(new GradientPaint(w/2, h - 200, green2, w/2, h/2, green1));
        g2.fillRect(0, 325, w, 470);

        g2.fill(topHills.get(0));
        g2.fill(topHills.get(2));

        g2.setPaint(new GradientPaint(w/2, h/2, ltBlue2, w/2, 0, ltBlue1));
        g2.fill(topHills.get(1));

        int[] xes = new int[3];
        xes[0] = 420;
        xes[1] = w;
        xes[2] = w;
        int[] yes = new int[3];
        yes[0] = 320;
        yes[1] = 320;
        yes[2] = 375;

        g2.fillPolygon(xes, yes, 3);

        g2.setPaint(new GradientPaint(w/2, h, green4, w/2, h/2 + 200, green3));
        g2.fillRect(0, 420, w, 370);
        g2.draw(mediumHills.get(0));
        g2.fill(mediumHills.get(1));
        g2.draw(mediumHills.get(2));
        g2.fill(mediumHills.get(3));

        int[] x1 = new int[3];
        x1[0] = 0;
        x1[1] = 0;
        x1[2] = 15;
        int[] y1 = new int[3];
        y1[0] = 400;
        y1[1] = 420;
        y1[2] = 420;

        int[] x2 = new int[3];
        x2[0] = 247;
        x2[1] = 365;
        x2[2] = 409;
        int[] y2 = new int[3];
        y2[0] = 419;
        y2[1] = 441;
        y2[2] = 418;

        int[] x3 = new int[3];
        x3[0] = 465;
        x3[1] = 520;
        x3[2] = 520;
        int[] y3 = new int[3];
        y3[0] = 418;
        y3[1] = 410;
        y3[2] = 424;

        g2.fillPolygon(x1, y1, 3);
        g2.fillPolygon(x3, y3, 3);

        g2.setPaint(new GradientPaint(w/2, h - 200, green2, w/2, h/2, green1));
        g2.fill(mediumHills.get(0));
        g2.fill(mediumHills.get(2));

        g2.fillPolygon(x2, y2, 3);

    }

    public boolean isDay() {
        return day;
    }

    public void setDay(boolean day) {
        this.day = day;
    }

}
