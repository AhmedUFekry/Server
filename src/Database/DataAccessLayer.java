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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.ClientDriver;
/**
 *
 * @author DELL
 */
public class DataAccessLayer {
   public static String signInCheck(String userName, String password) {
       DTOPlayerData player = new DTOPlayerData();
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
                  System.out.println("Database.DataAccessLayer.signInCheck()");
                  player.setFullName(rs.getString("fullname"));
                  player.setUserName(rs.getString("username"));
                  player.setEmail(rs.getString("email"));
                  player.setPassword(rs.getString("password"));
                  player.setWinMatch(rs.getInt("win"));
                  player.setWinMatch(rs.getInt("lose"));
                  player.setWinMatch(rs.getInt("totalmatches"));
                  player.setIsOnline(rs.getBoolean("ONLINESTATUS"));
                  player.setIsAvailable(rs.getBoolean("availabilitystatus"));
                  player.setIsMale(rs.getBoolean("gender"));
                  return rs.getString("username");
              }
           
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "error";
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
        return result;
    }
}
