import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static java.awt.event.KeyEvent.VK_SPACE;


/**
 * Created by clarissa_briasco on 3/16/17.
 */

public class Panel extends JPanel {

    private Stick stick;
    private Hero hero;
    private Timer timer;
    private boolean grow, fall, walk, move;
    private int stickAngle;



    public Panel() {

        stick = new Stick(200, 500);

        grow = true;
        fall = false;
        walk = false;
        move = false;

        hero = new Hero(200, 500, Hero.NORTH);
        stickAngle = -90;

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

                if(grow && keyEvent.getKeyCode() == 32){
                    stick.grow();
                    repaint();
                }

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

                if(grow && keyEvent.getKeyCode() == 32){
                    grow = false;
                    fall = true;
                    repaint();
                }

            }
        });

        timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                hero.setX((int)hero.getX() + 10);

                if(hero.getX() < 200){
                    move = false;
                    grow = true;
                    stick = new Stick(200, 500);
                }
                if(fall){
                    stickAngle += 10;
                    if(stickAngle == 0){
                        fall = false;
                        walk = true;
                    }
                }
                if(walk){
                    hero.setX((int)hero.getX() + 10);
                    stick.setX(stick.getLoc().x + 10);
                    if(hero.getX() > 400){
                        walk = false;
                        move = true;
                    }
//                    if(hero.getX() > )
                }
                if(move){
                    hero.setX((int)hero.getX() - 10);
                    stick.setX(stick.getLoc().x - 10);
                }


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

        if(grow){
            stick.draw(g2, 0, 0);
        }
        if(fall){
            stick.draw(g2, 2, stickAngle);
        }
        if(walk){
            stick.draw(g2, 1, 0);
        }
        if(move){
            stick.draw(g2, 1, 0);
        }


        Pillar pillar = new Pillar(50, 500, (int) (Math.random() * 130 + 15), 500);
        pillar.draw(g2);


    }

}