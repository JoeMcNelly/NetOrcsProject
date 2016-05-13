package netOrcsClient;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

public class BackGroundPanel extends JComponent {
    private Image image;
    public BackGroundPanel(Image image) {
        this.image = image;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
