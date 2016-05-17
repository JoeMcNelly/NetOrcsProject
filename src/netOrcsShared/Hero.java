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
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	private static final int HERO_SPEED=2;
	
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
	
	public void moving (String startStop, String direction) {
		boolean temp = false;
		if (startStop.equals("start")){
			temp = true;
		}
		
		switch(direction){
		case "w":
			up=temp;
			break;
		case "a":
			left=temp;
			break;
		case "s":
			down=temp;
			break;
		case "d":
			right=temp;
			break;
		default:
			break;
	}
	}
	
	public boolean getUp(){
		return up;
	}
	public boolean getDown(){
		return down;
	}
	public boolean getRight(){
		return right;
	}
	public boolean getLeft(){
		return left;
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
