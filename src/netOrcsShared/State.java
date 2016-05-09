/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netOrcsShared;

import java.io.Serializable;

/**
 *
 * @author mcnelljd
 */
public class State implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3807319663566387642L;
	private String lastReceivedMessage = "";

    public String getLRM() {
        return this.lastReceivedMessage;
    }

    public void setLRM(String message) {
        lastReceivedMessage = message;
    }
}
