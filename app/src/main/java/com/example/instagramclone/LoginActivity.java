package com.example.instagramclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginActivity extends AppCompatActivity {

    private EditText tvUsername;
    private EditText tvPassword;
    private Button submit;
    private Button signUp;
    ImageView instagramLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ParseUser currentuser = ParseUser.getCurrentUser();


        if(currentuser != null){
            gotoMainActivity();
            Toast.makeText(this, currentuser.getUsername(), Toast.LENGTH_SHORT).show();
        }


        tvUsername = findViewById(R.id.unText);
        tvPassword = findViewById(R.id.pwInput);
        submit = findViewById(R.id.BtnSubmit);
        signUp = findViewById(R.id.BtnSignup);
        instagramLogo = findViewById(R.id.ivInstagram);

        instagramLogo.setImageResource(R.mipmap.ic_icon_round);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = tvUsername.getText().toString();
                String password = tvPassword.getText().toString();
                login(username, password);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSignup();
            }
        });
    }

    private void gotoSignup() {
        Intent i = new Intent(this, Signup.class);
        startActivity(i);
        finish();
    }

    private void login(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null){
                    Log.e("loginFailed", "Issue went wrong when login in: "  + e.getMessage());
                    Toast.makeText(LoginActivity.this, "Failed Username / Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d("worked", "it went through");
                Toast.makeText(LoginActivity.this, "welcome", Toast.LENGTH_SHORT).show();
                gotoMainActivity();
            }
        });
    }

    private void gotoMainActivity() {
        Intent i = new Intent(this, TimelineActivity.class);
        startActivity(i);
        finish(); //closes this login activity and keeps the modal(opened activity) as main
    }
}
