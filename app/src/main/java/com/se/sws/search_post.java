package com.se.sws;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.se.sws.Model.Post;

public class search_post extends AppCompatActivity {
    boolean flag; // Is user admin or not
    Intent _intent; // Usage in multiple functions
    ImageView mCreatePostsFab; // Background
    String current_uid;
    String post_author;
    private FirebaseAuth firebaseAuth;

    ListView listView;     // List View object

    RecyclerView recyclerView; // For scrolling
    StaggeredGridLayoutManager staggeredGridLayoutManager; // The way posts are shown

    /**
     * FB information - user & fireStore
     */
    FirebaseUser firebaseUser; // Will be used for representatives of each university
    FirebaseFirestore firebaseFirestore;

    /**
     * We connect FB and the app together here
     */
    //FirestoreRecyclerAdapter<firebaseModel, search_post.PostViewHolder> postAdapter;
    FirestoreRecyclerOptions<Post> allUsersPosts;

    public search_post() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);
        _intent = getIntent();
        flag = _intent.getBooleanExtra("isAdmin", false); // Is the user admin or not
        current_uid = _intent.getStringExtra("uid");
        post_author = _intent.getStringExtra("model_uid");

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser(); // Will be used for representatives of each university
        firebaseFirestore = FirebaseFirestore.getInstance();

        // initialise ListView with id
        listView = findViewById(R.id.listView);
        Query query = firebaseFirestore.collection("Universities").document("backUp").collection("All").orderBy("title", Query.Direction.ASCENDING);
        // Connect the query above with the adapter declared on the start
        allUsersPosts = new FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post.class).build();


    }


/*
problems are:
list

 */



}