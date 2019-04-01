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

import com.example.instagramclone.FriendslistAdapter;
import com.example.instagramclone.Post;
import com.example.instagramclone.R;
import com.example.instagramclone.TimeLineAdapter;
import com.example.instagramclone.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class InboxFragment extends Fragment {

    private RecyclerView rvFriends;
    protected List<User> friends;
    protected FriendslistAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inbox, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        rvFriends = view.findViewById(R.id.rvFriendsView);

        //set the layoutManager
        rvFriends.setLayoutManager(new LinearLayoutManager(getContext()));

        //set the data
        friends = new ArrayList<>();

        //set the Adapter
        adapter = new FriendslistAdapter(getContext(), friends);

        //set the adapter to the view
        rvFriends.setAdapter(adapter);

//        showFriends();
    }

//    private void showFriends() {
//        final ParseQuery<User> postQuery = new ParseQuery<>(User.class);
//        postQuery.findInBackground(new FindCallback<User>() {
//            @Override
//            public void done(List<User> objects, ParseException e) {
//                if(e != null){
//                    Log.e("app", "something has happend " + e.getMessage() );
//                    return;
//                }
//                friends.addAll(objects);
//                adapter.notifyDataSetChanged();
//                for (int i = 0; i < objects.size() ; i++) {
//                    Log.d("mainAct", "-  username: " + friends.get(i).getUsername() + " This is a friend");
//                }
//            }
//        });
//    }
}
