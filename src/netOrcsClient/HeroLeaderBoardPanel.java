package netOrcsClient;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import netOrcsShared.GameObjects;
import netOrcsShared.State;

public class HeroLeaderBoardPanel extends JPanel {
	Graphics2D g2;
	List<Integer> deadHeros;
	
	
	public HeroLeaderBoardPanel() {
		super();
//		this.setSize(50, 750);
		deadHeros = new ArrayList<>();
		this.add(new JLabel("Leaderboard"));
	}
	
	public synchronized void updateState(State s) {
		for(GameObjects hero : s.getHeroes()){
			if(!hero.isAlive() && !this.deadHeros.contains(hero.getIndex())){
				this.add(new JLabel(hero.getPlayerValue() + ": " + hero.getTime()));
				this.deadHeros.add(hero.getIndex());
				this.validate();
				this.repaint();
			}
		}
		
		
		
	}
	
}
