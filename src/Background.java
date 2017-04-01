import java.awt.*;

/**
 * Created by sarah_zhang on 3/31/17.
 */
public interface Background {
    void draw(Graphics2D g2);

    boolean isDay();

    void setDay(boolean day);
}
