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
public class DTOPlayerData {

 
    private String userName;
    private String fullName;
    private String email;
    private String password;
    private int winMatch;
    private int loseMAtch;
    private int totalMatch;
    private boolean isOnline;
    private boolean isAvailable;
    private boolean isMale; // false = female & true = male 
    
    public DTOPlayerData() {
    }

    public DTOPlayerData(String userName, String fullName, String email, String password, int winMatch, int loseMAtch, int totalMatch, boolean isOnline, boolean isAvailable, boolean isMale) {
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.winMatch = winMatch;
        this.loseMAtch = loseMAtch;
        this.totalMatch = totalMatch;
        this.isOnline = isOnline;
        this.isAvailable = isAvailable;
        this.isMale = isMale;
    }
    
       public String getUserName() {
        return userName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getWinMatch() {
        return winMatch;
    }

    public int getLoseMAtch() {
        return loseMAtch;
    }

    public int getTotalMatch() {
        return totalMatch;
    }

    public boolean isIsOnline() {
        return isOnline;
    }

    public boolean isIsAvailable() {
        return isAvailable;
    }

    public boolean isIsMale() {
        return isMale;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWinMatch(int winMatch) {
        this.winMatch = winMatch;
    }

    public void setLoseMAtch(int loseMAtch) {
        this.loseMAtch = loseMAtch;
    }

    public void setTotalMatch(int totalMatch) {
        this.totalMatch = totalMatch;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void setIsMale(boolean isMale) {
        this.isMale = isMale;
    }
};

