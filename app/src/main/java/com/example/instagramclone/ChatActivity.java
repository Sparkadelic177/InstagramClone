package com.example.instagramclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
     RecyclerView rvChat;
     List<Post>chats;
     //TODO: adapter class goes here
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //connect the the rv

        chats = new ArrayList<>();

    }
}
