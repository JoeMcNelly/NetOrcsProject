/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netOrcsClient;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

/**
 *
 * @author mcnelljd
 */
public class NetOrcsGame extends JFrame implements KeyListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 750;
    private static final int HEIGHT = 750;
    private NetOrcsConnection connection;

    NetOrcsGame(NetOrcsConnection conn) {
        this.connection = conn;
        setTitle("Net Orcs!");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        connection.sendAction("" + e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
