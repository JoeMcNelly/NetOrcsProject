package netOrcsShared;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.sql.Time;

public abstract class GameObjects implements Serializable{
	
	private boolean isAlive = true;
	int speed;
	Color color;
	private Time timeAlive;
	abstract public Point getPosition();
	abstract public void setPosition(Point position);
	abstract public int getIndex();
	abstract public void setIndex(int index);
	abstract public int size();
	abstract public Color getColor();
	abstract public void setColor(Color color);
	abstract public void setSpeed(int speed);
	abstract public int getSpeed();
	public void setTimeAlive(Time time){
		this.timeAlive = time;
	}
	public Time getTime(){
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
}
