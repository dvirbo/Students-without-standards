package com.se.sws;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.se.sws.boards.Institutions;

import java.util.Objects;

import static android.content.ContentValues.TAG;

/**
 * Whenever we extend a post we use this class
 */
public class postDetails extends AppCompatActivity {

    private String university; // The university pressed on when entering universities
    private String current_uid;
    private String model_uid;
    private String noteId;
    private Boolean flag; // is the user admin or not
    private String name;
    private String title;
    private String author;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postdetails);
        // Gets the id of 3 TextViews in the XML file
        TextView mTitleOfPostDetail = findViewById(R.id.titleofnotedetail);
        TextView mContentOfPostDetail = findViewById(R.id.contentofnotedetail);
        TextView mPhoneOfPostDetail = findViewById(R.id.phoneofnotedetail);
        TextView mAuthorNameDetail = findViewById(R.id.authorNamePostDetail);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        Intent data=getIntent(); // instead of writing getIntent.getStringExtra, getBooleanExtra etc...
        current_uid = data.getStringExtra("uid");
        // Fill every detail on screen
        mContentOfPostDetail.setText(data.getStringExtra("content"));
        mTitleOfPostDetail.setText(data.getStringExtra("title"));
        title = mTitleOfPostDetail.toString();
        mPhoneOfPostDetail.setText(data.getStringExtra("phone"));
        model_uid = data.getStringExtra("model_uid");

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
        name = data.getStringExtra("UserName");

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
                    AlertDialog.Builder builder = new AlertDialog.Builder(postDetails.this);
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
                                    Intent intent = new Intent(postDetails.this, Institutions.class);
                                    intent.putExtra("isAdmin",flag); // If the user who clicked on the posts is an admin or not
                                    intent.putExtra("uid",current_uid);
                                    intent.putExtra("UserName",name);
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
    }

    // Which board to return to
    public void backArrow(View view){
        Intent intent = new Intent(postDetails.this, Institutions.class);
        intent.putExtra("ins",university);
        intent.putExtra("isAdmin",flag); // If the user who clicked on the posts is an admin or not
        intent.putExtra("uid",current_uid); // current user
        intent.putExtra("UserName",name); // Current username
        intent.putExtra("model_uid",model_uid); // author of thr post
        startActivity(intent);
    }


}