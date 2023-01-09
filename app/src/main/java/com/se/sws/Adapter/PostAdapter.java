package com.se.sws.Adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.se.sws.CommentsActivity;
import com.se.sws.InstitutionsActivity;
import com.se.sws.R;

import java.util.Objects;

import static android.content.ContentValues.TAG;

/**
 * Whenever we extend a post we use this class
 */
public class PostAdapter extends AppCompatActivity {

    private String university; // The university pressed on when entering universities
    private String current_uid;
    private String model_uid;
    private String noteId;
    private Boolean flag; // is the user admin or not
    String s;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postdetails);
        // Gets the id of 3 TextViews in the XML file
        TextView mTitleOfPostDetail = findViewById(R.id.titleofnotedetail);
        TextView mContentOfPostDetail = findViewById(R.id.contentofnotedetail);
        TextView mPhoneOfPostDetail = findViewById(R.id.phoneofnotedetail);
        TextView mAuthorNameDetail = findViewById(R.id.authorNamePostDetail);
        ImageView likeButton = findViewById(R.id.likeBtn);
        TextView mLikes = findViewById(R.id.likes);
        ImageView comment = findViewById(R.id.addComment);
        TextView comments = findViewById(R.id.view_comments);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        Intent data=getIntent(); // instead of writing getIntent.getStringExtra, getBooleanExtra etc...
        current_uid = firebaseUser.getUid();
        // Fill every detail on screen
        mContentOfPostDetail.setText(data.getStringExtra("content"));
        mTitleOfPostDetail.setText(data.getStringExtra("title"));
        mPhoneOfPostDetail.setText(data.getStringExtra("phone"));
        model_uid = data.getStringExtra("model_uid");
        // Gets the full name of the post author
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Users").document(model_uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        s = document.getString("FullName");
                        mAuthorNameDetail.setText(s);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        noteId = data.getStringExtra("noteId");

        // Will be used in back-arrow function (which board to return)
        this.university = data.getStringExtra("university");
        this.flag = data.getBooleanExtra("isAdmin",false);
        ImageView popupButton = this.findViewById(R.id.menupopbutton); // The 3 dots on the right of each post

        // Is the current user who clicked on the post is the author or admin
        System.out.println("isAdmin? " + flag);
        System.out.println("User id: " + current_uid);
        System.out.println("Model uid id: " + model_uid);
        if (flag || Objects.equals(current_uid, model_uid)){
            popupButton.setVisibility(View.VISIBLE); // reveal delete button
        }else{
            popupButton.setVisibility(View.GONE); // hide delete button
        }
        /*
         * 3 dots button
         */
        popupButton.setOnClickListener(v -> {

            PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
            popupMenu.setGravity(Gravity.END);
            // Delete button is shown whenever being pressed
            popupMenu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PostAdapter.this);
                    builder.setTitle("Delete");
                    builder.setMessage("Are you sure you want to delete?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        // We choose to delete
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DocumentReference documentReference = firebaseFirestore.collection("Universities").document(university).collection("All").document(noteId);
                            documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(v.getContext(),"Post deleted",Toast.LENGTH_SHORT).show();
                                     // Go to back screen
                                    Intent intent = new Intent(PostAdapter.this, InstitutionsActivity.class);
                                    intent.putExtra("isAdmin",flag); // If the user who clicked on the posts is an admin or not
                                    intent.putExtra("uid",current_uid);
                                    intent.putExtra("model_uid",model_uid);
                                    intent.putExtra("ins",university);
                                    startActivity(intent);

                                }
                            }).addOnFailureListener(e -> Toast.makeText(v.getContext(),"Failed To Delete",Toast.LENGTH_SHORT).show());
                        }
                    });
                    // Do nothing when "no" is pressed
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.create().show();
                    return false;
                }
            });
            popupMenu.show();
        }
        );

       // isLikes(noteId); Stuck here :\\\\\\\
        /*
        * Useful links - https://www.youtube.com/watch?v=B1NiPvfMbDM&ab_channel=KODDev likes system
        *               https://www.youtube.com/watch?v=wQN2eCO-M_Q&ab_channel=CodingBunch chatroom
        * */
        isLikes(noteId,likeButton);
        nrLikes(mLikes,noteId);
        getComments(noteId, comments);

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(postDetails.this,"Liked post!",Toast.LENGTH_SHORT).show();
                if (likeButton.getTag().equals("like")){
                    FirebaseDatabase.getInstance().getReference().child("Likes")
                            .child(noteId)
                            .child(firebaseUser.getUid()).setValue(true);
                }else{
                    FirebaseDatabase.getInstance().getReference().child("Likes")
                            .child(noteId)
                            .child(firebaseUser.getUid()).removeValue();
                }
            }});

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostAdapter.this, CommentsActivity.class);
                intent.putExtra("postid",noteId);
                intent.putExtra("publisherid",model_uid);
                startActivity(intent);

            }
        });

        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostAdapter.this, CommentsActivity.class);
                intent.putExtra("postid", noteId);
                intent.putExtra("publisherid", model_uid);
                startActivity(intent);
            }
        });
    }

    private void getComments(String postid, TextView comments){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Comments")
                .child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comments.setText("View all " + snapshot.getChildrenCount() + " Comments");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void isLikes(String postid, ImageView imageView){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Likes")
                .child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(firebaseUser.getUid()).exists()){
                    imageView.setImageResource(R.drawable.ic_liked);
                    imageView.setTag("liked");
                }else {
                    imageView.setImageResource(R.drawable.ic_like);
                    imageView.setTag("like");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void nrLikes(TextView likes, String postid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Likes")
                .child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                likes.setText(snapshot.getChildrenCount() + " likes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    // Which board to return to
    public void backArrow(View view){
        Intent intent = new Intent(PostAdapter.this, InstitutionsActivity.class);
        intent.putExtra("ins",university);
        intent.putExtra("isAdmin",flag); // If the user who clicked on the posts is an admin or not
        intent.putExtra("uid",current_uid); // current user
        intent.putExtra("model_uid",model_uid); // author of the post
        startActivity(intent);
    }


}