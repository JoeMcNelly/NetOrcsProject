package netOrcsClient;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.JPanel;

import netOrcsShared.GameObjects;
import netOrcsShared.State;

public class NetOrcsPanel extends JPanel {
	Graphics2D g2;
	int size;
	State state;

	public NetOrcsPanel() {
		this.size = 10;

	}

	@Override
	protected void paintComponent(java.awt.Graphics g) {
		super.paintComponent(g);
		this.g2 = (Graphics2D) g;
		this.g2.draw(new Rectangle2D.Double(10, 10, 10, 10));
		System.out.println("IN PAINT BITCH");
		if (state != null) {
			for (GameObjects orc : state.getOrcs()) {
				System.out.println(orc.getPosition().getX() + " "
						+ orc.getPosition().getY() + " " + this.getGraphics());
				Ellipse2D.Double eOrc = new Ellipse2D.Double(orc.getPosition()
						.getX(), orc.getPosition().getY(), size, size);
				System.out.println(eOrc);
				g2.setColor(Color.GREEN);
				g2.fill(eOrc);
				g2.draw(eOrc);
				
			}
		}
	}

	public void updateState(State s) {
		this.state = s;
		repaint();
	}

}
