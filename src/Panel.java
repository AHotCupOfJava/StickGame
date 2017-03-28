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
    private Background background;
    private Timer timer;
    private boolean start, grow, fall, walk, move, die, spacePressed;
    private int pillarWidth1, pillarWidth2, pillarHeight, baseX, baseY, points;
    private double stickAngle;
    private Rectangle startButton, restartButton;



    public Panel() {

        stick = new Stick(50, 500);

        background = new Background(520, 770);

        start = true;

        startButton = new Rectangle(210, 420, 100, 40);
        restartButton = new Rectangle(210, 400, 100, 40);

        grow = false;
        fall = false;
        walk = false;
        move = false;
        die = false;

        spacePressed = false;

        hero = new Hero(50, 500, Hero.NORTH);
        hero.setY(500 - hero.getPic().getHeight());

        stick.setX(50 + hero.getPic().getWidth());

        stickAngle = -1.5;

        points = 0;

        pillarWidth1 = (int) (Math.random() * 130 + 30);
        pillarWidth2 = (int) (Math.random() * 130 + 30);

        /*
        two Pillar objects that change every time the Hero moves
         */
        baseX = 50;
        baseY = 500;
        pillarHeight = 500;

        pillar1 = new Pillar(baseX - pillarWidth1 + baseX, baseY, pillarWidth1, pillarHeight);
        pillar2 = new Pillar(50+pillarWidth1+(int)(Math.random()*300+10), baseY, pillarWidth2, pillarHeight);

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
                        int distance = stick.getLoc().x + stick.getHeight();
                        if(distance > pillar2.getX()+pillar2.getW()/2-6 && distance < pillar2.getX()+pillar2.getW()/2+6)
                            points++;
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
                        points++;
                    }
                }
                else if(die){
                    hero.setDir(Hero.SOUTH);
                    hero.setY((int)hero.getY() + 20);
                }


                repaint();
            }
        });

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                System.out.println(mouseEvent.getX() + ", " + mouseEvent.getY());

                if(start && startButton.contains(mouseEvent.getX(), mouseEvent.getY())){
                    start = false;
                    grow = true;
                    timer.start();
                }
                else if(die && hero.getY() > getHeight() && restartButton.contains(mouseEvent.getX(), mouseEvent.getY())){
                    restart();
                    System.out.println("restart");
                }
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

        background.draw(g2);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Dialog", Font.PLAIN, 50));
        String pts = ""+points;
        g2.drawString(pts, 240, 100);


        if(start){
            g2.setColor(new Color(109, 207, 255));
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Copperplate", Font.CENTER_BASELINE, 70));
            g2.drawString("STICK HERO", 40, getHeight() / 2);
            g2.fill(startButton);

            g2.setColor(new Color(109, 207, 255));
            g2.setFont(new Font("Copperplate", Font.CENTER_BASELINE, 20));
            g2.drawString("Start", 230, 445); //215 if 20pt font
        }
        else if(grow){
            stick.draw(g2, 0, 0);
        }
        else if(fall){
            stick.draw(g2, 2, stickAngle);
        }
        else if(walk){
            stick.draw(g2, 1, 0);

            int distance = stick.getLoc().x + stick.getHeight();
            if(distance > pillar2.getX()+pillar2.getW()/2-6 && distance < pillar2.getX()+pillar2.getW()/2+6){
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Dialog", Font.PLAIN, 20));
                g2.drawString("PERFECT! +1", 250, 300);
            }
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
                g2.setColor(new Color(255, 0, 0, 200));
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Copperplate", Font.PLAIN, 60));
                g2.drawString("You died.", 123, getHeight() / 2);
                g2.fill(restartButton);

                g2.setColor(new Color(255, 0, 0));
                g2.setFont(new Font("Copperplate", Font.CENTER_BASELINE, 20));
                g2.drawString("Restart", 217, 425); //??? if 20pt font

                timer.stop();
            }
        }


    }

    public void restart(){
        stick = new Stick(50, 500);

        start = true;

        startButton = new Rectangle(210, 420, 100, 40);
        restartButton = new Rectangle(210, 400, 100, 40);

        grow = false;
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

        timer.restart();
    }

}