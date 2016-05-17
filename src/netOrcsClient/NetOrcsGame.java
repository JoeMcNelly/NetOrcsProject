/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netOrcsClient;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
    static NetOrcsPanel panel;
    //static HeroLeaderBoardPanel leaderBoard;

    NetOrcsGame(NetOrcsConnection conn) throws IOException {
        this.connection = conn;
        panel = new NetOrcsPanel();
        setTitle("Net Orcs!");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.setVisible(true);
        this.add(panel);
        //leaderBoard = new HeroLeaderBoardPanel();
        //this.add(leaderBoard,BorderLayout.EAST);
        BufferedImage myImage = ImageIO.read(new File("src/objects/backgrounds/natalie2.jpg"));
        //this.setContentPane(new BackGroundPanel(myImage));
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //connection.sendAction("" + e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {
    	connection.sendAction("start " + e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {
    	connection.sendAction("stop " + e.getKeyChar());
    }

}
