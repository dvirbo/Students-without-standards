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
/**
Firebase User Management Tutorial | Admin / Normal user Management:
https://www.youtube.com/playlist?list=PLlGT4GXi8_8dGFSIM_nM4eMSVhXVF7cgp
 */

public class LoginActivity extends AppCompatActivity {

    public boolean flag = true;
    EditText email,password;
    Button loginBtn,gotoRegister;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
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
                        Toast.makeText(LoginActivity.this,"You have been logged Successfully",Toast.LENGTH_SHORT).show();
                        checkUserAccessLevel(Objects.requireNonNull(authResult.getUser()).getUid());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        gotoRegister.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RegisterActivity.class)));
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
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            if (documentSnapshot.getString("isUser") != null){
                flag = false; // User does not have admin privileges
            }
            intent.putExtra("isAdmin",flag);
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
        if (FirebaseAuth.getInstance().getCurrentUser() != null){ // Returns the currently signed-in FirebaseUser or null if there is none.
            DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            df.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.getString("isUser") != null){
                    flag = false; // User does not have admin privileges
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("isAdmin",flag);
                startActivity(intent);
                finish();
            }).addOnFailureListener(e -> {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            });
        }
    }

}