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
		System.out.println("IN PAINT BITCH");
		if (state != null) {
			for (GameObjects orc : state.getOrcs()) {
				Ellipse2D.Double eOrc = new Ellipse2D.Double(orc.getPosition()
						.getX(), orc.getPosition().getY(), size, size);
				g2.setColor(Color.GREEN);
				g2.fill(eOrc);
				g2.draw(eOrc);
				
			}
			for(GameObjects hero : state.getHeroes()){
				Rectangle2D.Double ehero = new Rectangle2D.Double(hero.getPosition()
						.getX(), hero.getPosition().getY(), size, size);
				g2.setColor(Color.BLUE);
				g2.fill(ehero);
				g2.draw(ehero);
			}
		}
	}

	public synchronized void updateState(State s) {
		this.state = s;
//		this.paintImmediately(0, 0, 750, 750);
		repaint();
	}

}
