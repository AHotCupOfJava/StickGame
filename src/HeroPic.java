import org.w3c.dom.css.Rect;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by clarissa_briasco on 4/5/17.
 */
public class HeroPic {

    private int x, y, price;
    private BufferedImage pic;
    private int[] back;
    private boolean locked, isSkin;

    public HeroPic(String picName, int xx, int yy, Rectangle background, int cherries){
        x = xx;
        y = yy;

        back = new int[6];
        back[0] = background.x;
        back[1] = background.y;
        back[2] = background.width;
        back[3] = background.height;
        back[4] = 10;
        back[5] = 10;

        price = cherries;

        locked = true;
        isSkin = false;

        try {
            pic = ImageIO.read(new File("res/" + picName));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D g2){

        g2.setColor(Color.WHITE);
        g2.fillRoundRect(back[0], back[1], back[2], back[3], back[4], back[5]);

        g2.setStroke(new BasicStroke(3));

        g2.setColor(Color.BLACK);
        if(isSkin)
            g2.setColor(new Color(0, 204, 1));
        g2.drawRoundRect(back[0], back[1], back[2], back[3], back[4], back[5]);


        double rotationRequired = Math.toRadians(0);
        double locationX = pic.getWidth() / 2;
        double locationY = pic.getHeight() / 2;

        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        g2.drawImage(op.filter(pic, null), x, y, null);


        g2.setFont(new Font("Dialog", Font.BOLD, 17));

        if(locked){
            g2.setColor(new Color(255, 0, 0, 230));
            g2.drawString("LOCKED", back[0] + 5, back[1] + back[3]/2 + 10);
        }

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Dialog", Font.BOLD, 14));
        g2.drawString(price + " cherries", back[0] + 5, back[1] + back[3] - 10);

    }

    public void unlock(){
        locked = false;
    }

    public boolean isLocked(){
        return locked;
    }

    public void setSkin(boolean b){
        isSkin = b;
    }

    public boolean clicked(double x, double y){
        Rectangle bound = new Rectangle(back[0], back[1], back[2], back[3]);
        Point p = new Point( (int)x, (int)y );
        return bound.contains(p);
    }

    public int getPrice(){
        return price;
    }

    public BufferedImage getPic(){
        return pic;
    }



}
