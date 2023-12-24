/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DTO;

/**
 *
 * @author DELL
 */
public class MoveDTO {
    private int moveID;
    private int gameID;
    private String userName;
    private char symbol;
    private int moveorder;
    private int positionX;  //refer to column
    private int positionY; //refer to row

    public MoveDTO(int moveID, int gameID, String userName, char symbol, int moveorder, int positionX, int positionY) {
        this.moveID = moveID;
        this.gameID = gameID;
        this.userName = userName;
        this.symbol = symbol;
        this.moveorder = moveorder; // who play first ordered by played frist
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public MoveDTO() {
    }

    public int getMoveID() {
        return moveID;
    }

    public int getGameID() {
        return gameID;
    }

    public String getUserName() {
        return userName;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getMoveorder() {
        return moveorder;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setMoveID(int moveID) {
        this.moveID = moveID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public void setMoveorder(int moveorder) {
        this.moveorder = moveorder;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
    
}
