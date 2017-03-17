import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static java.awt.event.KeyEvent.VK_SPACE;


/**
 * Created by clarissa_briasco on 3/16/17.
 */

public class Panel extends JPanel {

    Stick stick;

    Timer timer;

    public Panel() {

        stick = new Stick(200, 500);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

                if(keyEvent.getKeyCode() == 32){
                    stick.grow();
                    repaint();
                }

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });

        timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                repaint();
            }
        });

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                System.out.println(mouseEvent.getX() + ", " + mouseEvent.getY());
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });

    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        stick.draw(g2, 0, 0);

        Pillar pillar = new Pillar(50, 500, (int) (Math.random() * 130 + 15), 500);
        pillar.draw(g2);

    }

}