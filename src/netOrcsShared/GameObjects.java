package netOrcsShared;

import java.awt.Point;
import java.io.Serializable;

public abstract class GameObjects implements Serializable{
	
	private boolean isAlive = true;
	abstract public Point getPosition();
	abstract public void setPosition(Point position);
	abstract public int getIndex();
	abstract public void setIndex(int index);
	abstract public int size();
	public void kill(){
		isAlive = false;
	}
	public boolean isAlive(){
		return isAlive;
	}
}
