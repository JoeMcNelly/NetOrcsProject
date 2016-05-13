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

    void start() {
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
    	Orc orc = new Orc();
    	Random rand = new Random();
    	int index = rand.nextInt();
    	Point p = new Point(rand.nextInt(), rand.nextInt());
    	orc.setIndex(index);
    	orc.setPosition(p);
        state.addOrc(orc);
        broadcast();
    }

    public State getState() {
        return this.state;
    }

}
