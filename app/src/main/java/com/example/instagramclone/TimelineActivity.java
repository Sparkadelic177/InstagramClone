package com.example.instagramclone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class TimelineActivity extends AppCompatActivity {

    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    public final String APP_TAG = "PostActivity";

    RecyclerView rvPosts;
    TimeLineAdapter adapter;
    List<Post> posts;

    ImageView ivPost;
    ImageView btnHome;
    ImageView btnPost;
    ImageView btnProfile;
    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Toolbar bottomnav = findViewById(R.id.bottomnav);
        setSupportActionBar(bottomnav);

        rvPosts = findViewById(R.id.rvTimeline);
        ivPost = findViewById(R.id.ivPost);

        btnHome = findViewById(R.id.BtnHome);
        btnPost = findViewById(R.id.BtnNewPost);
        btnProfile = findViewById(R.id.BtnProfile);

        posts = new ArrayList<>();
        adapter = new TimeLineAdapter(this, posts);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvPosts.setLayoutManager(linearLayoutManager);

        rvPosts.setAdapter(adapter);

        btnHome.setImageResource(R.mipmap.ic_home_outline);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nav("home");
            }
        });

        btnPost.setImageResource(R.mipmap.ic_new_post_outline);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nav("post");
            }
        });

        btnProfile.setImageResource(R.mipmap.ic_user_outline);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nav("profile");
            }
        });

        postQuery();

    }

    public void gotoPostActivity(){
        Intent g = new Intent(this, PostActivity.class);
        g.putExtra("image", photoFile);
        startActivity(g);
        finish();
    }


    //this method is going to be used to get the data on the timeline
    private void postQuery() {
        final ParseQuery<Post> postQuery = new ParseQuery<>(Post.class);
        postQuery.include(Post.KEY_USER); //include the user for each post
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e != null){
                    Log.e("app", "something has happend " + e.getMessage() );
                    return;
                }

                for (int i = 0; i < objects.size() ; i++) {
                    posts.add(objects.get(i));
                    Log.d("mainAct", "Post: " + posts.get(i).getDescription() + "-  username: " + posts.get(i).getUser().getUsername() + "--- " + posts.get(i).getImage());
                }
//                adapter.clear();
                adapter.addAll(posts);
            }
        });
    }


    private void nav(String whereTo) {
        Intent i;
        if(whereTo.equals("home")){
            i = new Intent(this,TimelineActivity.class);
            startActivity(i);
            finish();
        }else if(whereTo.equals("post")){
            onLaunchCamera();
        }else if(whereTo.equals("profile")){
            Log.d("app", "it is going to the profile page");
            try{
                i = new Intent(this, Profile.class);
                startActivity(i);
                finish();
            }catch (Exception e){
                Log.e("app", "something went wrong " + e.getMessage());
            }

        }else{
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    private void onLaunchCamera() {

        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(TimelineActivity.this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
//                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
//                ivPost.setImageBitmap(takenImage);
                gotoPostActivity();
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }
}
