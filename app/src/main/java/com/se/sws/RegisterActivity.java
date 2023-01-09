package com.se.sws;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represent a registration to our app
 */
public class RegisterActivity extends AppCompatActivity {
    EditText fullName, email, password, phone; // relevant data
    Button registerBtn, goToLogin;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    CheckBox isAdminBox, isStudentBox; // where user sing as admin / student

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        fullName = findViewById(R.id.registerName);
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerPassword);
        phone = findViewById(R.id.registerPhone);
        registerBtn = findViewById(R.id.registerBtn);
        goToLogin = findViewById(R.id.gotoLogin);

        isAdminBox = findViewById(R.id.isAdmin);
        isStudentBox = findViewById(R.id.isStudent);

        /*
        check if user is admin or student
        by Register a callback to be invoked when the checked state of this button changes.
         */
        isStudentBox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (compoundButton.isChecked()) {
                isAdminBox.setChecked(false);
            }
        });

        isAdminBox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (compoundButton.isChecked()) {
                isStudentBox.setChecked(false);
            }
        });

        registerBtn.setOnClickListener(v -> {
            checkField(fullName);
            checkField(email);
            checkField(password);
            checkField(phone);

            //checkbox confirmation
            if (!(isAdminBox.isChecked() || isStudentBox.isChecked())) {
                Toast.makeText(RegisterActivity.this, "Select the Account Type", Toast.LENGTH_SHORT).show();
                return;
            }

            if (valid) { // if valid -> create userInfo (HashMap) that contain the relevant data
                fAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(authResult -> {
                    FirebaseUser user = fAuth.getCurrentUser();
                    Toast.makeText(RegisterActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                    assert user != null;
                    DocumentReference df = fStore.collection("Users").document(user.getUid());
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("FullName", fullName.getText().toString());
                    userInfo.put("UserEmail", email.getText().toString());
                    userInfo.put("PhoneNumber", phone.getText().toString());
                    //specify admin
                    if (isAdminBox.isChecked()) {
                        userInfo.put("isAdmin", "1");
                    }
                    if (isStudentBox.isChecked()) {
                        userInfo.put("isUser", "1");
                    }

                    df.set(userInfo);
                    Intent _intent;
                    _intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(_intent);
                    finish();

                }).addOnFailureListener(e -> Toast.makeText(RegisterActivity.this, "Failed to Create Account", Toast.LENGTH_SHORT).show());
            }
        });

        goToLogin.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));

    }



    /**
     * This method check if the data is valid
     * @param textField - text data - contain our data that need to load to DB
     */
    public void checkField(EditText textField) {
        if (textField.getText().toString().isEmpty()) {
            textField.setError("Error");
            valid = false;
        } else {
            valid = true;
        }

    }
}