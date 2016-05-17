package netOrcsClient;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import netOrcsShared.GameObjects;
import netOrcsShared.State;

public class NetOrcsPanel extends JPanel {
	Graphics2D g2;
	State state;

	public NetOrcsPanel() {
	}

	@Override
	protected void paintComponent(java.awt.Graphics g) {
		super.paintComponent(g);
		this.g2 = (Graphics2D) g;
		if (state != null) {
			for (GameObjects orc : state.getOrcs()) {
				if (orc.isAlive()) {
					Ellipse2D.Double eOrc = new Ellipse2D.Double(orc
							.getPosition().getX(), orc.getPosition().getY(),
							orc.size(), orc.size());
					g2.setColor(Color.GREEN);
					g2.fill(eOrc);
					g2.draw(eOrc);

				}
			}
			for (GameObjects hero : state.getHeroes()) {
				if (hero.isAlive()) {
					Rectangle2D.Double ehero = new Rectangle2D.Double(hero
							.getPosition().getX(), hero.getPosition().getY(),
							hero.size(), hero.size());
					g2.setColor(Color.BLUE);
					g2.fill(ehero);
					g2.draw(ehero);
				}
			}

		}
	}

	public synchronized void updateState(State s) {
		this.state = s;
		if (state.gameOver()) {
			int response = JOptionPane.showConfirmDialog(null, "Player"
					+ state.getLastHero().getIndex()
					+ " has won.\n Would you like to play again", "Game Over",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.NO_OPTION) {
				System.exit(0);
			} else if (response == JOptionPane.YES_OPTION) {
				System.out.println("Yes");
			}
		} else {
			repaint();
		}
	}

}
