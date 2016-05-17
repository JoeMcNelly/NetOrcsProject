/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netOrcsClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import netOrcsShared.State;

/**
 *
 * @author mcnelljd
 */
public class NetOrcsConnection {

    private String IP = "";
    private String PORT = "";
    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;


    public NetOrcsConnection(String ip, String port) {
        this.IP = ip;
        this.PORT = port;
       
        
    }

    //Connect to remote server, return true if successful
    public boolean connect() {
        try {
            int port = Integer.parseInt(PORT);
            socket = new Socket(InetAddress.getByName(IP), port);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            listen();
        } catch (Exception e) {
        	e.printStackTrace();
            return false;
        }
        return true;
    }

    public void sendAction(String action) {
        try {
            out.writeObject(action);
            out.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void listen() {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
            	State state;
                while (true) {
                    try {
                        Object o = in.readObject();
                        state = (State) o;
                        NetOrcsGame.panel.updateState(state);
                        if(state.gameOver()){
                        	 break;
                        }
                    } catch (Exception e) {
                    	
                        System.err.println("Data received in unknown format");
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();

    }

}
