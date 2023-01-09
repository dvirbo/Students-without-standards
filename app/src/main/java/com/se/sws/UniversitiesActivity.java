package com.se.sws;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * This Class represent the page of all the universities that exist in our DB
 * by click on each Uni we get to the desire board
 */
public class UniversitiesActivity extends AppCompatActivity {
    boolean flag;
    Intent _intent;
    private Button move1;
    String uid;
    String UserName;
    String ins; // which institution to go after press Button

    /*
    Here i've added a Button which will move me from certain screen to another screen
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universities);

        _intent = getIntent();
        flag = _intent.getBooleanExtra("isAdmin", false);
        uid = _intent.getStringExtra("uid");
        UserName = _intent.getStringExtra("UserName");

        Button back = findViewById(R.id.go_back);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(UniversitiesActivity.this, MainActivity.class);
            intent.putExtra("isAdmin", flag);
            intent.putExtra("uid", uid);
            intent.putExtra("UserName", UserName);
            startActivity(intent);
        });



    }

    /*
    Here is the code line which logs the USER out to the Login class.
     */
    public void logoutAdmin(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    /*
    Moves us to the picked university
     */
    public void UniversitiesBoard(View view) {
        /*
        To move us from one activity to another activity we use INTENT
        in other words, it helps us communicate between two activities inside the same app

         */
        Intent intent = null;

        //If the button pressed is of ariel university , initialize the intent
        if (view == findViewById(R.id.ariel_uni)) {  // ariel:
            ins = "AR";

        }
        //If the button pressed is of Ben-Gurion university , initialize the intent
        else if (view == findViewById(R.id.ben_gurion_uni)) { // BGU:
            ins = "BGU";
        }
        //If the button pressed is of Tel-aviv university , initialize the intent
        else if (view == findViewById(R.id.tel_aviv_uni)) {  //TLV
            ins = "tlv";
        }
        //If the button pressed is of Technion , initialize the intent
        else if (view == findViewById(R.id.technion_uni)) { // tecnion
            ins = "Technion";
        }
        //If the button pressed is of Reichman , initialize the intent
        else if (view == findViewById(R.id.reichman_uni)) { // reichman
            ins = "Reicman";
        }
        //If the button pressed is of Haifa , initialize the intent
        else if (view == findViewById(R.id.haifa_uni)) { // haifa
            ins = "Haifa";
        }
        //If the button pressed is of hebrew university , initialize the intent
        else if (view == findViewById(R.id.hebrew_uni)) {
            ins = "heb";
        }

        intent = new Intent(getApplicationContext(), InstitutionsActivity.class);

        intent.putExtra("isAdmin", flag); // Whether the user is an admin or not
        intent.putExtra("uid", uid);
        intent.putExtra("UserName", UserName);
        intent.putExtra("ins", ins);
        startActivity(intent);


    }


}