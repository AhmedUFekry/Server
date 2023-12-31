/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Model.DTO.DTOPlayerData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLNonTransientConnectionException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.apache.derby.jdbc.ClientDriver;
/**
 *
 * @author DELL
 */
public class DataAccessLayer {
   public static String signInCheck(String userName, String password) {
       //DTOPlayerData player = new DTOPlayerData();
            String pw; 
            try {
            DriverManager.registerDriver(new ClientDriver()); //when error occour throw it and close
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/XODATABASE", "root", "root");
            PreparedStatement stmt = con.prepareStatement("select * from PLAYER where username = ? and password = ?");
            stmt.setString(1, userName);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                  int updatedResult = updateStatus(userName,true);
                  int UpdateAvilability = updateAvilability(userName, true);
                  return rs.getString("username");
              }
           stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
            handleDatabaseError(ex);
        }
        return "error";
    }
    
   
    public static String signUpCheck(DTOPlayerData player) {
      // DTOPlayerData player = new DTOPlayerData();
            int result;
            try {
            DriverManager.registerDriver(new ClientDriver()); //when error occour throw it and close
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/xodatabase", "root", "root");
            PreparedStatement stmt= con.prepareStatement ( "INSERT INTO PLAYER (USERNAME,FULLNAME,EMAIL,PASSWORD,ONLINESTATUS,AVAILABILITYSTATUS,GENDER) VALUES (?,?,?,?,?,?,?)");
            stmt.setString(1,player.getUserName().trim());
            stmt.setString(2,player.getFullName().trim());
            stmt.setString(3,player.getEmail().trim());
            stmt.setString(4,player.getPassword().trim());
            stmt.setBoolean(5,true);
            stmt.setBoolean(6,true);
            stmt.setBoolean(7,player.isIsMale());
           
            result  =stmt.executeUpdate();
            stmt.close();
            con.close();
            System.out.println(result);
            if(result>0)
            {
                return player.getUserName().trim();
            }
        }catch(SQLIntegrityConstraintViolationException e){
            
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
            handleDatabaseError(ex);
        }
        return "error";
    }
     public static DTOPlayerData profileCheck(String userName) {
       DTOPlayerData player = new DTOPlayerData();
            String pw; 
            try {
            DriverManager.registerDriver(new ClientDriver()); //when error occour throw it and close
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/xodatabase", "root", "root");
            PreparedStatement stmt = con.prepareStatement("select * from PLAYER where username = ? ");
            stmt.setString(1, userName);
           // stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                 // int updatedResult = updateStatus(userName,true);
                  System.out.println("Database.DataAccessLayer.profileCheck()");
                  player.setFullName(rs.getString("fullname").trim());
                  player.setUserName(rs.getString("username").trim());
                  player.setEmail(rs.getString("email").trim());
                  player.setPassword(rs.getString("password").trim());
                  player.setTotalMatch(rs.getInt("totalmatches"));
                  player.setWinMatch(rs.getInt("win"));
                  player.setLoseMAtch(rs.getInt("lose"));
                  player.setIsOnline(rs.getBoolean("ONLINESTATUS"));
                  player.setIsAvailable(rs.getBoolean("availabilitystatus"));
                  player.setIsMale(rs.getBoolean("gender"));
                  System.out.println("Dreturn "+ player.getFullName());
                  return player;
              }
           stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
            handleDatabaseError(ex);
        }
            
       System.out.println("return null");
  
        return null;
    }
    public static int updateStatus(String userName,Boolean status) throws SQLException{
        int result;
       //1- load & Register the driver
        DriverManager.registerDriver(new ClientDriver()); //when error occour throw it and close
        //2-connection
       Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/XODATABASE", "root", "root"); //when error occour throw it and close
        //3- Queries
        PreparedStatement stmt = con.prepareStatement("update PLAYER  set onlinestatus = ? where username = ?");
        stmt.setBoolean(1, status);
        stmt.setString(2, userName);
        result = stmt.executeUpdate();
        
        if(result>0){
            System.out.println("Online status updated successfully.");
        } else {
            System.out.println("No rows updated. Username not found.");
        }
        stmt.close();
        con.close();
        return result;
    }
    
    public static List<DTOPlayerData> availableList() {
    List<DTOPlayerData> players = new ArrayList<>();

    try {
        DriverManager.registerDriver(new ClientDriver());
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/XODATABASE", "root", "root");
             PreparedStatement stmt = con.prepareStatement("select * from PLAYER where ONLINESTATUS = true AND AVAILABILITYSTATUS = true")) {
                //and isAvailable = true
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DTOPlayerData player = new DTOPlayerData();
                    player.setFullName(rs.getString("fullname").trim());
                    player.setUserName(rs.getString("username").trim());
                    player.setEmail(rs.getString("email").trim());
                    player.setPassword(rs.getString("password").trim());
                    player.setWinMatch(rs.getInt("win"));
                    player.setLoseMAtch(rs.getInt("lose"));
                    player.setTotalMatch(rs.getInt("totalmatches"));
                    player.setIsOnline(rs.getBoolean("ONLINESTATUS"));
                    player.setIsAvailable(rs.getBoolean("availabilitystatus"));
                    player.setIsMale(rs.getBoolean("gender"));
                   // System.out.println("انا جوه الداتابيز");
                    players.add(player);
                }
            }
        }
    } catch (SQLException ex) {
        //handle sql exception
        ex.printStackTrace();
    }

    return players;
}
    public static List<DTOPlayerData> onlineUsers() {
    List<DTOPlayerData> players = new ArrayList<>();

    try {
        DriverManager.registerDriver(new ClientDriver());
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/XODATABASE", "root", "root");
             PreparedStatement stmt = con.prepareStatement("select * from PLAYER where ONLINESTATUS = true")) {
                //and isAvailable = true
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DTOPlayerData player = new DTOPlayerData();
                    player.setFullName(rs.getString("fullname").trim());
                    player.setUserName(rs.getString("username").trim());
                    player.setEmail(rs.getString("email").trim());
                    player.setPassword(rs.getString("password").trim());
                    player.setWinMatch(rs.getInt("win"));
                    player.setLoseMAtch(rs.getInt("lose"));
                    player.setTotalMatch(rs.getInt("totalmatches"));
                    player.setIsOnline(rs.getBoolean("ONLINESTATUS"));
                    player.setIsAvailable(rs.getBoolean("availabilitystatus"));
                    player.setIsMale(rs.getBoolean("gender"));
                  //  System.out.println("انا جوه الداتابيز");
                    players.add(player);
                }
            }
        }
    } catch (SQLException ex) {
        //handle sql exception
        ex.printStackTrace();
        handleDatabaseError(ex);
    }

    return players;
}

   public static List<DTOPlayerData> offlineUsers() {

    List<DTOPlayerData> players = new ArrayList<>();

    try {
        DriverManager.registerDriver(new ClientDriver());
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/XODATABASE", "root", "root");
             PreparedStatement stmt = con.prepareStatement("select * from PLAYER where ONLINESTATUS = false")) {
                //and isAvailable = true
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DTOPlayerData player = new DTOPlayerData();
                    player.setFullName(rs.getString("fullname"));
                    player.setUserName(rs.getString("username"));
                    player.setEmail(rs.getString("email"));
                    player.setPassword(rs.getString("password"));
                    player.setWinMatch(rs.getInt("win"));
                    player.setLoseMAtch(rs.getInt("lose"));
                    player.setTotalMatch(rs.getInt("totalmatches"));
                    player.setIsOnline(rs.getBoolean("ONLINESTATUS"));
                    player.setIsAvailable(rs.getBoolean("availabilitystatus"));
                    player.setIsMale(rs.getBoolean("gender"));
                  //  System.out.println("انا جوه الداتابيز");
                    players.add(player);
                }
            }
        }
    } catch (SQLException ex) {
        //handle sql exception
        ex.printStackTrace();
        handleDatabaseError(ex);
    }

    return players;
}

  public static List<DTOPlayerData> inGameUsers() {

    List<DTOPlayerData> players = new ArrayList<>();

    try {
        DriverManager.registerDriver(new ClientDriver());
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/XODATABASE", "root", "root");
             PreparedStatement stmt = con.prepareStatement("select * from PLAYER where ONLINESTATUS = true and AVAILABILITYSTATUS = false")) {
                //and isAvailable = true
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DTOPlayerData player = new DTOPlayerData();
                    player.setFullName(rs.getString("fullname"));
                    player.setUserName(rs.getString("username"));
                    player.setEmail(rs.getString("email"));
                    player.setPassword(rs.getString("password"));
                    player.setWinMatch(rs.getInt("win"));
                    player.setLoseMAtch(rs.getInt("lose"));
                    player.setTotalMatch(rs.getInt("totalmatches"));
                    player.setIsOnline(rs.getBoolean("ONLINESTATUS"));
                    player.setIsAvailable(rs.getBoolean("availabilitystatus"));
                    player.setIsMale(rs.getBoolean("gender"));
                  //  System.out.println("انا جوه الداتابيز");
                    players.add(player);
                }
            }
        }
    } catch (SQLException ex) {
        //handle sql exception
        ex.printStackTrace();
        handleDatabaseError(ex);
    }

    return players;
}

     public static int updateAvilability(String userName,Boolean status) throws SQLException{
        int result;
       //1- load & Register the driver
        DriverManager.registerDriver(new ClientDriver()); //when error occour throw it and close
        //2-connection
       Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/XODATABASE", "root", "root"); //when error occour throw it and close
        //3- Queries
        PreparedStatement stmt = con.prepareStatement("update PLAYER  set AVAILABILITYSTATUS = ? where username = ?");
        stmt.setBoolean(1, status);
        stmt.setString(2, userName);
        result = stmt.executeUpdate();
        
        if(result>0){
            System.out.println("avalabile status updated successfully.");
        } else {
            System.out.println("No rows updated. Username not found.");
        }
        stmt.close();
        con.close();
        return result;
    }
      private static void handleDatabaseError(SQLException ex) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("An error occurred while accessing the database.");

            if (ex instanceof SQLNonTransientConnectionException) {
                alert.setContentText("Could not connect to the database. Please check your network connection and try again.");
            }else if(ex instanceof SQLIntegrityConstraintViolationException){
                alert.setContentText(ex.getMessage());
            } else {
                alert.setContentText("An unexpected database error occurred. Please contact support.");
            }

            alert.show();
        });
    }
     
     public static String logOut(DTOPlayerData player){
       try {
           int result;
           
           //1- load & Register the driver
           DriverManager.registerDriver(new ClientDriver()); //when error occour throw it and close
           //2-connection
           Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/XODATABASE", "root", "root"); //when error occour throw it and close
           //3- Queries
           PreparedStatement stmt = con.prepareStatement("update PLAYER set AVAILABILITYSTATUS = false , ONLINESTATUS = false where username = ?");
           stmt.setString(1, player.getUserName().trim());
           result = stmt.executeUpdate();
           
           stmt.close();
           con.close();
           if(result>0){
               System.out.println("log out updated successfully.");
               return "logout";
           } else {
               System.out.println("No rows updated. Username not found.");
               
           }
       } catch (SQLException ex) {
           Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
           handleDatabaseError(ex);
       }
       return "error";
    }    
}
