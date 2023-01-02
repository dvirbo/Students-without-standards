package com.se.sws;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class InfoAboutUs extends AppCompatActivity {

    /**
     * This class show information's about our project.
     * By the help of the tutorial: https://www.youtube.com/watch?v=tZ2YEw6SoBU&t=520s&ab_channel=CodinginFlow
     */

    private Button move2;
    private String uid; // Current user id from login activity
    /*
    I've added an instance of a Button which will help me go back to the Universities page
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_about_us);
        boolean flag = getIntent().getBooleanExtra("isAdmin",false);
        uid = getIntent().getStringExtra("uid");
        /*
        This is the declaration for the button id previous which once clicking on it,
        it brings the USER back to the Universities page.
         */
        move2 = findViewById(R.id.prev);

        //Moves you to the Login page
        move2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Here by valuing the Intent object to bring us back to the Universities class
                 */
                Intent intent = new Intent(InfoAboutUs.this ,MainMenu.class);

                intent.putExtra("isAdmin",flag);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
    }
}