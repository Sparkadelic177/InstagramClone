package com.example.instagramclone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
//import android.widget.Toolbar;
import android.support.v7.widget.Toolbar;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class PostActivity extends AppCompatActivity {

    Context context;
    EditText description;
    ImageView post;
    ImageView photoIcon;
    ImageView directMessage;
    ImageView btnHome;
    ImageView btnPost;
    ImageView btnProfile;
    Button submit;
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    public final String APP_TAG = "PostActivity";
    File photoFile;
    File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Toolbar bottomnav = findViewById(R.id.bottomnav);
        setSupportActionBar(bottomnav);

        imageFile = getPhotoFileUri(photoFileName);
        Bitmap takenImage = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

        description = findViewById(R.id.etDescription);
        post = findViewById(R.id.ivImage);
        submit = findViewById(R.id.BtnSubmit);
        photoIcon = findViewById(R.id.ivPhoto);
        directMessage = findViewById(R.id.ivDirectMessage);
        btnHome = findViewById(R.id.BtnHome);
        btnPost = findViewById(R.id.BtnNewPost);
        btnProfile = findViewById(R.id.BtnProfile);

        post.setImageBitmap(takenImage);


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

        //used to submit the post to the server
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etDescription = description.getText().toString();
                ParseUser user = ParseUser.getCurrentUser();
//                if(photoFile == null || post.getDrawable() == null || imageFile == null ){
//                    Log.e("PostActivity", "There is no Photo");
//                    Toast.makeText(PostActivity.this, "There is no Photo", Toast.LENGTH_SHORT).show();
//                    return; //no image no posting.
//                }
                if(imageFile != null){
                    savePost(etDescription, user, imageFile);
                }else{
                    savePost(etDescription, user, photoFile);
                }
            }
        });
//        postQuery();
    }

    private void nav(String whereTo) {
        Intent i;
        if(whereTo.equals("home")){
            i = new Intent(this, TimelineActivity.class);
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
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }


    //sending the image to the backed
    private void savePost(String etDescription, ParseUser user, File photoFile) {

        Post upload = new Post();
        upload.setDescription(etDescription);
        upload.setUser(user);
        upload.setImage(new ParseFile(photoFile));
        upload.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if( e!=null){
                    Log.e("failed", "something went wrong " + e.getMessage());
                    return;
                }
                Log.d("worked", "We have successfully posted something");
                description.setText(""); //clear the edit view
                post.setImageResource(0); //clear the image
            }
        });
    }


    private void onLaunchCamera() {

        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(PostActivity.this, "com.codepath.fileprovider", photoFile);
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
                // RESIZE BITMAP, see section below
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // Load the taken image into a preview
                post.setImageBitmap(takenImage);
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

