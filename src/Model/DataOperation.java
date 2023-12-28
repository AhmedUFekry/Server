/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.DTO.DTOPlayerData;
import java.util.List;

/**
 *
 * @author DELL
 */
public class DataOperation {
    private String operation;
    private List<DTOPlayerData> players;

    public DataOperation(String profile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setPlayers(List<DTOPlayerData> players) {
        this.players = players;
    }

    public String getOperation() {
        return operation;
    }

    public List<DTOPlayerData> getPlayers() {
        return players;
    }

    public DataOperation(String operation, List<DTOPlayerData> players) {
        this.operation = operation;
        this.players = players;
    }

    public void addPlayer(DTOPlayerData player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
