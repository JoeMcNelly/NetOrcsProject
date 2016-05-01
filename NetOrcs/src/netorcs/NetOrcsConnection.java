/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netorcs;

import java.net.Socket;

/**
 *
 * @author mcnelljd
 */
public class NetOrcsConnection {
   private String IP = "";
   private String PORT = "";
   Socket socket;
   
   public NetOrcsConnection(String ip, String port){
       this.IP=ip;
       this.PORT=port;
   }
   //Connect to remote server, return true if successful
   public boolean connect(){
       try{
       int port = Integer.parseInt(PORT);
       socket = new Socket(IP, port);
       }catch(Exception e){
           return false;
       }
       return true;
   }
   
}
