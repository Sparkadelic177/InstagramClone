package com.example.instagramclone;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.example.instagramclone.Fragment.ComposeFragment;
import com.example.instagramclone.Fragment.ProfileFragment;
import com.example.instagramclone.Fragment.TimelineFragment;


public class TimelineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        final FragmentManager fragmentManager = getSupportFragmentManager();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    }

}
