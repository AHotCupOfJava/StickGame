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
    private HeroPic skin;

    private Timer timer;
    private ArrayList<HeroPic> characters;
    private boolean start, grow, fall, walk, move, die, store, spacePressed, profit;
    private int pillarWidth1, pillarWidth2, pillarHeight, baseX, baseY, points, money, page;
    private double stickAngle;
    private Rectangle startButton, restartButton, storeButton, backButton, left, right;



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
        backButton = new Rectangle(210, 140, 100, 40);
        left = new Rectangle(148, 600, 60, 30);
        right = new Rectangle(321, 600, 60, 30);

        page = 0;

        grow = false;
        fall = false;
        walk = false;
        move = false;
        die = false;

        spacePressed = false;

        hero = new Hero(50, 500, Hero.NORTH, "Hero.png");
        hero.setY(500 - hero.getPic().getHeight());


        //StickHero characters
        characters = new ArrayList<HeroPic>();
        characters.add(new HeroPic("Hero.png", 130, 250, new Rectangle(115, 230, 80, 90), 2)); //1
        characters.add(new HeroPic("Hero3.png", 130, 500, new Rectangle(115, 480, 80, 90), 2)); //2
        characters.add(new HeroPic("Hero4.png", 330, 250, new Rectangle(315, 230, 80, 90), 2)); //3
//        characters.add(new HeroPic("Hero5.png", 300, 500)); //4

        //domesticated animals
        characters.add(new HeroPic("Cat.png", 130, 250, new Rectangle(115, 230, 80, 90), 3));
        characters.add(new HeroPic("Dog.png", 130, 500, new Rectangle(115, 480, 80, 90), 3));
        characters.add(new HeroPic("Pig.png", 330, 250, new Rectangle(315, 230, 80, 90), 3));
        characters.add(new HeroPic("Horse.png", 330, 500, new Rectangle(315, 480, 80, 90), 3));

        //wild animals
        characters.add(new HeroPic("Deer.png", 130, 250, new Rectangle(115, 230, 80, 90), 4));
        characters.add(new HeroPic("Fox.png", 130, 500, new Rectangle(115, 480, 80, 90), 4));
        characters.add(new HeroPic("Elephant.png", 330, 250, new Rectangle(315, 230, 80, 90), 4));
        characters.add(new HeroPic("Giraffe.png", 330, 500, new Rectangle(315, 480, 80, 90), 4));

        //disney characters
        characters.add(new HeroPic("Mickey.png", 130, 250, new Rectangle(115, 230, 80, 90), 5));
        characters.add(new HeroPic("Pooh.png", 130, 500, new Rectangle(115, 480, 80, 90), 5));
        characters.add(new HeroPic("Simba.png", 330, 250, new Rectangle(315, 230, 80, 90), 5));
        characters.add(new HeroPic("CheshireCat.png", 330, 500, new Rectangle(315, 480, 80, 90), 5));

        skin = characters.get(0);
        skin.unlock();
        skin.setSkin(true);

        hero.setPic(skin.getPic());
        hero.setY(500 - hero.getPic().getHeight());


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

        pillar1 = new Pillar( (int)stick.getLoc().getX() - pillarWidth1 + 5, baseY, pillarWidth1, pillarHeight );
        pillar2 = new Pillar( 50+pillarWidth1+(int)(Math.random()*300+10), baseY, pillarWidth2, pillarHeight );

        cherry = new Cherries(pillar1.getX()+pillar1.getW()+(int)(Math.random()*(pillar2.getX()-pillar1.getX()-pillar1.getW())), 510, cherry.NORTH);


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

                if(walk && keyEvent.getKeyCode() == VK_UP && hero.getLoc().y > 500){
                    hero.flip();
                    hero.setY((int)(hero.getY()-hero.getPic().getHeight()-5)); //5 = stick width
                    repaint();
                }

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

                else if(store) {
                    if(page == 0) {
                        for (int i = 0; i < 3; i++) {
                            HeroPic p = characters.get(i);
                            if (p.clicked(mouseEvent.getX(), mouseEvent.getY())) {
                                chosen(p);
                            }
                        }
                    }
                    else if(page == 1){
                        for (int i = 3; i < 7; i++) {
                            HeroPic p = characters.get(i);
                            if (p.clicked(mouseEvent.getX(), mouseEvent.getY())) {
                                chosen(p);
                            }
                        }
                    }
                    else if(page == 2){
                        for (int i = 7; i < 11; i++) {
                            HeroPic p = characters.get(i);
                            if (p.clicked(mouseEvent.getX(), mouseEvent.getY())) {
                                chosen(p);
                            }
                        }
                    }
                    else if(page == 3){
                        for (int i = 11; i < 15; i++) {
                            HeroPic p = characters.get(i);
                            if (p.clicked(mouseEvent.getX(), mouseEvent.getY())) {
                                chosen(p);
                            }
                        }
                    }

                    if (backButton.contains(mouseEvent.getX(), mouseEvent.getY())) {
                        start = true;
                        store = false;
                        repaint();
                    }
                    else if (left.contains(mouseEvent.getX(), mouseEvent.getY()) && page > 0) {
                        page--;
                        repaint();
                    }
                    else if (right.contains(mouseEvent.getX(), mouseEvent.getY()) && page < 3) {
                        page++;
                        repaint();
                    }
                }

                else if(die && hero.getY() > getHeight() && restartButton.contains(mouseEvent.getX(), mouseEvent.getY())){
                    restart();
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
                    if(hero.getLoc().x+hero.getPic().getWidth() > pillar2.getX() && (int)(hero.getY()) > 500){
                        die = true;
                        grow = false;
                        fall = false;
                        walk = false;
                        move = false;
                    }
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
                        pillar2.setX( (int)stick.getLoc().getX() - pillar2.getW() + 5 );
                        pillar1 = pillar2;
                        pillar2 = new Pillar(getWidth() - baseX - w, baseY, w, pillarHeight);
                        points++;
                        cherry.setPic("cherries.png", cherry.getDir());
                        int random = pillar1.getX()+pillar1.getW()+(int)(Math.random()*(pillar2.getX()-pillar1.getX()-pillar1.getW()));
                        if(random+20 > pillar2.getX())
                            random-=20;
                        cherry.setX(random);
                    }
                }
                else if(die){
                    if(!hero.isFlipped())
                        hero.setDir(Hero.SOUTH);
                    hero.setY((int)hero.getY() + 20);
                }

                repaint();
            }
        });



    }


    public void chosen(HeroPic p){

        skin.setSkin(false);

        if (money >= p.getPrice()) {
            money -= p.getPrice();
            p.unlock();
        }

        p.setSkin(true);
        skin = p;

        hero.setPic(skin.getPic());
        hero.setY(500 - hero.getPic().getHeight());

        stick.setX(50 + hero.getPic().getWidth());
        pillar1.setX( (int)stick.getLoc().getX() - pillar1.getW() + 5);

        repaint();
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
            g2.fill(left);
            g2.fill(right);

            g2.setColor(new Color(109, 207, 255));

            g2.setFont(new Font("Dialog", Font.CENTER_BASELINE, 14));
            g2.drawString("<--", 163, 620);
            g2.drawString("-->", 336, 620);

            g2.setFont(new Font("Copperplate", Font.CENTER_BASELINE, 20));
            g2.drawString("Back", 235, 165); //??? if 20pt font


            if(page == 0) {
                for (int i = 0; i < 3; i++) {
                    characters.get(i).draw(g2);
                }
            }
            else if(page == 1){
                for (int i = 3; i < 7; i++) {
                    characters.get(i).draw(g2);
                }

            }
            else if(page == 2){
                for (int i = 7; i < 11; i++) {
                    characters.get(i).draw(g2);
                }

            }
            else if(page == 3){
                for (int i = 11; i < 15; i++) {
                    characters.get(i).draw(g2);
                }
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

        page = 0;

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

        cherry = new Cherries(pillar1.getX()+pillar1.getW()+(int)(Math.random()*(pillar2.getX()-pillar1.getX()-pillar1.getW())), 510, cherry.NORTH);

        timer.restart();
    }

}