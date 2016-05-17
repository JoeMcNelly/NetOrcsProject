package netOrcsClient;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

import netOrcsShared.GameObjects;
import netOrcsShared.State;

public class HeroLeaderBoardPanel extends JPanel {
	Graphics2D g2;
	State state;
	
	
	public HeroLeaderBoardPanel() {
		super();
		this.add(new JLabel("Leader Board"));
	}
	@Override
	protected void paintComponent(java.awt.Graphics g) {
		if(this.state != null){
			for(GameObjects hero : this.state.getHeroes()){
				if(!hero.isAlive()){
					this.add(new JLabel(hero.getColor() + ": " + hero.getTime()));
				}
			}
		}
	}
	
	public synchronized void updateState(State s) {
		System.out.println(s.getHeroes());
		this.state = s;
		this.repaint();
		
		
	}
	
}
