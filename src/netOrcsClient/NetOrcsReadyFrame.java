package netOrcsClient;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

public class NetOrcsReadyFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	JButton readyButton = new JButton("Ready Up!");
	NetOrcsGame game;
	public NetOrcsReadyFrame(NetOrcsGame nOG) {
		game=nOG;
        setTitle("Ready up!");
        setSize(200, 100);
        
        JButton quitButton = new JButton("Quit Game");
        add(readyButton, BorderLayout.WEST);
        add(quitButton, BorderLayout.EAST);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        readyButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                changeReadiness();
            }
        });
        quitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					stopGame();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
    }
	public void changeReadiness(){
			game.fireAction("ready");
			setVisible(false);
			dispose();
		
	}
	public void stopGame() throws IOException{
		game.close();
		System.exit(0);
	}
	
}
