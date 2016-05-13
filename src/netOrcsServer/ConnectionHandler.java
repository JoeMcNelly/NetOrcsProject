/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netOrcsServer;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import netOrcsShared.Hero;

/**
 *
 * @author mcnelljd
 */
class ConnectionHandler implements Runnable {

    ObjectOutputStream out;
    ObjectInputStream in;
    NetOrcsServer server;
    Socket socket;
    String user;
    Hero hero;

    ConnectionHandler(NetOrcsServer server, Socket socket, String user) throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
        this.server = server;
        this.socket = socket;
        this.user = user;
        hero = new Hero();
        hero.setIndex(server.numPlayers - 1);
        hero.setPosition(new Point((int)Math.floor(750/2), (int)Math.floor(750/2)));
        this.server.state.addhero(hero);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				try {
					server.addOrc();
					server.broadcast();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}, 0, 5000);
    }

    @Override
    public void run() {
        while (true) {

            String input;
            try {
                input = (String) in.readObject();
                System.out.println("Server Recieved: " + input + " from: " + user);
                server.handleAction(this, input);
            } catch (Exception ex) {
                System.out.println("User " + user + " has disconnected.");
                try {
                    socket.close();
                    server.removeHandler(this);
                    break;
                } catch (IOException ex1) {
                }
            }

        }
    }

    public void sendState() throws IOException {
        out.writeUnshared(server.getState());
        out.flush();
        out.reset();
    }

}
