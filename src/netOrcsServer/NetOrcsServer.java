/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netOrcsServer;

import java.awt.Point;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import netOrcsShared.GameObjects;
import netOrcsShared.Hero;
import netOrcsShared.Orc;
import netOrcsShared.State;

/**
 *
 * @author mcnelljd
 */
class NetOrcsServer {

	int port = -1;
	List<ConnectionHandler> handlers;
	ServerSocket server;
	HashSet<String> users = new HashSet<String>();
	State state = new State();
	int numPlayers = 0;
	double chanceToSpawnOrc = 0.05;
	int angerDistance = 200;
	int spawnDistance = 180;
	List<String> heroColors = new ArrayList<>();
	int numReadyPlayers = 0;
	Timer timer = new Timer();
	long startTime;
	long timeAlive;
	long interval = 10000;
	// HashMap<Rectangle2D.Double, GameObjects> orcPosition = new HashMap<>();
	public NetOrcsServer() {
		this.handlers = Collections.synchronizedList(new ArrayList<ConnectionHandler>());
		addHeroColor();
		start();
	}
	public NetOrcsServer(int port) {
		this.handlers = Collections.synchronizedList(new ArrayList<ConnectionHandler>());
		addHeroColor();
		this.port = port;
		start();
	}

	private void addHeroColor() {
		this.heroColors.add("blue");
		this.heroColors.add("black");
		this.heroColors.add("cyan");
		this.heroColors.add("magenta");
		this.heroColors.add("orange");
		this.heroColors.add("pink");
	}

	void start() {
		timeAlive=System.currentTimeMillis();
            try {
            	if (port == -1){
            		this.server = new ServerSocket(0);
            		port = server.getLocalPort();
            	}else{
            		this.server = new ServerSocket(port);
            	}
                
                System.out.println("NetOrcs server running at port: " + port);
            } catch (Exception e) {
                //e.printStackTrace();
                return;
            }

            while (!server.isClosed()) {
                try {
                    Socket client = this.server.accept();
                    int playerNumber = ++this.numPlayers;
                    System.out.println("Player " + playerNumber + " connected");
                    ConnectionHandler handler = new ConnectionHandler(this, client, "Player " + playerNumber);
                    this.handlers.add(handler);
                    Thread runner = new Thread(handler);
                    runner.start();
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        
    }

	void startTimer(){

		startTime=System.currentTimeMillis();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					validate();
					moveHeros();
					addOrc();
					moveOrcs();
					broadcast();
					collisionDetectionAngerAndDirectionBias();
					modifySpeeds();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}, 0, 50);

	}
	
	protected void modifySpeeds(){
		long nowTime = System.currentTimeMillis();
		long elapsedTime = nowTime-startTime;
		if (elapsedTime > interval){
			startTime=nowTime;
			if (chanceToSpawnOrc < 0.50)
				chanceToSpawnOrc += 0.03;
		}
		
	}
	
    protected void validate() throws IOException {
		if(state.gameOver()){
			broadcast();
			synchronized(handlers) {
	    		for (ConnectionHandler handler : handlers) {
	    			handler.close();
	    		}
	    	}
			System.out.println("Game over, restarting server for a new game...");
			server.close();
			timer.cancel();
			new NetOrcsServer(port);
		}
		
	}
	void removeHandler(ConnectionHandler handler) {
        handlers.remove(handler);
        Hero hero = handler.hero;
        hero.kill();
        state.killHero(hero);
        numReadyPlayers--;
        numPlayers--;
    }

    void broadcast() throws IOException {
    	synchronized(handlers) {
    		for (ConnectionHandler handler : handlers) {
    			handler.sendState();
    		}
    	}
    }

    void handleAction(ConnectionHandler handler, String input) throws IOException {
    	
    	if (input.equals("ready")){
    		numReadyPlayers++;
    		System.out.println(handler.user + " has readied up! " + numReadyPlayers + "/" + numPlayers + " are ready!");
    		if (numReadyPlayers == numPlayers){
    			System.out.println("Game is starting! Avoid the orcs!");
    			startTimer();
    		}
    		return;
    	}
    	
    	Hero hero = handler.hero;
    	String[] splitInput = input.split(" ");
    	String startStop = splitInput[0];
    	String direction = splitInput[1];
    	hero.moving(startStop, direction);
        state.updateHero(hero, hero.getIndex());
        state.updateHero(handler.hero, handler.hero.getIndex());
    }
   
	void moveHeros(){
    	for(GameObjects go : state.getHeroes()) {
    		Hero h = (Hero) go;
    		if (h.getUp()){
    			tryAction(h,"w");
    		}
    		if(h.getDown()){
    			tryAction(h,"s");
    		}
    		if(h.getRight()){
    			tryAction(h,"d");
    		}
    		if(h.getLeft()){
    			tryAction(h,"a");
    		}
    	}
    }

	protected void addOrc() {
		Random rand = new Random();

		if (rand.nextDouble() < this.chanceToSpawnOrc) {
			Orc orc = new Orc();
			int index = rand.nextInt();
			double dist = -1.0;
			Point p = new Point(0, 0);
			while (dist < spawnDistance) {
				p = new Point(rand.nextInt(750), rand.nextInt(750));
				double tempDist=0;
				double minDist=750;
				for (GameObjects hero : state.getHeroes()) {
					hero = (Hero) hero;
					double xdiff = (p.getX() + orc.size() / 2) - (hero.getPosition().getX() + hero.size() / 2);
					double ydiff = (p.getY() + orc.size() / 2) - (hero.getPosition().getY() + hero.size() / 2);
					tempDist = Math.sqrt(Math.pow(xdiff, 2) + Math.pow(ydiff, 2));
					if(tempDist<minDist){
						minDist=tempDist;
					}

				}
				dist=minDist;
			}

			orc.setIndex(index);
			orc.setPosition(p);
			if (rand.nextDouble() < 0.3) {
				orc.setAngry(true);
			}
			state.addOrc(orc);
		}
	}

	private void moveOrcs() {
		Random rand = new Random();
		String direction = "";
		for (GameObjects g : state.getOrcs()) {
			Orc orc = (Orc) g;
			//angry movement
			if (orc.getAngry()) {
				if (orc.getUp()) {
					tryAction(orc, "w");
				}
				if (orc.getDown()) {
					tryAction(orc, "s");
				}
				if (orc.getRight()) {
					tryAction(orc, "d");
				}
				if (orc.getLeft()) {
					tryAction(orc, "a");
				}
				
			//random not angry
			} else {
				if (rand.nextDouble() < 0.5) {
					if (rand.nextDouble() < 0.5) {
						direction = "w";
					} else {
						direction = "d";
					}
					tryAction(g,direction);
				}
				if (rand.nextDouble() < 0.5) {
					if (rand.nextDouble() < 0.5) {
						direction = "a";
					} else {
						direction = "s";
					}
					tryAction(g,direction);
				}
			}
		}

	}

	public void collisionDetectionAngerAndDirectionBias() {
		for (GameObjects go : state.getOrcs()) {
			Orc orc = (Orc) go;
			boolean angry = false;
			double minDist = 750;
			for (GameObjects hero : state.getHeroes()) {
				double heroRightX = hero.getPosition().getX() + hero.size();
				double heroBottomY = hero.getPosition().getY() + hero.size();
				double heroLeftX = hero.getPosition().getX();
				double heroTopY = hero.getPosition().getY();

				if (collided(heroLeftX, heroRightX, heroTopY, heroBottomY, orc.getPosition().getX(),
						orc.getPosition().getY())
						|| collided(heroLeftX, heroRightX, heroTopY, heroBottomY, orc.getPosition().getX() + orc.size(),
								orc.getPosition().getY())
						|| collided(heroLeftX, heroRightX, heroTopY, heroBottomY, orc.getPosition().getX(),
								orc.getPosition().getY() + orc.size())
						|| collided(heroLeftX, heroRightX, heroTopY, heroBottomY, orc.getPosition().getX() + orc.size(),
								orc.getPosition().getY() + orc.size())) {
					long timeDied = (System.currentTimeMillis() - timeAlive) / 1000;
					hero.setTimeAlive(timeDied);
					state.killHero((Hero) hero);
				}
				double xdiff = (orc.getPosition().getX() + orc.size() / 2)
						- (hero.getPosition().getX() + hero.size() / 2);
				double ydiff = (orc.getPosition().getY() + orc.size() / 2)
						- (hero.getPosition().getY() + hero.size() / 2);
				double dist = Math.sqrt(Math.pow(xdiff, 2) + Math.pow(ydiff, 2));
				boolean close = dist < this.angerDistance && hero.isAlive();

				if (close && dist < minDist) {
					minDist = dist;
					if (xdiff > 0) {
						//bias left
						orc.moving("start", "a");
						orc.moving("stop", "d");
					} else {
						// bias right
						orc.moving("stop", "a");
						orc.moving("start", "d");
					}
					if (ydiff > 0) {
						// bias up
						orc.moving("start", "w");
						orc.moving("stop", "s");
					} else {
						// bias down
						orc.moving("start", "s");
						orc.moving("stop", "w");
					}
				}
				angry = angry || close;
			}
			orc.setAngry(angry);

		}
	}

	public boolean collided(double heroLeftX, double heroRightX, double heroTopY, double heroBottomY, double orcX,
			double orcY) {
		if (heroLeftX <= orcX && heroRightX >= orcX) {
			if (heroTopY <= orcY && heroBottomY >= orcY) {
				return true;
			}
		}
		return false;
	}

	private GameObjects tryAction(GameObjects obj, String input) {
		Point pos = obj.getPosition();
		int x = (int) pos.getX();
		int y = (int) pos.getY();
		switch (input) {
		case "w":
			y -= obj.getSpeed();
			if (y < 0)
				y = 0;
			break;
		case "a":
			x -= obj.getSpeed();
			if (x < 0)
				x = 0;
			break;
		case "s":
			y += obj.getSpeed();
			if (y > 711 - obj.size())
				y = 711 - obj.size();
			break;
		case "d":
			x += obj.getSpeed();
			if (x > 733 - obj.size())
				x = 733 - obj.size();
			break;
		}
		obj.setPosition(new Point(x, y));
		return obj;
	}

	public State getState() {
		return this.state;
	}

}
