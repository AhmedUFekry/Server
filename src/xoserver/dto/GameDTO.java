/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoserver.dto;

import java.util.Date;
import java.util.List;

/**
 *
 * @author DELL
 */
public class GameDTO{
    private String playerWhoRecord;
    private int gameId;
    private String player1Name; 
    private String player2Name;
    private Date date;
    private String winnerName;

    public GameDTO(String playerWhoRecord, int gameId, String player1Name, String player2Name, Date date, String winnerName, List<MoveDTO> moves) {
        this.playerWhoRecord = playerWhoRecord;
        this.gameId = gameId;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.date = date;
        this.winnerName = winnerName;
    }


    public GameDTO() {
    }

    public String getPlayerWhoRecord() {
        return playerWhoRecord;
    }

    public int getGameId() {
        return gameId;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public Date getDate() {
        return date;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setPlayerWhoRecord(String playerWhoRecord) {
        this.playerWhoRecord = playerWhoRecord;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }
}
