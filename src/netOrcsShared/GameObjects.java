package netOrcsShared;

import java.awt.Point;
import java.io.Serializable;

public abstract class GameObjects implements Serializable{
	abstract public Point getPosition();
	abstract public void setPosition(Point position);
	abstract public int getIndex();
	abstract public void setIndex(int index);
}
