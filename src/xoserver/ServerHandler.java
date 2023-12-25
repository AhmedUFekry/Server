/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoserver;

import Database.DataAccessLayer;
import Model.DataOperation;
import com.google.gson.Gson;
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
public class ServerHandler{
       
    private ServerSocket myServerSocket; 
    public ServerHandler() {
        try {

            myServerSocket = new ServerSocket(5050);
            System.out.println("xoserver.ServerHandler.<init>()");

        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        } 
         // Accept client connections in a separate thread
         new Thread(() -> {
             while (true) {
                 try {
                     Socket clientSocket = myServerSocket.accept();
                     new ClientHandler(clientSocket);
                 } catch (IOException ex) {
                     Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
         }).start();
     } 
}

class ClientHandler extends Thread {
     private  Socket socket;
     private  DataInputStream dataInput ;
     private PrintStream dataOutput;
     private static Vector<ClientHandler> clientsVector ; 
    public ClientHandler(Socket s) { 
        try {
            dataInput = new DataInputStream(s.getInputStream ());
            dataOutput = new PrintStream(s.getOutputStream ());
            clientsVector = new Vector<ClientHandler>();
            System.out.println("xoserver.ClientHandler.<init>()");
         //   String msg = dataInput.readLine();
          //  System.out.println(msg);
            ClientHandler.clientsVector.add(this);
         //   dataOutput.println("Data Received");
            start();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     @Override
    public void run(){
        try {
            String msg ;
            //handling talk to the client
           // msg = dataInput.readLine();
           while((msg = dataInput.readLine()) != null){
               System.out.println(msg);
               // System.out.println("enterd");
           // if(msg != null){
                // check connection to the server at start screen
                if(msg.equals("start")){
                    dataOutput.println("connected successfully"); 
                }else{
                    handleClientOperation(msg);
                }
            } 
       }catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
               try
               {
                  if (socket != null && !socket.isClosed()) {
                       socket.close();
                       System.out.println("server closed");
                   }
                   dataOutput.close();
                   dataInput.close();
                   
               }
               catch(Exception ex)
               {
                   ex.printStackTrace();
               } 
           }
    }
     private void handleClientOperation(String msg) {
        DataOperation dataReceived = new Gson().fromJson(msg, DataOperation.class);
        if (dataReceived != null) {
            if (dataReceived.getOperation().equals("login")) {
                String responseToClient = DataAccessLayer.signInCheck(dataReceived.getPlayers().get(0).getUserName(), dataReceived.getPlayers().get(0).getPassword());
                System.out.println(dataReceived.getPlayers().get(0).getPassword());
                System.out.println(responseToClient);
                dataOutput.println(responseToClient);
            } else if (dataReceived.getOperation().equals("sign up")) {
                // Handle signup operation
                 System.out.println(dataReceived.getPlayers());
                String responseToClient = DataAccessLayer.signUpCheck(dataReceived.getPlayers().get(0));
                
               
                dataOutput.println(responseToClient);
                
            }
            // Add more operations here
        }else{
             System.out.println("Received null data from client");
        }
    }
     
    void forwardRequest(){
     
     }
   
}