package com.example.LeftMenuBar.LoginFiles.Admin;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LeftMenuBar.LoginFiles.LoginMenuItem.ZzChatMyViewHolder;
import com.example.LeftMenuBar.LoginFiles.LoginMenuItem.ZzMyViewHolder;
import com.example.LeftMenuBar.LoginFiles.User;
import com.example.LeftMenuBar.R;
import com.example.LeftMenuBar.backcode.Chat;
import com.example.LeftMenuBar.backcode.Posts;
import com.example.LeftMenuBar.backcode.Root;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class ActivityRoot extends AppCompatActivity implements View.OnClickListener {

    static List<String> ss = new ArrayList<>();

    FirebaseRecyclerOptions<Root> options;
    FirebaseRecyclerAdapter<Root, ZzRootViewHolder> adapter;

    DatabaseReference mRef, mRootRef;
    FirebaseUser mUser;
    FirebaseAuth mAuth;

    RecyclerView rootRecyclerView;
    Button rootbt, rootbt2, rootbt3;
    EditText rootText, sendCommand;

    private static Session session;
    private static ChannelShell channel;
    private static String host="139.162.221.45";
    private static String user="root";
    private static String password="madehost.al";
    int port=22;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);

        rootRecyclerView = findViewById(R.id.recycleViewReadRoot);
        rootRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        rootText = findViewById(R.id.rootTextReader);
        sendCommand = findViewById(R.id.rootCommand);

        rootbt2  = findViewById(R.id.rootBT2);
        rootbt2.setOnClickListener(ActivityRoot.this);

        rootbt = findViewById(R.id.rootBT);
        rootbt.setOnClickListener(ActivityRoot.this);

        rootbt3 = findViewById(R.id.rootBT3);
        rootbt3.setOnClickListener(ActivityRoot.this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("RootCommands");
        mRootRef = FirebaseDatabase.getInstance().getReference().child("RootCommands");

        loadInfoRoot("s");
    }

    // Trying to Load Information From DB
    private void loadInfoRoot(String s) {
        Query query = mRootRef.orderByChild("sort");
        options= new FirebaseRecyclerOptions.Builder<Root>().setQuery(query,Root.class).build();
        adapter= new FirebaseRecyclerAdapter<Root, ZzRootViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ZzRootViewHolder holder, int position, @NonNull Root model) {
                holder.rootOutText.setText(String.valueOf(model.getCommand()));
                if(holder.rootOutText.equals("")){
                    holder.rootOutText.setText("ls -l");
                } else if (holder.rootOutText.toString().isEmpty()) {
                    holder.rootOutText.setText("ls -l");
                }
                holder.rootOutText1.setText(String.valueOf(model.getLine()));
                holder.rootOutText2.setText(String.valueOf(model.getTime()));
            }
            @NonNull
            @Override
            public ZzRootViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_root, parent, false);
                return new ZzRootViewHolder(view);
            }
        };
        adapter.startListening();
        rootRecyclerView.setAdapter(adapter);
    }

    // Trying to Send Information to FB
    private void sendInfoRoot() {

        int i = (int) new Date().getTime();
        int c = 0;
        int w = i - i - i;

        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strDate = formatter.format(date);


        String sms = sendCommand.getText().toString();

        HashMap hashMap = new HashMap();
        hashMap.put("Time", strDate);
        hashMap.put("ServerTime", ServerValue.TIMESTAMP);
        hashMap.put("Command", sms);

        // for a duhet te ngdriihet se del infinit loop !!!! ERRORI I DB Eshte TEK FOR LOOP
        for(int a = 0; a < ss.size(); a++) {
            hashMap.put("Line", ss.get(c));
        }
        hashMap.put("sort", w);
        hashMap.put("userID", mUser.getUid());

        mRef.child(User.getUsername() + " - " + User.getLevel() + " - " +  mUser.getUid() + " - " + strDate).updateChildren(hashMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                sendCommand.setText("");
                Toast.makeText(getApplicationContext(), "Command SEND", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v == rootbt) {
            Toast.makeText(getApplicationContext(), "CONNECTING", Toast.LENGTH_SHORT).show();
            ReadFile();
        } else if ( v == rootbt2){
            Toast.makeText(getApplicationContext(), "CONNECTING 22222222", Toast.LENGTH_SHORT).show();
            SendCommand();
        } else if (v == rootbt3) {
            List<String> commands = new ArrayList<>();
            String commanderTaker = sendCommand.getText().toString();

            if (commanderTaker.isEmpty()){
                commands.add("ls -l");
            } else if (!commanderTaker.isEmpty()) {
                commands.add(commanderTaker + "; ls -l");
            }
            executeCommands(commands);
            sendInfoRoot();
            sendCommand.setText("");
            close();
        }
    }



    public void ReadFile() {

        JSch jsch = new JSch();
        Session session = null;

        try {
            session = jsch.getSession(user, host, port);
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
                while ((line = br.readLine()) != null) {
                    rootText.setText(line);
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

    public void SendCommand(){

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

    // Read Write (Send the code and Read teh output of the linux System)
    //

    private static Session getSession(){
        if(session == null || !session.isConnected()){
            session = connect(host,user,password);
        }
        return session;
    }
    private static Channel getChannel(){
        if(channel == null || !channel.isConnected()){
            try{
                channel = (ChannelShell)getSession().openChannel("shell");
                channel.connect();

            }catch(Exception e){
                System.out.println("Error while opening channel: "+ e);
            }
        }
        return channel;
    }
    private static Session connect(String hostname, String username, String password){

        JSch jSch = new JSch();

        try {

            session = jSch.getSession(username, hostname, 22);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword(password);

            System.out.println("Connecting SSH to " + hostname + " - Please wait for few seconds... ");
            session.connect();
            System.out.println("Connected!");
        }catch(Exception e){
            System.out.println("An error occurred while connecting to "+hostname+": "+e);
        }

        return session;

    }
    private static void executeCommands(List<String> commands){

        try{
            Channel channel=getChannel();

            System.out.println("Sending commands...");
            sendCommands(channel, commands);

            readChannelOutput(channel);
            System.out.println("Finished sending commands!");

        }catch(Exception e){
            System.out.println("An error ocurred during executeCommands: "+e);
        }
    }
    private static void sendCommands(Channel channel, List<String> commands){

        try{
            PrintStream out = new PrintStream(channel.getOutputStream());

            out.println("#!/bin/bash");
            for(String command : commands){
                out.println(command);
            }
            out.println("exit");

            out.flush();
        }catch(Exception e){
            System.out.println("Error while sending commands: "+ e);
        }

    }

    private static void readChannelOutput(Channel channel){

        byte[] buffer = new byte[1024];

        try{
            InputStream in = channel.getInputStream();
            String line = "";
            while (true){


                if (in.available() > 0) {
                    int i = in.read(buffer, 0, 1024);
                    if (i < 0) {
                        break;
                    }

                    line = new String(buffer, 0, i);

                    if (line.contains("@")) {
                        String[] parts = line.split("@");
                        String part1 = parts[0]; // 004
                        String part2 = parts[1]; // 034556
                        System.out.println(part2);
                    } else {
                        System.out.println(line);
                    }


                    ss.add(line);

                }

                if(line.contains("logout")){
                    break;
                }

                if (channel.isClosed()){
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee){}
            }

        }catch(Exception e){
            System.out.println("Error while reading channel output: "+ e);
        }

    }

    public static void close(){
        channel.disconnect();
        session.disconnect();
        System.out.println("Disconnected channel and session");
    }
}