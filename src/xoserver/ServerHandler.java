/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Geforce
 */
public class ServerHandler extends Thread  {
       
       private ServerSocket myServerSocket;
      
       
       public ServerHandler() {
           try {
               
               myServerSocket = new ServerSocket(5050);
              
       
           } catch (IOException ex) {
               Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
           } 
           while(true){
               try {
                   Socket s;
                   s = myServerSocket.accept();
                   new ClientHandler(s);
               } catch (IOException ex) {
                   Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
               }
           
           }
        
    }
  
    
    
}

class ClientHandler extends Thread {
     private  Socket s;
     private  DataInputStream dis ;
     private PrintStream ps;
     private static Vector<ClientHandler> clientsVector ; 
    public ClientHandler(Socket s) {
         
        try {
            dis = new DataInputStream(s.getInputStream ());
            ps = new PrintStream(s.getOutputStream ());
            clientsVector = new Vector<ClientHandler>();
            String msg = dis.readLine();
            System.out.println(msg);
            ClientHandler.clientsVector.add(this);
            ps.println("Data Received");
            start();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }finally
            {
                try
                {
                    ps.close();
                    dis.close();
                    s.close();
                    System.out.println("server closed");
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                } 
            }
    }
    
     @Override
    public void run(){
    while(true){
        try {
            String str ;
            str = dis.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    }
 
     void forwardRequest(){
     
     }
   
}