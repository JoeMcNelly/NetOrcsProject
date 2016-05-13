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
	List<ConnectionHandler> handlers = new ArrayList<ConnectionHandler>();
	ServerSocket server;
	HashSet<String> users = new HashSet<String>();
	State state = new State();
	int numPlayers = 0;
	double chanceToSpawnOrc = 0.05;

	void start() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					addOrc();
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
                    System.out.println("New Client Connected");
                    int playerNumber = ++this.numPlayers;
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
    }

    void broadcast() throws IOException {
        for (ConnectionHandler handler : handlers) {
            handler.sendState();
        }
    }

    void handleAction(ConnectionHandler handler, String input) throws IOException {
    	Hero hero = handler.hero;
        hero = (Hero) tryAction(hero, input);
        state.updateHero(hero, hero.getIndex());
        state.updateHero(handler.hero, handler.hero.getIndex());
        //broadcast();
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
		}
	}
    private GameObjects tryAction(GameObjects obj, String input) {
    	Point pos = obj.getPosition();
    	int x = (int)pos.getX();
    	int y = (int)pos.getY();
		switch(input){
			case "w":
				if (y > 0)
					y--;
				break;
			case "a":
				if (x > 0)
				x--;
				break;
			case "s":
				if (y < 750-obj.size())
					y++;
				break;
			case "d":
				if (x < 750-obj.size())
					x++;
				break;
		}
		obj.setPosition(new Point(x,y));
		return obj;
	}

	public State getState() {
		return this.state;
	}

}
