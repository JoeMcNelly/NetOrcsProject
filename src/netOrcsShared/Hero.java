package netOrcsShared;

import java.awt.Point;

public class Hero extends GameObjects{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1463577444691292450L;
	private int index;
	private Point position;
	public static final int SIZE = 10;
	
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

	@Override
	public int size() {
		return SIZE;
	}
}
