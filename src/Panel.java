import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * Created by clarissa_briasco on 3/16/17.
 */

public class Panel extends JPanel {

    public Panel() {

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });

    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        Stick stick = new Stick(200, 500);
        stick.draw(g2, 0, 0);
    }

}