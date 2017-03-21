import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by sarah_zhang on 3/15/17.
 */
public class Hero {

    private static int nextID = 1;
    private Point loc; //top left corner of this Sprite. Note loc.x and loc.y are the easy way to access the point.
    private int dir, picOrientation; //dir is the current direction in degrees.  See the constants below.
    private BufferedImage pic; //put the file in the res folder.
    private int speed; //Number of pixels moved each frame.
    private int id;
    public static final int NORTH = 90, SOUTH = 270, WEST = 180, EAST = 0;

    public Hero(int x, int y, int direction){

        loc = new Point(x, y);
        dir = direction;
        setPic("blank.png", NORTH);
        speed = 5;

        id = nextID;
        nextID++;
    }

    public void draw(Graphics2D g2){
        g2.setColor(Color.BLACK);
        g2.fillRect(loc.x, loc.y, getPic().getWidth(), getPic().getHeight());
    }

    /**
     * Changes the image file that this Sprite uses to draw.
     *
     * @param fileName    the case-sensitive file name
     * @param orientation the direction that the image file is facing
     */
    public void setPic(String fileName, int orientation) {
        try {
            pic = ImageIO.read(new File("res/" + fileName));
            picOrientation = orientation;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes the direction the Sprite is facing by the given angle.
     *
     * @param delta change in angle measured in degrees
     */
    public void rotateBy(int delta) {
        setDir(dir + delta);
    }

    /**
     * Changes the direction the Sprite is facing to the given angle.
     *
     * @param newDir the new direction measured in degrees
     */
    public void setDir(int newDir) {
        dir = newDir;
    }

    /**
     * Returns a Rectangle object that surrounds this Sprite's pic.
     * Useful for hit detection.
     *
     * @return the bounding Rectangle.
     */
    public Rectangle getBoundingRectangle() {
        return new Rectangle(loc.x, loc.y, pic.getWidth(), pic.getHeight());
    }

    /**
     * Returns the location of this Sprite.
     *
     * @return A point object.  Use p.x and p.y or p.getX() and p.getY()
     */
    public Point getLoc() {
        return loc;
    }

    /**
     * Changes the location of this Sprite.
     *
     * @param loc
     */
    public void setLoc(Point loc) {
        this.loc = loc;
    }

    /**
     * @return the direction the Sprite is facing.  See the constants for reference.
     */
    public int getDir() {
        return dir;
    }

    public BufferedImage getPic() {
        return pic;
    }

    public void setPic(BufferedImage pic) {
        this.pic = pic;
    }

    /**
     * Returns true if this Sprite is facing East, not true EAST, but EAST at all.
     *
     * @return Returns true if this Sprite is facing East, not true EAST, but EAST at all.
     */
    public boolean facingEast() {
        return dir % 360 < 90 || dir % 360 > 270;
    }

    /**
     * @return Returns true if this Sprite is facing NORTH, not true NORTH, but NORTH at all.
     */
    public boolean facingNorth() {
        return dir % 360 > 0 && dir % 360 < 180;
    }

    /**
     * @return Returns true if this Sprite is facing WEST, not true WEST, but WEST at all.
     */
    public boolean facingWest() {
        return dir % 360 > 90 && dir % 360 < 270;
    }

    /**
     * @return Returns true if this Sprite is facing SOUTH, not true SOUTH, but SOUTH at all.
     */
    public boolean facingSouth() {
        return dir % 360 > 180;
    }

    public int getID() {
        return id;
    }

    /**
     * Called by World if the user clicks on this Sprite.
     */
    public void onClick() {
        System.out.println("Clicked on Sprite " + getID());
    }

    /**
     Returns the center of this Sprite
     */
    public Point getCenterPoint(){
        return new Point(loc.x + pic.getWidth()/2, loc.y + pic.getHeight()/2);
    }

    /**
     Changes the speed of this Sprite
     */
    public void setSpeed(int newSpeed){
        speed = newSpeed;
    }

    /**
     Returns the current speed of this Sprite
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @return the x of the current location
     */
    public double getX(){
        return loc.getX();
    }

    /**
     * @return the y of the current location
     */
    public double getY(){
        return loc.getY();
    }

    /**
     * @param newX new x value for the location
     */
    public void setX(int newX){
        setLoc(new Point( newX, (int)loc.getY() ) );
    }

    /**
     * @param newY new y value for the location
     */
    public void setY(int newY){
        setLoc(new Point( (int)loc.getX(), newY)  );
    }


}
