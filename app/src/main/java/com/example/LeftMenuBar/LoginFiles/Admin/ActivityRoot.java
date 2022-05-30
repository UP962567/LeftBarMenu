package com.example.LeftMenuBar.LoginFiles.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.LeftMenuBar.R;
import com.google.android.material.snackbar.Snackbar;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class ActivityRoot extends AppCompatActivity implements View.OnClickListener {

    Button rootbt, rootbt2;
    EditText rootText;
    String[] firstArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);

        rootText = findViewById(R.id.rootTextReader);
        rootbt = findViewById(R.id.rootBT);
        rootbt2  = findViewById(R.id.rootBT2);
        rootbt.setOnClickListener(ActivityRoot.this);
        rootbt2.setOnClickListener(ActivityRoot.this);
    }

    public void connection() {

        String host="139.162.221.45";
        String user="root";
        String password="madehost.al";

        JSch jsch = new JSch();
        Session session = null;

        try {
            session = jsch.getSession(user, host, 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;

            InputStream stream = sftpChannel.get("/root/text.txt");
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(stream));
                String line;
                int o = 0;
                while ((line = br.readLine()) != null) {
                    o++;
                    rootText.setText(line);

                    ArrayList<String> gfg = new ArrayList<>();
                    gfg.add(line);
                    System.out.println("line " + o + " :" + line);


                }

            } catch (IOException io) {
                System.out.println("Exception occurred during reading file from SFTP server due to " + io.getMessage());
                io.getMessage();

            } catch (Exception e) {
                System.out.println("Exception occurred during reading file from SFTP server due to " + e.getMessage());
                e.getMessage();

            }

            sftpChannel.exit();
            session.disconnect();

            System.out.println("SYSTEM: We are out");

        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    public void executeSSHcommand(){
        String host="139.162.221.45";
        String user="root";
        String password="madehost.al";
        int port=22;
        try{

            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setTimeout(10000);
            session.connect();
            ChannelExec channel = (ChannelExec)session.openChannel("exec");
            channel.setCommand("mkdir /root/test/");
            channel.connect();
            channel.disconnect();
            // show success in UI with a snackbar alternatively use a toast
            Snackbar.make(this.findViewById(android.R.id.content),
                            "Success!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        catch(JSchException e){
            // show the error in the UI
            Snackbar.make(this.findViewById(android.R.id.content),
                            "Check WIFI or Server! Error : "+e.getMessage(),
                            Snackbar.LENGTH_LONG)
                    .setDuration(20000).setAction("Action", null).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == rootbt) {
            Toast.makeText(getApplicationContext(), "CONNECTING", Toast.LENGTH_SHORT).show();
            connection();
        } else if ( v == rootbt2){
            Toast.makeText(getApplicationContext(), "CONNECTING 22222222", Toast.LENGTH_SHORT).show();
            executeSSHcommand();
            System.out.println(Arrays.toString(firstArray));
        }
    }

    private class Contact {
    }
}