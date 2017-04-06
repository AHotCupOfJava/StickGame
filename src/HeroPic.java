import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by clarissa_briasco on 4/5/17.
 */
public class HeroPic {

    private int x, y;
    private BufferedImage pic;

    public HeroPic(String picName, int xx, int yy){
        x = xx;
        y = yy;

        try {
            pic = ImageIO.read(new File("res/" + picName));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D g2){

        double rotationRequired = Math.toRadians(0);
        double locationX = pic.getWidth() / 2;
        double locationY = pic.getHeight() / 2;

        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        g2.drawImage(op.filter(pic, null), x, y, null);

    }

}
