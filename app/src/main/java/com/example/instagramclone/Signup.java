package com.example.instagramclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Signup extends AppCompatActivity {

    private EditText username;
    private EditText email;
    private EditText password;
    private Button  submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = findViewById(R.id.etEmail);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        submit = findViewById(R.id.BtnNewUser);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = username.getText().toString();
                String eMail = email.getText().toString();
                String passWord = password.getText().toString();
                parserSignup(userName, eMail, passWord);
            }
        });


    }

    private void parserSignup(String userName, String eMail, String passWord) {

        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(userName);
        user.setPassword(passWord);
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e != null) {
                    Log.e("failed", "The sign up did not happen: " + e.getMessage());
                    return;
                }
                gotoMainActivity();
            }
        });
    }

    private void gotoMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish(); //closes this login activity and keeps the modal(opened activity) as main
    }


}
