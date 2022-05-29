package com.example.LeftMenuBar.LoginFiles;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.LeftMenuBar.LoginFiles.Admin.CreateUserAdmin;
import com.example.LeftMenuBar.LoginFiles.LoginMenuItem.ChatListActivity;
import com.example.LeftMenuBar.LoginFiles.LoginMenuItem.SettingsActivityLogin;
import com.example.LeftMenuBar.LoginFiles.LoginMenuItem.ContactLogin;
import com.example.LeftMenuBar.LoginFiles.LoginMenuItem.DashboardLogin;
import com.example.LeftMenuBar.LoginFiles.LoginMenuItem.FindFriendLogin;
import com.example.LeftMenuBar.LoginFiles.LoginMenuItem.NewsLogin;
import com.example.LeftMenuBar.LoginFiles.LoginMenuItem.ProfileLogin;
import com.example.LeftMenuBar.LoginFiles.LoginMenuItem.YourFriendLogin;
import com.example.LeftMenuBar.MainActivity;
import com.example.LeftMenuBar.R;
import com.example.LeftMenuBar.Utils.PreferenceManager;
import com.example.LeftMenuBar.Utils.UserLogin;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class LoginActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final UserLogin U = new UserLogin();
    private PreferenceManager preferenceManager;
    private DrawerLayout drawer_login;
    TextView UserFullname, UserEmailOrPassword;
    CircleImageView profileImage;


    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mRef;

    static NavigationView navigationView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferenceManager = new PreferenceManager(getApplicationContext());
        hideItem();

        if(preferenceManager.getBoolean(U.User_is_login)){
            Intent intent = new Intent(getApplicationContext(), DashboardLogin.class);
            startActivity(intent);
            finish();
        }

        NavigationView navigationView1 = findViewById(R.id.nav_view_login);
        navigationView1.setNavigationItemSelectedListener(this);

        NavigationView navigationView3 = findViewById(R.id.nav_view_login);
        navigationView3.setNavigationItemSelectedListener(this);

        View Email= navigationView1.getHeaderView(0);
        View profileIMAGE= navigationView1.getHeaderView(0);
        View Name= navigationView1.getHeaderView(0);

        UserEmailOrPassword= Email.findViewById(R.id.UserEmailOrUsernameShowTopBar);
        profileImage= profileIMAGE.findViewById(R.id.LoginTopImage);
        UserFullname= Name.findViewById(R.id.UserNameShowTopBar);

        Toolbar toolbar12 = findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar12);
        drawer_login = findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_login, toolbar12,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_login.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_login,
                    new DashboardLogin()).commit();
            navigationView1.setCheckedItem(R.id.login_nav_dashboard);
        }

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("Users");

        FirebaseMessaging.getInstance().subscribeToTopic(mUser.getUid());

        mRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String profileImageURL = snapshot.child("profileImage").getValue().toString();
                    String username = snapshot.child("username").getValue().toString();
                    String fullname = snapshot.child("fullName").getValue().toString();
                    String level = snapshot.child("accessLevel").getValue().toString();
                    String LevelT = snapshot.child("ID_ACC_LEVEL").getValue().toString();
                    int levelID = Integer.valueOf(LevelT);
                    System.out.println("THIS IS LIVE ID "+levelID);

                    User.setLevelID(levelID);
                    User.setFullname(fullname);
                    User.setUsername(username);
                    User.setLevel(level);
                    User.setImgProfile(profileImageURL);

                    hideItem();

                    System.out.println("Your level ID IS !!!  " + User.getLevelID());
                    System.out.println(User.getFullname());
                    System.out.println(User.getLevel());
                    System.out.println(User.getUsername());

                    Picasso.get().load(User.getImgProfile()).into(profileImage);
                    UserEmailOrPassword.setText(User.getUsername());
                    UserFullname.setText(User.getFullname());

                } else {
                    Toast.makeText(LoginActivity.this, "Data do not exist!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hideItem() {

        navigationView3 = findViewById(R.id.nav_view_login);
        navigationView3.setNavigationItemSelectedListener(this);

        System.out.println("LEVEL OF LEVEL LEVEL "  + User.getLevelID());
        Menu nav_map = navigationView3.getMenu();
        if(User.getLevelID() == 2){
            nav_map.findItem(R.id.login_nav_map).setVisible(false);
            nav_map.findItem(R.id.nav_share1).setVisible(false);
            nav_map.findItem(R.id.nav_mail1).setVisible(false);
            nav_map.findItem(R.id.nav_mail11).setVisible(false);
            nav_map.findItem(R.id.login_nav_settings).setVisible(false);
            nav_map.findItem(R.id.nav_mail12).setVisible(false);
            nav_map.findItem(R.id.admin_bar).setTitle("Admin");
            nav_map.findItem(R.id.nav_create_user).setVisible(true);
        } else if(User.getLevelID() == 1){
            nav_map.findItem(R.id.login_nav_map).setVisible(false);
            nav_map.findItem(R.id.nav_share1).setVisible(false);
            nav_map.findItem(R.id.nav_mail1).setVisible(false);
            nav_map.findItem(R.id.nav_mail11).setVisible(false);
            nav_map.findItem(R.id.admin_bar).setTitle("User");
            nav_map.findItem(R.id.login_nav_settings).setVisible(false);
            nav_map.findItem(R.id.nav_mail12).setVisible(false);
            nav_map.findItem(R.id.nav_create_user).setVisible(false);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        System.out.println(User.getLevelID());
        int level = User.getLevelID();

        if (id == R.id.login_nav_dashboard) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_login,
                    new DashboardLogin()).commit();
        } else if (id == R.id.login_nav_chat) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_login,
                    new ChatListActivity()).commit();
        } else if (id == R.id.login_nav_news) {
            if (level == 1) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_login,
                        new NewsLogin()).commit();
            } else if (level == 2) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_login,
                        new NewsLogin()).commit();
            }
        } else if (id == R.id.login_nav_profile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_login,
                    new ProfileLogin()).commit();
        } else if (id == R.id.login_nav_settings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_login,
                    new SettingsActivityLogin()).commit();
        } else if (id == R.id.login_nav_addfriends) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_login,
                    new FindFriendLogin()).commit();
        } else if (id == R.id.login_nav_friends) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_login,
                    new YourFriendLogin()).commit();
        } else if (id == R.id.login_nav_map) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_login,
                    new ProfileLogin()).commit();
        } else if (id == R.id.login_nav_contact) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_login,
                    new ContactLogin()).commit();
        } else if (id == R.id.login_nav_logout) {
            mAuth.signOut();
            Toast.makeText(this, "LogOut", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_create_user) {
            Intent i = new Intent(this, CreateUserAdmin.class);
            startActivity(i);
        }
        drawer_login.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer_login.isDrawerOpen(GravityCompat.START)) {
            drawer_login.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}