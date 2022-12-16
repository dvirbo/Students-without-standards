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
import com.se.sws.boards.ReichmanUni;
import com.se.sws.boards.TechnionUni;
import com.se.sws.boards.TelAvivUni;

import java.util.HashMap;
import java.util.Map;


public class AddProducts extends AppCompatActivity {
    EditText mcreatetitleofnote, mcreatecontentofnote;
    ImageView msavenote;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    Intent _intent;
    ProgressBar mprogressbarofcreatenote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

        _intent = getIntent();
        String uniName = _intent.getStringExtra("University");

        msavenote = findViewById(R.id.imageSave);
        mcreatecontentofnote = findViewById(R.id.inputNoteText);
        mcreatetitleofnote = findViewById(R.id.inputNoteTitle);

        mprogressbarofcreatenote = findViewById(R.id.progressbarofcreatenote);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        msavenote.setOnClickListener(v -> {
            String title = mcreatetitleofnote.getText().toString().trim();
            String content = mcreatecontentofnote.getText().toString().trim();
            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Both field are Require", Toast.LENGTH_SHORT).show();
            } else {

                mprogressbarofcreatenote.setVisibility(View.VISIBLE);

                DocumentReference documentReference = firebaseFirestore.collection(uniName).document(firebaseUser.getUid()).collection("myPosts").document();
                Map<String, Object> note = new HashMap<>();
                note.put("title", title);
                note.put("content", content);

                documentReference.set(note).addOnSuccessListener(aVoid -> {
                    Toast.makeText(getApplicationContext(), "Post Created Successfully", Toast.LENGTH_SHORT).show();
                    switch (uniName) {
                        case "AR":
                            startActivity(new Intent(AddProducts.this, ArielUniversity.class));
                            break;
                        case "BGU":
                            startActivity(new Intent(AddProducts.this, BenGurion.class));
                            break;
                        case "Haifa":
                            startActivity(new Intent(AddProducts.this, HaifaUni.class));
                            break;
                        case "Reicman":
                            startActivity(new Intent(AddProducts.this, ReichmanUni.class));
                            break;
                        case "Technion":
                            startActivity(new Intent(AddProducts.this, TechnionUni.class));
                            break;
                        case "tlv":
                            startActivity(new Intent(AddProducts.this, TelAvivUni.class));
                            break;
                    }

                }).addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Failed To Create Post", Toast.LENGTH_SHORT).show();
                    mprogressbarofcreatenote.setVisibility(View.INVISIBLE);
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


}