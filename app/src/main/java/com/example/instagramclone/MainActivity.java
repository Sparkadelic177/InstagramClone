package com.example.instagramclone;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText description;
    Button takeImage;
    ImageView post;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        description = findViewById(R.id.etDescription);
        takeImage = findViewById(R.id.BtnCaptureImage);
        post = findViewById(R.id.ivImage);
        submit = findViewById(R.id.BtnSubmit);

        //when everything is being loaded you want to view all of the posting
        //query for the post in the backend

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etDescription = description.getText().toString();
                ParseUser user = ParseUser.getCurrentUser();
                savePost(etDescription, user);
            }
        });
//        postQuery();
    }

    private void savePost(String etDescription, ParseUser user) {
        Post post = new Post();
        post.setDescription(etDescription);
        post.setUser(user);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if( e!=null){
                    Log.e("failed", "something went wrong " + e.getMessage());
                    return;
                }
                Log.d("worked", "We have successfully posted something");
                description.setText("");
            }
        });
    }

    private void postQuery() {
        final ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER); //include the user for each post
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                for (int i = 0; i < objects.size() ; i++) {
                    Log.d("mainAct", "Post: " + objects.get(i).getDescription() + " username: " + objects.get(i).getUser().getUsername());
                }
            }
        });
    }
}
