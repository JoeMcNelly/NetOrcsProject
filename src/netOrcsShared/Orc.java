package netOrcsShared;

import java.awt.Point;
import java.io.Serializable;

public class Orc extends GameObjects{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8670228112997194074L;
	private Point position;
	private int index;
	public static final int SIZE = 10;
	
	public Orc(){
		
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
}
