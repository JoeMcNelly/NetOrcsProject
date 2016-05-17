package netOrcsShared;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

public abstract class GameObjects implements Serializable{
	
	private boolean isAlive = true;
	protected int speed;
	protected Color color;
	protected boolean left = false;
	protected boolean right = false;
	protected boolean up = false;
	protected boolean down = false;
	protected long timeAlive;
	abstract public Point getPosition();
	abstract public void setPosition(Point position);
	abstract public int getIndex();
	abstract public void setIndex(int index);
	abstract public int size();
	abstract public Color getColor();
	abstract public void setColor(Color color);
	abstract public void setSpeed(int speed);
	abstract public int getSpeed();
	public void setTimeAlive(long time){
		this.timeAlive = time;
	}
	public long getTime(){
		return this.timeAlive;
	}
	public GameObjects(){
		speed=0;
	}
	public void kill(){
		isAlive = false;
	}
	public boolean isAlive(){
		return isAlive;
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
}
