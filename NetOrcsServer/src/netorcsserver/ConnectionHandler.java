/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netorcsserver;

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
class ConnectionHandler implements Runnable{
    ObjectOutputStream out;
    ObjectInputStream in;
    NetOrcsServer server;
    Socket socket;
    ConnectionHandler(NetOrcsServer server, Socket socket) throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
        this.server=server;
        this.socket=socket;
    }

    @Override
    public void run() {
        while (true){
            
                System.out.println("Waiting to receive...");
                String input;
            try {
                input = (String)in.readObject();
                System.out.println("Server Recieved: "+input);
                out.writeObject("XXX_"+input + "_XXX");
            } catch (Exception ex) {
                System.out.println("Connection Error, closing connection to client");
                    try {
                        socket.close();
                        server.removeHandler(this);
                    } catch (IOException ex1) {}
            } 
                
             
        }
    }
}
    
    

