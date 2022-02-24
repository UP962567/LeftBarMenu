package com.example.LeftMenuBar.LoginFiles;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.LeftMenuBar.LoginFiles.LoginMenuItem.ContactLogin;
import com.example.LeftMenuBar.LoginFiles.LoginMenuItem.DashboardLogin;
import com.example.LeftMenuBar.LoginFiles.LoginMenuItem.MapsLogin;

import com.example.LeftMenuBar.LoginFiles.LoginMenuItem.SettingsLogin;
import com.example.LeftMenuBar.MainActivity;
import com.example.LeftMenuBar.R;
import com.example.LeftMenuBar.Utils.DatabaseCodes;
import com.example.LeftMenuBar.Utils.UserLogin;
import com.google.android.material.navigation.NavigationView;


public class LoginActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final UserLogin U = new UserLogin();
    private DrawerLayout drawer_login;
    TextView UserFullname, UserEmailOrPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new DatabaseCodes.getLoginInofrmation().getLoginInofrmation();


        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view_login);
        navigationView1.setNavigationItemSelectedListener(this);
        View Email= navigationView1.getHeaderView(0);
        UserEmailOrPassword= Email.findViewById(R.id.UserEmailOrUsernameShowTopBar);
        UserEmailOrPassword.setText(U.getLoginEmail());

        NavigationView navigationView2 = (NavigationView) findViewById(R.id.nav_view_login);
        navigationView1.setNavigationItemSelectedListener(this);
        View Name= navigationView1.getHeaderView(0);
        UserFullname= Name.findViewById(R.id.UserNameShowTopBar);
        UserFullname.setText(U.getLoginFullname());


        Toolbar toolbar12 = findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar12);
        drawer_login = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view_login);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_login, toolbar12,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_login.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_login,
                    new DashboardLogin()).commit();
            navigationView.setCheckedItem(R.id.login_nav_dashboard);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login_nav_dashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_login,
                        new DashboardLogin()).commit();
                break;
            case R.id.login_nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_login,
                        new SettingsLogin()).commit();
                break;
            case R.id.login_nav_contact:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_login,
                        new ContactLogin()).commit();
                break;
            case R.id.login_nav_map:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_login,
                        new MapsLogin()).commit();
                break;
            case R.id.login_nav_logout:
                Toast.makeText(this, "LogOut", Toast.LENGTH_SHORT).show();
                Intent i;
                i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();

                break;
            case R.id.nav_mail:
                Toast.makeText(this, "Mail", Toast.LENGTH_SHORT).show();
                break;
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