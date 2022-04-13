package com.example.LeftMenuBar.LoginFiles.LoginMenuItem;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.LeftMenuBar.R;
import com.example.LeftMenuBar.Utils.UserLogin;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.InputStream;

public class SettingsActivityLogin extends Fragment implements View.OnClickListener {

    Button button11, button22,button33, buttonSSH;
    MediaPlayer mediaPlayer;

    private static final UserLogin U = new UserLogin();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View view = inflater.inflate(R.layout.activity_settings_login, container, false);

        button11= view.findViewById(R.id.Mbutton);
        button11.setOnClickListener(SettingsActivityLogin.this);
        button22= view.findViewById(R.id.Mbutton2);
        button22.setOnClickListener(SettingsActivityLogin.this);
        button33= view.findViewById(R.id.Mbutton3);
        button33.setOnClickListener(SettingsActivityLogin.this);
        buttonSSH= view.findViewById(R.id.MbuttonSSHConnection);
        buttonSSH.setOnClickListener(SettingsActivityLogin.this);

        mediaPlayer = mediaPlayer = MediaPlayer.create(getContext(), R.raw.shot_rel_music);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == button11) {
            if(mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(getContext(), R.raw.shot_rel_music);
            }
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopMusic();
                }
            });
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        } else if (view == button22) {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
            }
        } else if (view == button33) {
            if (mediaPlayer != null) {
                mediaPlayer.start();
                stopMusic();
            }
        } else if (view == buttonSSH) {
            Toast.makeText(getContext(), "CONNECTING", Toast.LENGTH_SHORT).show();
            connection();
        }
    }

    public static void connection() {
        String host="62.108.35.72";
        String user="root";
        String password="madehost.al";
        String command1="ls -ltr";
        String command4="ls /home/";
        String command2="sudo mkdir /home/test9";
        String command3="sudo useradd -g users -d /home/test9 -s /bin/bash -p $(echo test9 | openssl passwd -1 -stdin) test9";
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
            ((ChannelExec)channel).setCommand(command4);
            ((ChannelExec)channel).setCommand(command2);
            ((ChannelExec)channel).setCommand(command3);
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

    private void stopMusic() {
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        //stopMusic();
    }
}