/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netorcs;

import java.io.Serializable;

/**
 *
 * @author mcnelljd
 */
public class State implements Serializable {

    private String lastReceivedMessage = "";

    public String getLRM() {
        return this.lastReceivedMessage;
    }

    public void setLRM(String message) {
        lastReceivedMessage = message;
    }
}
