package com.example.LeftMenuBar.backcode;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.InputStream;

/**
 * Created by MaDy on 12/04/2022 / 08:46
 * <p>
 * This was by help from of a youtuber!
 * <p>
 * Helped for image shapes, firebase, database, connection, storage
 * <p>
 * From the logic of it will work on the main project!
 **/
public class RootConnection {

    public void executeRemoteShutdown(){
        String user = "ssh";
        String password = "123";
        String host = "192.168.1.4";
        int port=22;
        try{
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            Channel channel = (ChannelExec) session.openChannel("exec");
            ((ChannelExec) channel).setCommand("shutdown /s");
            channel.connect();
            try{
                Thread.sleep(1000);
            }catch(Exception ee){}
            channel.disconnect();
            session.disconnect();
        }
        catch(Exception e){}
    }

    public static class connection {

    }
}
