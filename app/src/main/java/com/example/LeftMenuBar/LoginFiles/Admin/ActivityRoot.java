package com.example.LeftMenuBar.LoginFiles.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.LeftMenuBar.LoginFiles.LoginMenuItem.SettingsActivityLogin;
import com.example.LeftMenuBar.R;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.InputStream;

public class ActivityRoot extends AppCompatActivity implements View.OnClickListener {

    Button rootbt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);

        rootbt = findViewById(R.id.rootBT);
        rootbt.setOnClickListener(ActivityRoot.this);
    }

    public static void connection() {
        String host="139.162.221.45";
        String user="root";
        String password="madehost.al";
        String command1="ls -ltr";
        try{




            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            Session session=jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command1);
            channel.setInputStream(null);
            ((ChannelExec)channel).setErrStream(System.err);
            InputStream in=channel.getInputStream();
            channel.connect();
            byte[] tmp=new byte[1024];
            while(true){
                while(in.available()>0){
                    int i=in.read(tmp, 0, 1024);
                    if(i<0)break;
                    System.out.print(new String(tmp, 0, i));
                }
                if(channel.isClosed()){
                    System.out.println("exit-status: "+channel.getExitStatus());
                    break;
                }
                try{Thread.sleep(1000);} catch(Exception ee){} }
            channel.disconnect();
            session.disconnect();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == rootbt) {
            Toast.makeText(getApplicationContext(), "CONNECTING", Toast.LENGTH_SHORT).show();
            connection();
        }
    }
}