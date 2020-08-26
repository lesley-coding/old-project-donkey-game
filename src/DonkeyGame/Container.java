package DonkeyGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;


public class Container extends JPanel implements KeyListener {
    private JLayeredPane panel;

    public Container() {
        setLayout(new CardLayout());
        welcome();
        setFocusable(true);
        addKeyListener(this);
    }

    public void gameRunning() {
        removeAll();
        panel = new GamePanel();
        add(panel);
        panel.setVisible(true);
        panel.requestFocusInWindow();
        revalidate();
    }


    public void welcome() {
        panel = new WelComePanel();
        add(panel);
        revalidate();
    }

    public static Font getCustomFont() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font myFont = null;
        try {
            myFont = Font.createFont(Font.TRUETYPE_FONT, new File("font/PixelMplus12-Regular.ttf"));
            ge.registerFont(myFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        assert myFont != null;
        return myFont.deriveFont(myFont.getSize() * 25f);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            gameRunning();
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
