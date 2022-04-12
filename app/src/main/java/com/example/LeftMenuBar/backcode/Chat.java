package com.example.LeftMenuBar.backcode;

/**
 * Created by MaDy on 11/04/2022 / 23:32
 * <p>
 * This was by help from of a youtuber!
 * <p>
 * Helped for image shapes, firebase, database, connection, storage
 * <p>
 * From the logic of it will work on the main project!
 **/
public class Chat {

    String sms, status, userID;

    public Chat() {
    }

    public Chat(String sms, String status, String userID) {
        this.sms = sms;
        this.status = status;
        this.userID = userID;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
