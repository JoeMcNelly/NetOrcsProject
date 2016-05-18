package netOrcsShared;

import java.awt.Color;
import java.awt.Point;

public class Hero extends GameObjects{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1463577444691292450L;
	private int index;
	private Point position;
	public static final int SIZE = 10;
	private static final int HERO_SPEED=4;
	
	public Hero(){
		super();
		this.speed=HERO_SPEED;
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

	@Override
	public int size() {
		return SIZE;
	}
	

	@Override
	public Color getColor() {
		return this.color;
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

	
}
