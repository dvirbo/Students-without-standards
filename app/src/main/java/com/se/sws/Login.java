package com.se.sws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class Login extends AppCompatActivity {

    public boolean flag = true;
    EditText email,password;
    Button loginBtn,gotoRegister;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId; // Current user
    /*
    Called when the activity is starting, where most initialization go:
    init the firebase store & auth
    */

    /**
     * Called when the activity is starting, where most initialization go:
     * init the firebase store & auth
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);
        gotoRegister = findViewById(R.id.gotoRegister);

        loginBtn.setOnClickListener(v -> {
            checkField(email);
            checkField(password);

            if (valid){
                fAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Login.this,"Logged Successfully",Toast.LENGTH_SHORT).show();
                        checkUserAccessLevel(Objects.requireNonNull(authResult.getUser()).getUid());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        gotoRegister.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),Register.class)));
    }

    /**
     * This method check if the user is sigh as admin or student
     * if admin -> show admin page
     * else -> show user page
     */
    private void checkUserAccessLevel(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        //extract data from the document
        df.get().addOnSuccessListener(documentSnapshot -> {
            Log.d("TAG","onSuccess" + documentSnapshot.getData());
            this.userId = uid; // gets the user id
            //identify the access level
            if (documentSnapshot.getString("isUser") != null){
                flag = false; // User is a simple user and not an admin
            }
            Intent intent = new Intent(getApplicationContext(), Universities.class);
            intent.putExtra("isAdmin",flag);
            intent.putExtra("uid", userId);
            startActivity(intent);
            finish();
        });
    }

    public void checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }else {
            valid = true;
        }

    }

    /**
     * This method check if the user is already sigh to the app
     * if true -> connect user to app and he don't need to verify -> move to admin/main class
     * else, move to login class
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            df.get().addOnSuccessListener(documentSnapshot -> {
                this.userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (documentSnapshot.getString("isUser") != null){
                    flag = false; // User does not have admin privileges
                }
                Intent intent = new Intent(getApplicationContext(), Universities.class);
                intent.putExtra("isAdmin",flag); // Pass the admin flag
                intent.putExtra("uid", this.userId); // Pass the user id
                startActivity(intent);
                finish();
            }).addOnFailureListener(e -> {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
            });
        }
    }

}