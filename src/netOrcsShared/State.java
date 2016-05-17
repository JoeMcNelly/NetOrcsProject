/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netOrcsShared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author mcnelljd
 */
public class State implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3807319663566387642L;
	private List<GameObjects> orcs = new ArrayList<>();
	private GameObjects lastHero;
	private List<GameObjects> heroes;
	public State() {

	    
		 heroes = Collections.synchronizedList(new ArrayList<GameObjects>());
	}
    public synchronized List<GameObjects> getOrcs() {
        return this.orcs;
    }

    public synchronized void setOrcs(ArrayList<GameObjects> orcs) {
        this.orcs = orcs;
    }
    public synchronized void addOrc(Orc orc){
    	this.orcs.add(orc);
    }
    public synchronized void removeOrc(Orc orc){
    	this.orcs.remove(orc);
    }
    public synchronized void removeAllOrcs(){
    	orcs = new ArrayList<>();
    }

    public synchronized List<GameObjects> getHeroes() {
        return this.heroes;
    }

    public synchronized void setHeroes(ArrayList<GameObjects> heroes) {
        this.heroes = heroes;
    }
    public synchronized void addhero(Hero hero){
    	this.heroes.add(hero);
    }
    public synchronized void killHero(Hero hero){
    	hero.kill();
    	this.lastHero = hero;
    	this.heroes.set(hero.getIndex(), hero);
    }

    public synchronized void updateHero(Hero hero, int index){
    	this.heroes.set(index, hero);
    }
    
    public synchronized boolean gameOver(){
    	int trueVals = 0;
    	for(GameObjects hero : heroes){
    		if(!hero.isAlive()){
    			trueVals++;
    		}
    	}
    	if(trueVals == heroes.size()){
    		return true;
    	}else {
    		return false;
    	}
    }
    
    public synchronized GameObjects getLastHero(){
    	return lastHero;
    }
    
}
