import java.awt.*;
import java.awt.geom.CubicCurve2D;

/**
 * Created by sarah_zhang on 4/4/17.
 */
public class Mountains implements Background{

    private int width, height;
    private boolean day;
    private Color lightGray, darkGray, peakColor, sky1, sky2;

    public Mountains(int width, int height){
        this.width = width;
        this.height = height;

        int random = (int)(Math.random()*2);
        if(random == 0){
            day = true;
            lightGray = new Color(194, 194, 194);
            darkGray = new Color(88, 88, 88);
            peakColor = Color.WHITE;
            sky1 = new Color(91, 150, 255);
            sky2 = new Color(206, 222, 255);
        }
        else{
            day = false;
            lightGray = new Color(110, 110, 110);
            darkGray = new Color(52, 52, 52);
            peakColor = new Color(194, 194, 194);
            sky1 = new Color(33, 0, 186);
            sky2 = new Color(255, 117, 237);
        }
    }

    public void draw(Graphics2D g2){
        //sky
        g2.setPaint(new GradientPaint(260, 0, sky1, 260, 400, sky2));
        g2.fillRect(0, 0, width, 400);
        //ground
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 380, width, 400);

        //draws left mountain
        int[] midX = new int[3];
        int[] midY = new int[3];
        midX[0] = 87;
        midX[1] = 0;
        midX[2] = 190;

        midY[0] = 240;
        midY[1] = 386;
        midY[2] = 386;
        g2.setPaint(new GradientPaint(midX[0], midY[0], darkGray, midX[0], midY[1], lightGray));
        g2.fillPolygon(midX, midY, 3);

        //draws snow peak on top of left mountain
        midX[1] = 60;
        midX[2] = 120;
        midY[1] = 280;
        midY[2] = 280;
        g2.setColor(peakColor);
        g2.fillPolygon(midX, midY, 3);
        CubicCurve2D.Double peak = new CubicCurve2D.Double(midX[1]+1, midY[1], midX[1]-10, midY[1]+20, midX[1]-20, midY[1]+40, midX[1]+20, midY[1]);
        g2.fill(peak);
        CubicCurve2D.Double midPeak = new CubicCurve2D.Double(midX[1]+5, midY[1], midX[1]+20, midY[1]+35, midX[2]-20, midY[1]+35, midX[2]-5, midY[2]);
        g2.fill(midPeak);
        CubicCurve2D.Double rightPeak = new CubicCurve2D.Double(midX[1]+35, midY[1], midX[2]+20, midY[1]+40, midX[2]+12, midY[1]+20, midX[2], midY[2]);
        g2.fill(rightPeak);

        //draws right mountain
        midX[0] = 440;
        midX[1] = 340;
        midX[2] = 540;

        midY[0] = 280;
        midY[1] = 386;
        midY[2] = 386;
        g2.setPaint(new GradientPaint(midX[0], midY[0], darkGray, midX[0], midY[1], lightGray));
        g2.fillPolygon(midX, midY, 3);

        //draws snow peak on top of right mountain
        midX[1] = 400;
        midX[2] = 480;
        midY[1] = 320;
        midY[2] = 320;
        g2.setColor(peakColor);
        g2.fillPolygon(midX, midY, 3);
        peak = new CubicCurve2D.Double(midX[1]+1, midY[1], midX[1]-20, midY[1]+20, midX[1]-30, midY[1]+40, midX[1]+35, midY[1]);
        g2.fill(peak);
        midPeak = new CubicCurve2D.Double(midX[1]+5, midY[1], midX[1]+20, midY[1]+35, midX[2]-20, midY[1]+35, midX[2]-5, midY[2]);
        g2.fill(midPeak);
        rightPeak = new CubicCurve2D.Double(midX[1]+35, midY[1], midX[2]+30, midY[1]+40, midX[2]+20, midY[1]+20, midX[2], midY[2]);
        g2.fill(rightPeak);

        //draws middle mountain
        midX[0] = 247;
        midX[1] = 139;
        midX[2] = 362;

        midY[0] = 150;
        midY[1] = 386;
        midY[2] = 386;
        g2.setPaint(new GradientPaint(midX[0], midY[0], darkGray, midX[0], midY[1], lightGray));
        g2.fillPolygon(midX, midY, 3);

        //draws snow peak on top of middle mountain
        midX[1] = 220;
        midX[2] = 276;
        midY[1] = 207;
        midY[2] = 207;
        g2.setColor(peakColor);
        g2.fillPolygon(midX, midY, 3);
        peak = new CubicCurve2D.Double(midX[1]+1, midY[1], midX[1]-10, midY[1]+20, midX[1]-20, midY[1]+40, midX[1]+20, midY[1]);
        g2.fill(peak);
        midPeak = new CubicCurve2D.Double(midX[1]+5, midY[1], midX[1]+20, midY[1]+35, midX[2]-20, midY[1]+35, midX[2]-5, midY[2]);
        g2.fill(midPeak);
        rightPeak = new CubicCurve2D.Double(midX[1]+35, midY[1], midX[2]+20, midY[1]+40, midX[2]+12, midY[1]+20, midX[2], midY[2]);
        g2.fill(rightPeak);
    }

    public boolean isDay(){
        return day;
    }

    public void setDay(boolean day){
        this.day = day;
    }


}
