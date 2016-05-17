package netOrcsShared;

import java.awt.Color;
import java.awt.Point;

public class Orc extends GameObjects{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8670228112997194074L;
	private Point position;
	private int index;
	public static final int SIZE = 10;
	private boolean angry=false;
	private static final int NORMAL_ORC_SPEED=2;
	private static final int ANGRY_ORC_SPEED=10;
	public Orc(){
		super();
		this.speed=NORMAL_ORC_SPEED;
		this.color=Color.GREEN;
	}
	
	public Point getPosition() {
		return position;
	}
	public void setPosition(Point position) {
		this.position = position;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public int size() {
		return SIZE;
	}
	@Override
	public Color getColor() {
		
		return color;
	}
	@Override
	public void setColor(Color color) {
		this.color = color;
		
	}
	@Override
	public void setSpeed(int speed) {
		this.speed=speed;
	}
	@Override
	public int getSpeed() {
		return this.speed;
	}
	
	public boolean getAngry() {
		return this.angry;
	}
	
	public void setAngry(boolean angry) {
		this.angry=angry;
		if(this.angry){
			this.color=Color.RED;
			this.speed=ANGRY_ORC_SPEED;
		}else {
			this.color=Color.GREEN;
			this.speed=NORMAL_ORC_SPEED;
		}
	}
}
