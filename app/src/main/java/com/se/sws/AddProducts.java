package com.se.sws;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.se.sws.boards.ArielUniversity;
import com.se.sws.boards.BenGurion;
import com.se.sws.boards.HaifaUni;
import com.se.sws.boards.HebrewUni;
import com.se.sws.boards.ReichmanUni;
import com.se.sws.boards.TechnionUni;
import com.se.sws.boards.TelAvivUni;

import java.util.HashMap;
import java.util.Map;

/**
 * The admin panel - adds a product option
 */
public class AddProducts extends AppCompatActivity {
    // Edit text info variables
    EditText mCreateTitleOfPost, mCreateContentOfPost, mCreatePhoneOfPost;
    ImageView mSavePost;

    // Firebase credentials - where and how to put the information we want to store
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    Intent _intent;
    ProgressBar mProgressBarOfCreatePost; // Used when the post is published
    String uniName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

        _intent = getIntent();
        /*
        After we press on universities we get the name of the university by passing
        the name of it via an intent (put extra)
        */
        uniName = _intent.getStringExtra("University");

        /*
        * Variables below gets the information via the activity_add_product.xml
        * Whatever we fill goes in these boys except for the progress bar which we do not fill
        * But it appears whenever you create the post
        */
        mSavePost = findViewById(R.id.imageSave);
        mCreateContentOfPost = findViewById(R.id.inputNoteText);
        mCreateTitleOfPost = findViewById(R.id.inputNoteTitle);
        mCreatePhoneOfPost = findViewById(R.id.inputPhoneNumber);
        mProgressBarOfCreatePost = findViewById(R.id.progressbarofcreatenote);

        /*
        * Gets the info from FB
        */
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser(); // Will be added later where we want to add representatives for each university

        /*
        * Gets the info from the filled fields (title, content & phone)
        */
        mSavePost.setOnClickListener(v -> {
            String title = mCreateTitleOfPost.getText().toString().trim();
            String content = mCreateContentOfPost.getText().toString().trim();
            String phone = mCreatePhoneOfPost.getText().toString().trim();

            /*
             * All fields are required to fill - if one of em are empty will throw a toast
             */
            if (title.isEmpty() || content.isEmpty() || phone.isEmpty()) {
                Toast.makeText(getApplicationContext(), "All fields are Required", Toast.LENGTH_SHORT).show();
            } else {
                // Creates the post animation
                mProgressBarOfCreatePost.setVisibility(View.VISIBLE);
                /*
                 * Adds the Post information save to FB - phone, content & title
                 */
                DocumentReference documentReference = firebaseFirestore.collection(uniName).document("All").collection("items").document();
                Map<String, Object> note = new HashMap<>();
                note.put("content", content);
                note.put("phone",phone);
                note.put("title", title);

                documentReference.set(note).addOnSuccessListener(aVoid -> {
                    Toast.makeText(getApplicationContext(), "Post Created Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = null; // Declare the intent once so we use it more efficiently instead of starting it every time we want to move into another university board
                    switch (uniName) {
                        case "AR":
                            intent = new Intent(AddProducts.this, ArielUniversity.class);
                            break;
                        case "BGU":
                            intent = new Intent(AddProducts.this, BenGurion.class);
                            break;
                        case "Haifa":
                            intent = new Intent(AddProducts.this, HaifaUni.class);
                            break;
                        case "Reicman":
                            intent = new Intent(AddProducts.this, ReichmanUni.class);
                            break;
                        case "Technion":
                            intent = new Intent(AddProducts.this, TechnionUni.class);
                            break;
                        case "tlv":
                            intent = new Intent(AddProducts.this, TelAvivUni.class);
                            break;
                        case "heb":
                            intent = new Intent(AddProducts.this, HebrewUni.class);
                    }
                    assert intent != null;
                    intent.putExtra("isAdmin",true); // Anyway the user who adds the products is an admin
                    startActivity(intent);

                }).addOnFailureListener(e -> { // User failed to create the post
                    Toast.makeText(getApplicationContext(), "Failed To Create Post", Toast.LENGTH_SHORT).show();
                    mProgressBarOfCreatePost.setVisibility(View.INVISIBLE);
                });
            }
        });
    }

    /**
     * Will be used later probably (the built in back arrow button)
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    // Which board to return to
    public void backArrowAdd(View view){
        Intent intent = null;
        switch (uniName) {
            case "AR":
                intent = new Intent(AddProducts.this, ArielUniversity.class);
                break;
            case "BGU":
                intent = new Intent(AddProducts.this, BenGurion.class);
                break;
            case "Haifa":
                intent = new Intent(AddProducts.this, HaifaUni.class);
                break;
            case "Reicman":
                intent = new Intent(AddProducts.this, ReichmanUni.class);
                break;
            case "Technion":
                intent = new Intent(AddProducts.this, TechnionUni.class);
                break;
            case "tlv":
                intent = new Intent(AddProducts.this, TelAvivUni.class);
                break;
            case "heb":
                intent = new Intent(AddProducts.this, HebrewUni.class);
        }
        assert intent != null;
        intent.putExtra("isAdmin",true); // User who adds the post is an admin
        startActivity(intent);
    }


}