package com.example.instagramclone.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instagramclone.Post;
import com.example.instagramclone.R;
import com.example.instagramclone.TimeLineAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class TimelineFragment extends Fragment {

    private RecyclerView rvPost;
    protected List<Post> posts;
    protected TimeLineAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timeline, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        rvPost = view.findViewById(R.id.rvTimeline);

        //create the dataSource
        posts = new ArrayList<>();

        //create the adapter
        adapter = new TimeLineAdapter(getContext(), posts);

        //place the adapter on RV
        rvPost.setAdapter(adapter);

        //set the layoutManager
        rvPost.setLayoutManager(new LinearLayoutManager(getContext()));

        postQuery();

    }

    //this method is going to be used to get the data on the timeline
    protected void postQuery() {
        final ParseQuery<Post> postQuery = new ParseQuery<>(Post.class);
        postQuery.include(Post.KEY_USER); //include the user for each post
        postQuery.setLimit(20);
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e != null){
                    Log.e("app", "something has happend " + e.getMessage() );
                    return;
                }
                posts.addAll(objects);
                adapter.notifyDataSetChanged();
                for (int i = 0; i < objects.size() ; i++) {
                    Log.d("mainAct", "Post: " + posts.get(i).getDescription() + "-  username: " + posts.get(i).getUser().getUsername() + "--- " + posts.get(i).getImage());
                }
            }
        });
    }
}
