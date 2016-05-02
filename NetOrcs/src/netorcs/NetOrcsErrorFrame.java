/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netorcs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author mcnelljd
 */
public class NetOrcsErrorFrame extends JFrame {

    public NetOrcsErrorFrame() {
        setTitle("Error On Connect");
        setSize(400, 100);
        JButton button = new JButton("OK");
        JLabel lab = new JLabel("Could not conncet to the server, check input and firewall settings");
        add(lab, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);
        setVisible(true);
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
    }

}
