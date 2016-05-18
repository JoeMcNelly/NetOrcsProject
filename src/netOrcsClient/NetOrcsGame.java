/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netOrcsClient;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

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
    static NetOrcsPanel panel;
    //static HeroLeaderBoardPanel leaderBoard;

    NetOrcsGame(NetOrcsConnection conn) throws IOException {
        this.connection = conn;
        panel = new NetOrcsPanel(this);
        setTitle("Net Orcs!");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.setVisible(true);
        this.add(panel);
        new NetOrcsReadyFrame(this);
        //BufferedImage myImage = ImageIO.read(new File("src/objects/backgrounds/natalie2.jpg"));

        //leaderBoard = new HeroLeaderBoardPanel();
        //this.add(leaderBoard,BorderLayout.EAST);

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

	public void close() throws IOException{
		connection.close();
		setVisible(false);
		dispose();
	}

	public void fireAction(String action) {
		connection.sendAction(action);
	}
	public String getIP(){
		return connection.IP;
	}
	public String getPort(){
		return connection.PORT;
	}

}
