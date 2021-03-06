/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netOrcsClient;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author mcnelljd
 */
public class NetOrcsStartFrame extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NetOrcsStartFrame(String ip2, String port) {
		setTitle("Net Orcs");
        setSize(250, 100);
        JButton button = new JButton("Connect");
        JPanel pan1 = new JPanel();
        JPanel pan2 = new JPanel();
        JTextField ip = new JTextField(9);
        JTextField prt = new JTextField(5);
        ip.setText(ip2);
        prt.setText(port);
        JLabel iplab = new JLabel("IP:");
        JLabel prtlab = new JLabel("Port:");
        pan1.add(iplab);
        pan1.add(ip);
        pan2.add(prtlab);
        pan2.add(prt);
        add(pan1, BorderLayout.WEST);
        add(pan2, BorderLayout.EAST);
        add(button, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        ActionListener start = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                NetOrcsConnection conn = new NetOrcsConnection(ip.getText().trim(), prt.getText().trim());
                boolean success = conn.connect();
                if (success) {
                    setVisible(false);
                    dispose();
                    try {
						new NetOrcsGame(conn);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                } else {
                    new NetOrcsErrorFrame();
                }
            }
        };

        button.addActionListener(start);
	}
	
	public NetOrcsStartFrame() {
       new NetOrcsStartFrame("", "");
    }

	
}
