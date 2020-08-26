package DonkeyGame;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;

public class WelComePanel extends JLayeredPane {
    private static final Image BLACK_BG = new ImageIcon("img/blackBG.jpg").getImage();
    private static final Font MY_FONT = Container.getCustomFont();


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(BLACK_BG, 0, 0, null);
        showTexts(g, g2d);
    }

    private void showTexts(Graphics g, Graphics2D g2d) {
        g2d.setFont(MY_FONT);
        FontMetrics metrics = g.getFontMetrics(MY_FONT);
        g2d.setColor(Color.green);
        g2d.setStroke(new BasicStroke(3));

        g2d.drawRect(120, 75, 360, 75);
        String gameName = "Donkey Game";
        g2d.drawString(gameName, (360 - metrics.stringWidth(gameName)) / 2 + 120, (75 - metrics.getHeight()) / 2 + 75 + metrics.getAscent());


        g2d.drawRect(120, 75, 360, 75);
        g2d.setColor(Color.white);


        String s = "Based on DOS game Donkey.BAS(IBM)";
        g2d.drawString(s, (600 - metrics.stringWidth(s)) / 2, 230);

        g2d.setColor(Color.yellow);
        String startHint = "press space bar to continue";
        g2d.drawString(startHint, (600 - metrics.stringWidth(startHint)) / 2, 280);
    }


}

