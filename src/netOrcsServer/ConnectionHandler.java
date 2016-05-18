/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netOrcsServer;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.net.Socket;

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

    ConnectionHandler(NetOrcsServer server, Socket socket, String user) throws IOException, NoSuchFieldException, SecurityException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
        this.server = server;
        this.socket = socket;
        this.user = user;
        int index = server.numPlayers - 1;
        hero = new Hero();
        hero.setIndex(index);
        hero.setPosition(new Point((int)Math.floor(750/2), (int)Math.floor(750/2)));
        Field field = Class.forName("java.awt.Color").getField(server.heroColors.get(index  % server.heroColors.size()));
        Color color = (Color)field.get(null);
        hero.setColor(color);
        hero.setPlayerValue(server.heroColors.get(index  % server.heroColors.size()));
        this.server.state.addhero(hero);
        
    }

    @Override
    public void run() {
        while (true) {

            String input;
            try {
                input = (String) in.readObject();
                server.handleAction(this, input);
            } catch (Exception ex) {
                System.out.println(user + " has disconnected.");
                //ex.printStackTrace();
                try {
                    socket.close();
                    server.removeHandler(this);
                    break;
                } catch (IOException ex1) {
                }
            }

        }
    }

    public void sendState() {
        try {
			out.writeUnshared(server.getState());
			out.flush();
	        out.reset();
		} catch (IOException e){
		}
        
    }

	public void close() {
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
