package com.example.instagramclone;

import android.content.Intent;
import android.security.NetworkSecurityPolicy;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


import com.example.instagramclone.Fragment.ComposeFragment;
import com.example.instagramclone.Fragment.InboxFragment;
import com.example.instagramclone.Fragment.ProfileFragment;
import com.example.instagramclone.Fragment.TimelineFragment;
import com.parse.ParseUser;


public class TimelineActivity extends AppCompatActivity {
    ImageView ivDirectMessage;
    ImageView ivLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        final FragmentManager fragmentManager = getSupportFragmentManager();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ivLogout = findViewById(R.id.ivLogout);
        ivLogout.setImageResource(R.drawable.ic_power_settings_new_black_24dp);


        ivDirectMessage = findViewById(R.id.ivDirectMessage);
        ivDirectMessage.setImageResource(R.mipmap.ic_ufi_new_direct);

        //default location when app start up
        bottomNavigationView.setSelectedItemId(R.id.action_home);

        //choosing on the fragment view depending on the icon id pressed
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = new ComposeFragment();
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        fragment = new TimelineFragment();
                        break;
                    case R.id.action_compose:
                        fragment = new ComposeFragment();
                        break;
                    case R.id.action_profile:
                        fragment = new ProfileFragment();
                        break;
                    default:
                        break;
                }
                //telling the manager what to replace the container with and commit immiediatly
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });

        ivDirectMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new InboxFragment();
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
            }
        });

        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

}
