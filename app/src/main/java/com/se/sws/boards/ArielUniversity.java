package com.se.sws.boards;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.se.sws.AddProducts;
import com.se.sws.R;
import com.se.sws.firebasemodel;
import com.se.sws.postdetails;

import java.util.Objects;

public class ArielUniversity extends AppCompatActivity {
    boolean flag;
    Intent _intent;
    ImageView mcreatenotesfab;
    private FirebaseAuth firebaseAuth;


    RecyclerView mrecyclerview;
    StaggeredGridLayoutManager staggeredGridLayoutManager;


    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    FirestoreRecyclerAdapter<firebasemodel,NoteViewHolder> noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ariel_university);
        View ariel = findViewById(R.id.imageAddPostMain_AR);
        _intent = getIntent();
        flag = _intent.getBooleanExtra("isAdmin", false);

        mcreatenotesfab=(ImageView) ariel;
        firebaseAuth=FirebaseAuth.getInstance();

        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();

        Objects.requireNonNull(getSupportActionBar()).setTitle("Ariel Posts");

        ImageView imageAddItemMain = (ImageView) ariel;
        imageAddItemMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddProducts.class);
                intent.putExtra("University","AR");
                startActivity(intent);
            }
        });

        if (flag) {
            imageAddItemMain.setVisibility(View.VISIBLE);
        } else {
            imageAddItemMain.setVisibility(View.GONE);
        }


    Query query=firebaseFirestore.collection("AR").document("All").collection("items").orderBy("title",Query.Direction.ASCENDING);

    FirestoreRecyclerOptions<firebasemodel> allusernotes= new FirestoreRecyclerOptions.Builder<firebasemodel>().setQuery(query,firebasemodel.class).build();

    noteAdapter = new FirestoreRecyclerAdapter<firebasemodel, NoteViewHolder>(allusernotes) {
            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull firebasemodel model) {

                holder.title.setText(model.getTitle());
                holder.content.setText(model.getContent());
                holder.phone.setText(model.getPhone());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Post details activity
                        Intent intent = new Intent(v.getContext(), postdetails.class);
                        intent.putExtra("university","AR");
                        intent.putExtra("title",model.getTitle());
                        intent.putExtra("content",model.getContent());
                        intent.putExtra("phone",model.getPhone());
                        intent.putExtra("isAdmin",flag);

                        v.getContext().startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.posts_layout,parent,false);
                return new NoteViewHolder(view);
            }
        };

        mrecyclerview=findViewById(R.id.postsRecyclerView);
        mrecyclerview.setHasFixedSize(true);
        staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mrecyclerview.setLayoutManager(staggeredGridLayoutManager);
        mrecyclerview.setAdapter(noteAdapter);

    }
    public class NoteViewHolder extends RecyclerView.ViewHolder
    {
        private TextView title;
        private TextView content;
        private TextView phone;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.textTitle);
            content = (TextView) itemView.findViewById(R.id.textSubtitle);
            phone = (TextView) itemView.findViewById(R.id.textPhoneNumber);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(noteAdapter != null){
            noteAdapter.stopListening();
        }
    }


}