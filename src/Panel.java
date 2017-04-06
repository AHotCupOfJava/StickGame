import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_UP;


/**
 * Created by clarissa_briasco on 3/16/17.
 */

public class Panel extends JPanel {


    private Stick stick;
    private Hero hero;
    private Pillar pillar1, pillar2;
    private Background background;
    private Cherries cherry;

    private Timer timer;
    private ArrayList<HeroPic> characters;
    private boolean start, grow, fall, walk, move, die, store, spacePressed, profit;
    private int pillarWidth1, pillarWidth2, pillarHeight, baseX, baseY, points, money;
    private double stickAngle;
    private Rectangle startButton, restartButton, storeButton, backButton;



    public Panel() {

        stick = new Stick(50, 500);

        int random = (int)(Math.random()*2);
        if(random == 0)
            background = new Hills(520, 770);
        else
            background = new Mountains(520, 770);
        background.setDay(background.isDay());

        if(background.isDay())
            stick.setColor(new Color(45, 22, 24));
        else
            stick.setColor(new Color(141, 98, 74));

        start = true;
        store = false;

        startButton = new Rectangle(110, 420, 100, 40);
        restartButton = new Rectangle(210, 400, 100, 40);
        storeButton = new Rectangle(310, 420, 100, 40);
        backButton = new Rectangle(210, 110, 100, 40);

        grow = false;
        fall = false;
        walk = false;
        move = false;
        die = false;

        spacePressed = false;

        hero = new Hero(50, 500, Hero.NORTH, "Hero.png");
        hero.setY(500 - hero.getPic().getHeight());

        characters = new ArrayList<HeroPic>();
        characters.add(new HeroPic("Hero.png", 100, 250));
        characters.add(new HeroPic("Hero3.png", 100, 500));
        characters.add(new HeroPic("Hero4.png", 300, 250));

        stick.setX(50 + hero.getPic().getWidth());

        stickAngle = -1.5;

        points = 0;

        money = 0;
        profit = false;

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

        cherry = new Cherries(pillar1.getX()+pillarWidth1+(int)(Math.random()*300+10)-30, 510, cherry.NORTH);

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
                if(walk && keyEvent.getKeyCode() == VK_DOWN){
                    hero.flip();
                    hero.setY((int)(hero.getY()+hero.getPic().getHeight()+5)); //5 = stick width
                    repaint();
                }

                if(keyEvent.getKeyCode() == VK_UP && hero.getLoc().y > 500){
                    hero.flip();
                    hero.setY((int)(hero.getY()-hero.getPic().getHeight()-5)); //5 = stick width
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
                    if(hero.getLoc().x+hero.getPic().getWidth() > cherry.getLoc().x && hero.getLoc().y > 500 && !profit){
                        profit = true;
                        money++;
                        cherry.setPic("transparent.png", cherry.getDir());
                    }
                    if(hero.getLoc().x+hero.getPic().getWidth() > pillar2.getX() && (int)(hero.getY()) > 500)
                        die = true;
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
                        profit = false;
                    }
                }
                else if(move){
                    hero.setX((int)hero.getX() - 10);
                    stick.setX(stick.getLoc().x - 10);
                    pillar1.setX(pillar1.getX() - 10);
                    pillar2.setX(pillar2.getX() - 10);
                    cherry.setX((int)cherry.getX()-10);
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
                        cherry.setPic("cherries.png", cherry.getDir());
                        int random = (int)(Math.random()*(pillar2.getX()-pillar1.getX()-pillar1.getW())+pillar1.getX()+pillar1.getW());
                        if(random+20 > pillar2.getX())
                            random-=20;
                        cherry.setX(random);
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
                    store = false;
                    grow = true;
                    timer.start();
                }
                else if(start && storeButton.contains(mouseEvent.getX(), mouseEvent.getY())){
                    start = false;
                    store = true;
                    repaint();
                }
                else if(store && backButton.contains(mouseEvent.getX(), mouseEvent.getY())){
                    start = true;
                    store = false;
                    repaint();
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

        if(background.isDay())
            g2.setColor(Color.BLACK);
        else
            g2.setColor(Color.WHITE);
        g2.setFont(new Font("Dialog", Font.PLAIN, 50));
        String pts = "" + points;
        g2.drawString(pts, 240, 100);

        if(background.isDay())
            stick.setColor(new Color(45, 22, 24));
        else
            stick.setColor(new Color(141, 98, 74));


        if (start) {
            g2.setColor(new Color(109, 207, 255));
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Copperplate", Font.CENTER_BASELINE, 70));
            g2.drawString("STICK HERO", 40, 82);
            g2.fill(startButton);
            g2.fill(storeButton);

            g2.setFont(new Font("Dialog", Font.BOLD, 20));
            g2.drawString("INSTRUCTIONS", 185, 140);
            g2.setFont(new Font("Dialog", Font.PLAIN, 16));
            g2.drawString("Hold space to stretch out the stick.", 125, 170);
            g2.drawString("Press the up and down arrow keys to flip sides on the stick.", 32, 200);
            g2.drawString("Collect the cherries to buy more characters.", 88, 230);
            g2.drawString("If the stick lands in the red zone, you get +1 points.", 53, 260);

            g2.setColor(new Color(109, 207, 255));
            g2.setFont(new Font("Copperplate", Font.CENTER_BASELINE, 20));
            g2.drawString("Start", 130, 445); //215 if 20pt font
            g2.drawString("Store", 330, 445);

            pillar1.draw(g2);
            pillar2.draw(g2);

            hero.draw(g2);

        }
        else if (store){
            g2.setColor(new Color(109, 207, 255));
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Copperplate", Font.CENTER_BASELINE, 70));
            g2.drawString("STORE", 140, 100);
            g2.fill(backButton);

            g2.setColor(new Color(109, 207, 255));
            g2.setFont(new Font("Copperplate", Font.CENTER_BASELINE, 20));
            g2.drawString("Back", 235, 135); //??? if 20pt font

            g2.setColor(new Color(225, 225, 225));
            g2.fillRoundRect(80, 230, 80, 90, 10, 10);
            g2.fillRoundRect(80, 480, 80, 90, 10, 10);
            g2.fillRoundRect(280, 230, 80, 90, 10, 10);
            g2.fillRoundRect(280, 480, 80, 90, 10, 10);

            g2.setStroke(new BasicStroke(3));
            g2.setColor(new Color(0, 0, 0));
            g2.drawRoundRect(80, 230, 80, 90, 10, 10);
            g2.drawRoundRect(80, 480, 80, 90, 10, 10);
            g2.drawRoundRect(280, 230, 80, 90, 10, 10);
            g2.drawRoundRect(280, 480, 80, 90, 10, 10);

            for (HeroPic p: characters){
                p.draw(g2);
            }

        }
        else {
            if(background.isDay())
                g2.setColor(Color.BLACK);
            else
                g2.setColor(Color.WHITE);
            g2.setFont(new Font("Dialog", Font.PLAIN, 20));
            g2.drawString("Cherries: " + money, 10, 30);

            if(background.isDay())
                stick.setColor(new Color(45, 22, 24));
            else
                stick.setColor(new Color(141, 98, 74));

            if (grow) {
                stick.draw(g2, 0, 0);
            } else if (fall) {
                stick.draw(g2, 2, stickAngle);
            } else if (walk) {
                stick.draw(g2, 1, 0);

                int distance = stick.getLoc().x + stick.getHeight();
                if (distance > pillar2.getX() + pillar2.getW() / 2 - 6 && distance < pillar2.getX() + pillar2.getW() / 2 + 6) {
                    if(background.isDay())
                        g2.setColor(Color.BLACK);
                    else
                        g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Dialog", Font.PLAIN, 20));
                    g2.drawString("PERFECT! +1", 250, 300);
                }
            } else if (move) {
                stick.draw(g2, 1, 0);
            }

            cherry.draw(g2);

            pillar1.draw(g2);
            pillar2.draw(g2);

            hero.draw(g2);
        }


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

        grow = false;
        fall = false;
        walk = false;
        move = false;
        die = false;

        spacePressed = false;

        hero = new Hero(50, 500, Hero.NORTH, "Hero.png");
        hero.setY(500 - hero.getPic().getHeight());

        stick.setX(50 + hero.getPic().getWidth());

        stickAngle = -1.5;

        points = 0;
        profit = false;

        int random = (int)(Math.random()*2);
        if(random == 0)
            background = new Hills(520, 770);
        else
            background = new Mountains(520, 770);

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

        cherry = new Cherries(pillar1.getX()+pillarWidth1+(int)(Math.random()*300+10)-30, 510, cherry.NORTH);

        timer.restart();
    }

}