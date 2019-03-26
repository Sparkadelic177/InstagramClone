package com.example.instagramclone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import org.w3c.dom.Text;

import java.io.File;
import java.util.List;


public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.ViewHolder> {

    private Context context; //to let the other pages whats going on here
    private List<Post> posts; //the list are going to have the model class methods to get data
    public String photoFileName = "photo.jpg";
    public final String APP_TAG = "PostActivity";
    TimelineActivity timelineActivity = new TimelineActivity();
    Bitmap bmp;



    //constructor for when we call this class through the TimeLineActivity class
    public TimeLineAdapter(Context context, List<Post>posts){
        this.context = context;
        this.posts = posts;
    }


    //need to turn the image returned to a bitmap image, or turn it to a absolute path
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Post post = posts.get(i);
        ParseFile parseFile = post.getImage();

        parseFile.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, ParseException e) {
                if(e != null){
                    Log.e("App", "Something went wrong with the file");
                    return;
                }

                 bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
            }
        });

        viewHolder.ivPost.setImageBitmap(bmp);
        viewHolder.tvDescription.setText(post.getDescription());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    //this is where the layout is made
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inflating the parent layout with the layout tweet, returning a view
        View view = LayoutInflater.from(context).inflate(R.layout.postlayout, viewGroup, false);
        //calling the constructor passing the view to return a ViewHolder
        return new ViewHolder(view);
    }



    //this is where the layout is defind
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivPost;
        ImageView ivHeart;
        ImageView ivSend;
        ImageView ivComment;
        ImageView ivSave;
        TextView tvDescription;


        public ViewHolder(View itemView){
            super(itemView);
            ivPost = itemView.findViewById(R.id.ivPost);
            ivHeart = itemView.findViewById(R.id.ivHeart);
            ivSend = itemView.findViewById(R.id.ivSend);
            ivComment = itemView.findViewById(R.id.ivComment);
            ivSave = itemView.findViewById(R.id.ivSave);
            tvDescription = itemView.findViewById(R.id.tvDescription);

            ivHeart.setImageResource(R.mipmap.ic_ufi_heart);
            ivSend.setImageResource(R.mipmap.ic_ufi_new_direct);
            ivComment.setImageResource(R.mipmap.ic_ufi_comment);
            ivSave.setImageResource(R.mipmap.ic_ufi_save);

        }

    }
}
