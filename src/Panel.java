import com.sun.tools.doclets.internal.toolkit.util.SourceToHTMLConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.QuadCurve2D;

import static java.awt.event.KeyEvent.VK_SPACE;


/**
 * Created by clarissa_briasco on 3/16/17.
 */

public class Panel extends JPanel {

    private Stick stick;
    private Hero hero;
    private Pillar pillar1, pillar2;
    private Timer timer;
    private boolean grow, fall, walk, move, die, spacePressed;
    private int pillarWidth1, pillarWidth2, pillarHeight, baseX, baseY;
    private double stickAngle;



    public Panel() {

        stick = new Stick(50, 500);

        grow = true;
        fall = false;
        walk = false;
        move = false;
        die = false;
        spacePressed = false;

        hero = new Hero(50, 500, Hero.NORTH);
        hero.setY(500 - hero.getPic().getHeight());

        stick.setX(50 + hero.getPic().getWidth());

        stickAngle = -1.5;

        pillarWidth1 = (int) (Math.random() * 130 + 30);
        pillarWidth2 = (int) (Math.random() * 130 + 30);

        /*
        two Pillar objects that change every time the Hero moves
         */
        baseX = 50;
        baseY = 500;
        pillarHeight = 500;
        pillar1 = new Pillar(baseX - pillarWidth1 + baseX, baseY, pillarWidth1, pillarHeight);
        pillar2 = new Pillar(520 - pillarWidth2 - 50, baseY, pillarWidth2, pillarHeight);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

                if(grow && keyEvent.getKeyCode() == 32){
                    spacePressed = true;
                    repaint();
                }

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

                if(grow && keyEvent.getKeyCode() == 32){
                    grow = false;
                    spacePressed = false;
                    fall = true;
                    repaint();
                }

            }
        });

        timer = new Timer(35, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if(grow && spacePressed){
                    stick.grow();
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
                    int distance = stick.getLoc().x + stick.getHeight();
                    if(hero.getX() > distance && (hero.getX() < pillar2.getX() || hero.getX() > pillar2.getX() + pillar2.getW() )){
                        die = true;
                        grow = false;
                        fall = false;
                        walk = false;
                        move = false;
                    }
                    else if(hero.getX() > distance && hero.getX() > pillar2.getX() && hero.getX() < pillar2.getX() + pillar2.getW()){
                        walk = false;
                        move = true;
                    }
                }
                else if(move){
                    hero.setX((int)hero.getX() - 10);
                    stick.setX(stick.getLoc().x - 10);
                    pillar1.setX(pillar1.getX() - 10);
                    pillar2.setX(pillar2.getX() - 10);
                    if(hero.getX() < 50){
                        move = false;
                        grow = true;

                        stick = new Stick(50 + hero.getPic().getWidth(), 500);
                        stickAngle = -1.5;

                        int w = (int) (Math.random() * 130 + 30);
                        pillar2.setX(100 - pillar2.getW());
                        pillar1 = pillar2;
                        pillar2 = new Pillar(getWidth() - baseX - w, baseY, w, pillarHeight);
                    }
                }
                else if(die){
                    hero.setDir(Hero.SOUTH);
                    hero.setY((int)hero.getY() + 20);
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

        Background background = new Background(getWidth(), getHeight());
        background.draw(g2);


        if(grow){
            stick.draw(g2, 0, 0);
        }
        else if(fall){
            stick.draw(g2, 2, stickAngle);
        }
        else if(walk){
            stick.draw(g2, 1, 0);
        }
        else if(move){
            stick.draw(g2, 1, 0);
        }

        pillar1.draw(g2);
        pillar2.draw(g2);

        hero.draw(g2);

        if(die){
            stick.draw(g2, 1, 0);
            if(hero.getY() > getHeight()) {
                g2.setColor(new Color(255, 0, 0, 190));
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Copperplate", Font.PLAIN, 50));
                g2.drawString("You died.", 140, getHeight() / 2);
                timer.stop();
            }
        }


    }

}