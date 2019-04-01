package com.example.instagramclone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.List;


public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.ViewHolder> {

    private Context context; //to let the other pages whats going on here
    private List<Post> posts; //the list are going to have the model class methods to get data


    //constructor for when we call this class through the TimeLineActivity class
    public TimeLineAdapter(Context context, List<Post>posts){
        this.context = context;
        this.posts = posts;
    }

    //this is where the layout is made
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inflating the parent layout with the layout tweet, returning a view
        View view = LayoutInflater.from(context).inflate(R.layout.post_item, viewGroup, false);
        //calling the constructor passing the view to return a ViewHolder
        return new ViewHolder(view);
    }


    //need to turn the image returned to a bitmap image, or turn it to a absolute path
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Post post = posts.get(i);

        ParseFile image = post.getImage();
        String url = image.getUrl();
        if(url != null) {
            Glide.with(context).load(image.getUrl()).into(viewHolder.ivPost);
            Log.d("url_image", "this is the url " + url);
        }


        viewHolder.tvHandle.setText(post.getUser().getUsername());
        viewHolder.tvDescription.setText(post.getDescription());

//        DownloadImageTask imageTask = new DownloadImageTask(ivPost);
//        Bitmap bmp = imageTask.doInBackground(image.getUrl());
//        imageTask.onPostExecute(bmp);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }



    //this is where the layout is defind
    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivHeart;
        private ImageView ivSend;
        private ImageView ivComment;
        private ImageView ivSave;
        private TextView tvDescription;
        private TextView tvHandle;
        private ImageView ivPost;


        public ViewHolder(View itemView){
            super(itemView);
            ivPost = itemView.findViewById(R.id.ivPost);
            ivHeart = itemView.findViewById(R.id.ivHeart);
            ivSend = itemView.findViewById(R.id.ivSend);
            ivComment = itemView.findViewById(R.id.ivComment);
            ivSave = itemView.findViewById(R.id.ivSave);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvHandle = itemView.findViewById(R.id.tvHandle);

            ivHeart.setImageResource(R.mipmap.ic_ufi_heart);
            ivSend.setImageResource(R.mipmap.ic_ufi_new_direct);
            ivComment.setImageResource(R.mipmap.ic_ufi_comment);
            ivSave.setImageResource(R.mipmap.ic_ufi_save);
        }

    }

//    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//        ImageView bmImage;
//        public DownloadImageTask(ImageView bmImage) {
//            this.bmImage = bmImage;
//        }
//
//        protected Bitmap doInBackground(String... urls) {
//            String urldisplay = urls[0];
//            Bitmap bmp = null;
//            try {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                bmp = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                Log.e("Error" , e.getMessage());
//                e.printStackTrace();
//            }
//            return bmp;
//        }
//        protected void onPostExecute(Bitmap result) {
//            ivPost.setImageBitmap(result);
//        }
//    }
}
