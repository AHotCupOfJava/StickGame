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
    private int pillarWidth;
    private double stickAngle;



    public Panel() {

        stick = new Stick(200, 500);

        grow = true;
        fall = false;
        walk = false;
        move = false;

        hero = new Hero(200, 500, Hero.NORTH);
        stickAngle = -1.5;

        pillarWidth = (int)(Math.random() * 130 + 15);

        pillarWidth1 = (int) (Math.random() * 130 + 15);
        pillarWidth2 = (int) (Math.random() * 130 + 15);

        /*
        two Pillar objects that change every time the Hero moves
         */
        int x = 50;
        int y = 500;
        int h = 500;
        pillar1 = new Pillar(x, y, pillarWidth1, h);
        pillar2 = new Pillar(getWidth()-x-pillarWidth2, y, pillarWidth2, h);

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
                else if(fall){
                    stickAngle += 0.1;
                    if(stickAngle >= 0){
                        fall = false;
                        walk = true;
                    }
                }
                else if(walk){
                    hero.setX((int)hero.getX() + 10);
                    if(hero.getX() >  400){
                        walk = false;
                        move = true;
                    }
//                    if(hero.getX() > )
                }
                else if(move){
                    hero.setX((int)hero.getX() - 10);
                    stick.setX(stick.getLoc().x - 10);
                }


                repaint();
            }
        });
        timer.start();

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

//        hero.draw(g2);

        pillar1.draw(g2);
        pillar2.draw(g2);
    }

    public void pillarMove(){
        while(pillar1.getX() > 0) {
            pillar1.setX(getX()-1);
        }
        while(pillar2.getX() > 0){
            pillar2.setX(getX()-1);
        }
    }

}