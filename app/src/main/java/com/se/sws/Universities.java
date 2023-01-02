package com.se.sws;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.se.sws.boards.ArielUniversity;
import com.se.sws.boards.BenGurion;
import com.se.sws.boards.HaifaUni;
import com.se.sws.boards.HebrewUni;
import com.se.sws.boards.ReichmanUni;
import com.se.sws.boards.TechnionUni;
import com.se.sws.boards.TelAvivUni;

/**
 * This Class represent the page of all the universities that exist in our DB
 * by click on each Uni we get to the desire board...
 */
public class Universities extends AppCompatActivity {
    boolean flag;
    Intent _intent;
    private Button move1;
    String uid;
    String UserName;


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
            Intent intent = new Intent(Universities.this, MainMenu.class);
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
        startActivity(new Intent(getApplicationContext(), Login.class));
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
        //to be ariel
        if (view == findViewById(R.id.ariel_uni)) {  // ariel:
            intent = new Intent(getApplicationContext(), ArielUniversity.class);
        }
        //If the button pressed is of ariel university , initialize the intent
        //to be Ben-Gurion
        else if (view == findViewById(R.id.ben_gurion_uni)) { // BGU:
            intent = new Intent(getApplicationContext(), BenGurion.class);
        }
        //If the button pressed is of ariel university , initialize the intent
        //to be Tel-aviv
        else if (view == findViewById(R.id.tel_aviv_uni)) {  //TLV
            intent = new Intent(getApplicationContext(), TelAvivUni.class);
        }
        //If the button pressed is of ariel university , initialize the intent
        //to be Technion
        else if (view == findViewById(R.id.technion_uni)) { // tecnion
            intent = new Intent(getApplicationContext(), TechnionUni.class);
        }
        //If the button pressed is of ariel university , initialize the intent
        //to be Reichman
        else if (view == findViewById(R.id.reichman_uni)) { // reichman
            intent = new Intent(getApplicationContext(), ReichmanUni.class);
        }
        //If the button pressed is of ariel university , initialize the intent
        //to be Haifa
        else if (view == findViewById(R.id.haifa_uni)) { // haifa
            intent = new Intent(getApplicationContext(), HaifaUni.class);
        }
        //If the button pressed is of ariel university , initialize the intent
        else if (view == findViewById(R.id.hebrew_uni)) {
            intent = new Intent(getApplicationContext(), HebrewUni.class);
        }


        assert intent != null;
        intent.putExtra("isAdmin", flag); // Whether the user is an admin or not
        intent.putExtra("uid", uid);
        intent.putExtra("UserName", UserName);
        startActivity(intent);


    }


}