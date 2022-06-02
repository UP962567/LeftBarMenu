package com.example.LeftMenuBar.backcode;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.InputStream;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by MaDy on 12/04/2022 / 08:46
 * <p>
 * This was by help from of a youtuber!
 * <p>
 * Helped for image shapes, firebase, database, connection, storage
 * <p>
 * From the logic of it will work on the main project!
 **/
public class Root {

    private String Command, userID;
    private String Time;
    private String Line;
    private long ServerTime;

    public Root() {
    }

    public Root(String command, String line, long serverTime, String time, String userID) {
        Command = command;
        Line = line;
        ServerTime = serverTime;
        Time = time;
        this.userID = userID;
    }

    public String getCommand() {
        return Command;
    }

    public void setCommand(String command) {
        Command = command;
    }

    public String getLine() {
        return Line;
    }

    public void setLine(String line) {
        Line = line;
    }

    public long getServerTime() {
        return ServerTime;
    }

    public void setServerTime(long serverTime) {
        ServerTime = serverTime;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
