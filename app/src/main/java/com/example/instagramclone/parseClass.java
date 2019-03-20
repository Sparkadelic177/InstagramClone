package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class parseClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("sparky-instagram") // should correspond to APP_ID env variable
                .clientKey("SparkysInstagramboi")  // set explicitly unless clientKey is explicitly configured on Parse server
                .server("https://sparky-instagram.herokuapp.com/parse").build());


    }
}

