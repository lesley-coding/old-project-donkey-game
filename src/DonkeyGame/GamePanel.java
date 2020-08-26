package DonkeyGame;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public class GamePanel extends JLayeredPane implements KeyListener {
    private static final int CAR_LEFT_X = 205;
    private static final int CAR_INITIAL_Y = 250;
    private static final int DONKEY_SPEED = 20;
    private static final int CAR_SPEED = 10;
    private static final int DONKEY_LENGTH = 28;

    private int car_posX;
    private int car_posY;
    private Timer timer;


    private int donkey_delay;
    private int car_delay;
    private int hit_stage;
    private boolean hasWin;

    private static final Font MY_FONT = Container.getCustomFont();
    private static Image carImg;
    private static Image bgImag;
    private static Image donkeyImg;
    private static java.util.List<Image> donkey_hit_r;
    private static java.util.List<Image> donkey_hit_l;
    private static java.util.List<Image> car_hit_r;
    private static java.util.List<Image> car_hit_l;

    private boolean hasHit;

    private static float dash_phase;
    private static boolean isLeft;
    private Donkey donkey;
    private static final int DELAY = 100;


    public GamePanel() {
        initGame();
    }

    private void initGame() {
        initUI();
        initValues();
        timer = new Timer(DELAY, e -> {
            if (!hasHit) {
                moveCar();
                if (Donkey.getNum() != 0)
                    moveDonkey();
            }
            repaint();
            if (!hasHit)
                hasHit = checkIfHit();
        });
        timer.start();
        this.addKeyListener(this);
    }


    private void initValues() {
        car_posX = CAR_LEFT_X;
        car_posY = CAR_INITIAL_Y;
        donkey_delay = 10;
        hasHit = false;
        hasWin = false;
        hit_stage = 0;
        isLeft = true;
        car_delay = 15;
        dash_phase = 0f;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        doDrawing(g2d);
    }

    private void doDrawing(Graphics2D g2d) {
        g2d.drawImage(bgImag, 0, 0, this);
        drawText_Ui(g2d);
        setLineStroke(g2d);
        g2d.drawLine(270, 380, 270, 0);

        if (hasWin) {
            showWin(g2d);
        }

        if (!hasHit) {
            g2d.drawImage(carImg, car_posX, car_posY, this);
            drawDonkey(g2d);
        }
        if (hasHit) {
            showHitImg(g2d);
            showBoom(g2d);
        }
    }


    private void moveCar() {
        if (!hasWin) {
            car_delay--;
            if (car_delay == 0) {
                car_posY = car_posY - CAR_SPEED;
                car_delay = 15;
            }
            dash_phase += 100.0f;
        }
    }


    private void moveDonkey() {
        if (!hasWin) {
            donkey.setD_PosY(donkey.getD_PosY() + DONKEY_SPEED);
            hasWin = checkIfWin();
        }
    }

    private boolean checkIfWin() {
        if (Donkey.getNum() == Donkey.getMaxNum() && donkeyOutOfView()) {
            return true;
        }
        return false;
    }

    private void drawDonkey(Graphics2D g2d) {
        if (donkey_delay != 0)
            donkey_delay--;
        else {
            if (Donkey.getNum() == 0) {
                donkey = new Donkey();
            }
            if (Donkey.getNum() <= Donkey.getMaxNum() + 1) {
                if (donkeyOutOfView())
                    donkey = new Donkey();
                if (Donkey.getNum() < Donkey.getMaxNum() + 1)
                    g2d.drawImage(donkeyImg, donkey.getD_PosX(), donkey.getD_PosY(), this);
            }
        }

    }

    private void showHitImg(Graphics2D g2d) {
        drawCarHitImg(g2d);
        drawDonkeyHitImg(g2d);
        if (hit_stage < 5) {
            hit_stage += 1;
        }
        if (donkeyOutOfView() && carOutOfView()) {
            //restart
            timer.stop();
            Donkey.setNum(0);
            initGame();
        }
    }

    private boolean donkeyOutOfView() {
        return donkey.getD_PosX() < 0 || donkey.getD_PosY() < 0 || donkey.getD_PosY() > 300;
    }

    private boolean carOutOfView() {
        return car_posX < 0 || car_posY > this.getHeight();
    }

    private void drawDonkeyHitImg(Graphics2D g2d) {
        if (!donkeyOutOfView()) {
            donkey.setD_PosY(donkey.getD_PosY() - 30);
            donkey.setD_PosX(donkey.getD_PosX() - 30);
            g2d.drawImage(donkey_hit_l.get(hit_stage / 2), donkey.getD_PosX(), donkey.getD_PosY(), this);
            if (isLeft) {
                g2d.drawImage(donkey_hit_r.get(hit_stage / 2), 480 - donkey.getD_PosX(), donkey.getD_PosY(), this);
            } else {
                g2d.drawImage(donkey_hit_r.get(hit_stage / 2), 600 - donkey.getD_PosX(), donkey.getD_PosY(), this);
            }
        }
    }

    private void drawCarHitImg(Graphics2D g2d) {
        if (!carOutOfView()) {
            car_posY += 10;
            car_posX -= 30;
            g2d.drawImage(car_hit_l.get(hit_stage / 2), car_posX, car_posY, this);
            if (isLeft)
                g2d.drawImage(car_hit_r.get(hit_stage / 2), 480 - car_posX, car_posY, this);

            else
                g2d.drawImage(car_hit_r.get(hit_stage / 2), 600 - car_posX, car_posY, this);
        }
    }


    private boolean checkIfHit() {
        if (Donkey.getNum() != 0) {
            if (isLeft && donkey.isLeft() || !isLeft && !donkey.isLeft())
                return car_posY - donkey.getD_PosY() < DONKEY_LENGTH;
        }
        return false;
    }


    private void switchToRightLane() {
        car_posX += 80;
        isLeft = false;
        car_delay = 15;
    }

    private void switchToLeftLane() {
        car_posX -= 80;
        isLeft = true;
        car_delay = 15;
    }


    private void showWin(Graphics2D g2d) {
        g2d.setColor(Color.getHSBColor(0, 0, (float) 0.33));
        Rectangle2D winRec = new Rectangle2D.Double(66, 196, 100, 30);
        g2d.fill(winRec);
        g2d.setColor(Color.getHSBColor(0, 0, (float) 0.66));
        g2d.drawString("you win!", 70, 219);
    }

    private void showBoom(Graphics2D g2d) {
        g2d.setColor(Color.getHSBColor(0, 0, (float) 0.33));
        Rectangle2D winRec = new Rectangle2D.Double(112, 200, 67, 23);
        g2d.fill(winRec);
        g2d.setColor(Color.getHSBColor(0, 0, (float) 0.66));
        g2d.drawString("BOOM!", 118, 219);
    }


    private void drawText_Ui(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.getHSBColor(0, 0, (float) 0.33));
        Rectangle2D hintRec = new Rectangle2D.Double(375, 220, 200, 45);
        Rectangle2D exitRec = new Rectangle2D.Double(375, 300, 200, 45);
        Rectangle2D numRec = new Rectangle2D.Double(40, 16, 100, 23);
        Rectangle2D numRec2 = new Rectangle2D.Double(58, 55, 40, 20);
        g2d.fill(hintRec);
        g2d.fill(exitRec);
        g2d.fill(numRec);
        g2d.fill(numRec2);

        g2d.setColor(Color.getHSBColor(0, 0, (float) 0.66));
        g2d.setFont(MY_FONT);
        g2d.drawString("Press ← →", 380, 240);
        g2d.drawString("to switch lanes", 380, 260);
        g2d.drawString("Press ESC", 380, 320);
        g2d.drawString("to Exit", 380, 340);
        g2d.drawString("Donkey", 45, 35);
        if (Donkey.getNum() > Donkey.getMaxNum())
            g2d.drawString(Donkey.getMaxNum() + "", 70, 75);
        else
            g2d.drawString(Donkey.getNum() + "", 70, 75);
    }


    private void setLineStroke(Graphics2D g2d) {
        float[] dash = {15f, 0f, 5f};
        BasicStroke bs = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, dash, dash_phase);
        g2d.setStroke(bs);
        g2d.setColor(Color.gray);
    }

    private void initUI() {
        carImg = new ImageIcon("img/car.png").getImage();
        bgImag = new ImageIcon("img/background.jpg").getImage();
        donkeyImg = new ImageIcon("img/donkey.png").getImage();

        donkey_hit_l = new ArrayList<>();
        donkey_hit_l.add(new ImageIcon("img/d_hit_left1.png").getImage());
        donkey_hit_l.add(new ImageIcon("img/d_hit_left2.png").getImage());
        donkey_hit_l.add(new ImageIcon("img/d_hit_left3.png").getImage());


        donkey_hit_r = new ArrayList<>();
        donkey_hit_r.add(new ImageIcon("img/d_hit_right1.png").getImage());
        donkey_hit_r.add(new ImageIcon("img/d_hit_right2.png").getImage());
        donkey_hit_r.add(new ImageIcon("img/d_hit_right3.png").getImage());

        car_hit_l = new ArrayList<>();
        car_hit_l.add(new ImageIcon("img/car_left1.png").getImage());
        car_hit_l.add(new ImageIcon("img/car_left2.png").getImage());
        car_hit_l.add(new ImageIcon("img/car_left3.png").getImage());


        car_hit_r = new ArrayList<>();
        car_hit_r.add(new ImageIcon("img/car_right1.png").getImage());
        car_hit_r.add(new ImageIcon("img/car_right2.png").getImage());
        car_hit_r.add(new ImageIcon("img/car_right3.png").getImage());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT && !isLeft) {
            switchToLeftLane();
        }
        if (key == KeyEvent.VK_RIGHT && isLeft) {
            switchToRightLane();
        }
        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

}
