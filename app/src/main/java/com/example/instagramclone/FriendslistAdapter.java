package com.example.instagramclone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.List;

public class FriendslistAdapter extends RecyclerView.Adapter<FriendslistAdapter.ViewHolder> {

    List<User>friends;
    Context context;

    public FriendslistAdapter(Context context, List<User>friends ){
        this.context = context;
        this.friends = friends;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inflating the parent layout with the layout tweet, returning a view
        View view = LayoutInflater.from(context).inflate(R.layout.friends_layout, viewGroup, false);
        //calling the constructor passing the view to return a ViewHolder
        return new FriendslistAdapter.ViewHolder(view);
    }


    //need to turn the image returned to a bitmap image, or turn it to a absolute path
//    the view holder gets past througt this method to connect the widgets
    @Override
    public void onBindViewHolder(@NonNull FriendslistAdapter.ViewHolder viewHolder, int i) {
        User user = friends.get(i);
//        ParseFile parseFile = suer.getImage();

        viewHolder.tvUserName.setText(user.getUsername());

        //TODO: we need to place the users image next to the name, need a file / column to save an image
//        if(parseFile != null)
//            Glide.with(context).load(parseFile.getUrl()).into(viewHolder.ivUserImage);

    }

    @Override
    public int getItemCount() {
        return friends.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivUserImage;
        TextView tvUserName;

        //an view item is going to through where the view is connecting the widgets
        ViewHolder(View itemView){
            super(itemView);
            ivUserImage = itemView.findViewById(R.id.ivUserImage);
            tvUserName = itemView.findViewById(R.id.tvUsername);
        }
    }



}

