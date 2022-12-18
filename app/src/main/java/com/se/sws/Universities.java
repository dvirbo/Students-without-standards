package com.se.sws;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.se.sws.boards.ArielUniversity;
import com.se.sws.boards.BenGurion;
import com.se.sws.boards.HaifaUni;
import com.se.sws.boards.HebrewUni;
import com.se.sws.boards.ReichmanUni;
import com.se.sws.boards.TechnionUni;
import com.se.sws.boards.TelAvivUni;

public class Universities extends AppCompatActivity {
    boolean flag;
    Intent _intent;
    private Button move1;

    /*
    Here i've added a Button which will move me from certain screen to another screen
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universities);

        _intent = getIntent();
        flag = _intent.getBooleanExtra("isAdmin", false);

        /*
        For example here i chose to use the button by clicking on it, it will
        pass me to the AboutUs page, which gives details about our project.
         */
        move1 = findViewById(R.id.info);
        //Moves you to the Information page
        move1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Here how it actually moves us from the Current page which is Univesities
                CLASS to the InfoAboutUs CLASS
                 */
                Intent intent = new Intent(Universities.this ,InfoAboutUs.class);
                startActivity(intent);
            }
        });
        /*
        Here i chose to move us using the Button to the Contact page
         */
        move1 = findViewById(R.id.contact);
        //Moves you to the Information page
        move1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Here how it actually moves us from the Universities class to the Contact class
                 */
                Intent intent = new Intent(Universities.this ,Contact.class);
                startActivity(intent);
            }
        });

/**     TODO:
        --button visibility for later user--
        Button _button1 = (Button)findViewById(R.id.ariel_uni);
        if(flag == false){
            _button1.setVisibility(View.GONE);
        }else{
            _button1.setVisibility(View.VISIBLE);
        }
 */
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
    public void UniversitiesBoard(View view){
        /*
        To move us from one activity to another activity we use INTENT
        in other words, it helps us communicate between two activities inside the same app

         */
        Intent intent = null;
        //If the button pressed is of ariel univesity , invitialize the intent
        //to be ariel
        if (view == findViewById(R.id.ariel_uni)){  // ariel:
            intent = new Intent(getApplicationContext(), ArielUniversity.class);
        }
        //If the button pressed is of ariel univesity , invitialize the intent
        //to be Ben-Gurion
        else if(view == findViewById(R.id.ben_gurion_uni)){ // BGU:
            intent = new Intent(getApplicationContext(), BenGurion.class);
        }
        //If the button pressed is of ariel univesity , invitialize the intent
        //to be Tel-aviv
        else if(view == findViewById(R.id.tel_aviv_uni)){  //TLV
            intent = new Intent(getApplicationContext(), TelAvivUni.class);
        }
        //If the button pressed is of ariel univesity , invitialize the intent
        //to be Technion
        else if(view == findViewById(R.id.technion_uni)){ // tecnion
            intent = new Intent(getApplicationContext(), TechnionUni.class);
        }
        //If the button pressed is of ariel univesity , invitialize the intent
        //to be Reichman
        else if(view == findViewById(R.id.reichman_uni)){ // reichman
            intent = new Intent(getApplicationContext(), ReichmanUni.class);
        }
        //If the button pressed is of ariel univesity , invitialize the intent
        //to be Haifa
        else if(view == findViewById(R.id.haifa_uni)){ // haifa
            intent = new Intent(getApplicationContext(), HaifaUni.class);
        }
        //If the button pressed is of ariel univesity , invitialize the intent
        //to be Hebrew
        else if(view == findViewById(R.id.hebrew_uni)){
            intent = new Intent(getApplicationContext(), HebrewUni.class);
        }


        assert intent != null;
        intent.putExtra("isAdmin",flag);
        startActivity(intent);


    }


}