package com.example.instagramclone.Fragment;

import android.util.Log;

import com.example.instagramclone.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends TimelineFragment {

    @Override
    protected void postQuery() {
        final ParseQuery<Post> postQuery = new ParseQuery<>(Post.class);
        postQuery.include(Post.KEY_USER); //include the user for each post
        postQuery.setLimit(20);
        postQuery.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser()); //filtering out only the current users data
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e != null){
                    Log.e("app", "something has happend " + e.getMessage() );
                    return;
                }
                posts.addAll(objects);
                adapter.addAll(posts);
                for (int i = 0; i < objects.size() ; i++) {
                    Log.d("mainAct", "Post: " + posts.get(i).getDescription() + "-  username: " + posts.get(i).getUser().getUsername() + "--- " + posts.get(i).getImage());
                }
            }
        });

    }
}
