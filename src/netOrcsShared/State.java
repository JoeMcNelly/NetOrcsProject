/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netOrcsShared;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author mcnelljd
 */
public class State implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3807319663566387642L;
	private ArrayList<Orc> orcs = new ArrayList<Orc>();

    public ArrayList<Orc> getOrcs() {
        return this.orcs;
    }

    public void setOrcs(ArrayList<Orc> orcs) {
        this.orcs = orcs;
    }
    public void addOrc(Orc orc){
    	this.orcs.add(orc);
    }
    public void removeOrc(Orc orc){
    	this.orcs.remove(orc);
    }
}
