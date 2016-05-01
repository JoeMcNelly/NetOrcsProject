/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netorcsserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author mcnelljd
 */
class NetOrcsServer {
    int port;
    List<ConnectionHandler> handlers;
    ServerSocket server;
    HashSet<String> users = new HashSet<String>();

    void start() {
        while(true) {
			try {
				this.server = new ServerSocket(0);
                                port=server.getLocalPort();
				System.out.println("NetOrcs server running at port: " + port);
			}
			catch(Exception e) {
				e.printStackTrace();
				return;
			}
			
			while(true) {
				try {
					Socket client = this.server.accept();

					ConnectionHandler handler = new ConnectionHandler(this, client);
					this.handlers.add(handler);

					Thread runner = new Thread(handler);
					runner.start();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
    }
    
}
