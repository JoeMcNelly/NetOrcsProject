package netOrcsShared;

import java.awt.Point;

public class Hero extends GameObjects{

	private int index;
	private Point position;
	
	public Hero(){
		
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Point getPosition() {
		return position;
	}
	public void setPosition(Point position) {
		this.position = position;
	}
}
