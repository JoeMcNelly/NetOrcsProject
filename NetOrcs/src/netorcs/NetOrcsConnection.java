/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netorcs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    State state;

    public NetOrcsConnection(String ip, String port) {
        this.IP = ip;
        this.PORT = port;
        state = new State();
    }

    //Connect to remote server, return true if successful
    public boolean connect() {
        try {
            int port = Integer.parseInt(PORT);
            socket = new Socket(IP, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            listen();
        } catch (Exception e) {
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
                while (true) {
                    System.out.println("Ready to receive...");
                    try {
                        Object o = in.readObject();
                        state = (State) o;
                        System.out.println("CurrentLMR: " + state.getLRM());
                    } catch (Exception e) {
                        System.err.println("Data received in unknown format");
                    }
                }
            }
        });
        t.start();

    }

}
