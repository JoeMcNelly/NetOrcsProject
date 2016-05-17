/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netOrcsServer;

import java.awt.Color;
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

	int port;
	List<ConnectionHandler> handlers;
	ServerSocket server;
	HashSet<String> users = new HashSet<String>();
	State state = new State();
	int numPlayers = 0;
	double chanceToSpawnOrc = 0.05;
	List<Color> heroColors = new ArrayList<>();

	// HashMap<Rectangle2D.Double, GameObjects> orcPosition = new HashMap<>();
	public NetOrcsServer() {
		this.handlers = Collections.synchronizedList(new ArrayList<ConnectionHandler>());
		addHeroColor();
	}
	private void addHeroColor() {
		this.heroColors.add(Color.BLUE);
		this.heroColors.add(Color.BLACK);
		this.heroColors.add(Color.CYAN);
		this.heroColors.add(Color.MAGENTA);
		this.heroColors.add(Color.ORANGE);
		this.heroColors.add(Color.PINK);
	}
	void start() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					moveHeros();
					addOrc();
					moveOrcs();
					broadcast();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}, 0, 50);
        while (true) {
            try {
                this.server = new ServerSocket(0);
                port = server.getLocalPort();
                System.out.println("NetOrcs server running at port: " + port);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            while (true) {
                try {
                    Socket client = this.server.accept();
                    int playerNumber = ++this.numPlayers;
                    System.out.println("Player " + playerNumber + " connected");
                    
                    ConnectionHandler handler = new ConnectionHandler(this, client, "Player " + playerNumber);
                    this.handlers.add(handler);
                    Thread runner = new Thread(handler);
                    runner.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void removeHandler(ConnectionHandler handler) {
        handlers.remove(handler);
        Hero hero = handler.hero;
        hero.kill();
        state.killHero(hero);
    }

    void broadcast() throws IOException {
    		for (ConnectionHandler handler : handlers) {
    			handler.sendState();
    		}
    }

    void handleAction(ConnectionHandler handler, String input) throws IOException {
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
			Point p = new Point(rand.nextInt(750), rand.nextInt(750));
			orc.setIndex(index);
			orc.setPosition(p);
			state.addOrc(orc);
			// this.orcPosition.put(new Rectangle2D.Double(p.getX(), p.getY(),
			// p.getX() + orc.size(), p.getY() + orc.size()), orc);
		}
	}

	private void moveOrcs() {
		Random rand = new Random();
		String direction = "";
		for (GameObjects g : state.getOrcs()) {
			if (rand.nextDouble() < 0.5) {
				if (rand.nextDouble() < 0.5) {
					direction = "w";
				} else {
					direction = "d";
				}
			} else {
				if (rand.nextDouble() < 0.5) {
					direction = "a";
				} else {
					direction = "s";
				}
			}
			tryAction(g, direction);
			// Point p = g.getPosition();
			// this.orcPosition.put(new Rectangle2D.Double(p.getX(), p.getY(),
			// p.getX() + g.size(), p.getY() + g.size()), g);
		}

	}

	public void collisionDetection() {
		for (GameObjects hero : state.getHeroes()) {
			double heroRightX = hero.getPosition().getX() + hero.size();
			double heroBottomY = hero.getPosition().getY() + hero.size();
			double heroLeftX = hero.getPosition().getX();
			double heroTopY = hero.getPosition().getY();
			for (GameObjects orc : state.getOrcs()) {

				double orcX = orc.getPosition().getX() + orc.size() / 2;
				double orcY = orc.getPosition().getY() + orc.size() / 2;

				if (heroLeftX <= orcX && heroRightX >= orcX) {
					if (heroTopY <= orcY && heroBottomY >= orcY) {
						state.killHero((Hero) hero);
					}
				}

			}

		}
	}

	private GameObjects tryAction(GameObjects obj, String input) {
		Point pos = obj.getPosition();
		int x = (int) pos.getX();
		int y = (int) pos.getY();
		switch (input) {
		case "w":
			if (y > 0)
				y -= 4;
			break;
		case "a":
			if (x > 0)
				x -= 4;
			break;
		case "s":
			if (y < 708 - obj.size())// 711-obj.size())
				y += 4;
			break;
		case "d":
			if (x < 730 - obj.size())// 733-obj.size())
				x += 4;
			break;
		}
		obj.setPosition(new Point(x, y));
		collisionDetection();
		return obj;
	}

	public State getState() {
		return this.state;
	}

}
