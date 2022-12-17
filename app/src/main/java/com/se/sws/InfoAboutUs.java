package com.se.sws;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InfoAboutUs extends AppCompatActivity {

    private Button move2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_about_us);
        move2 = findViewById(R.id.prev);

        //Moves you to the Login page
        move2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoAboutUs.this ,Universities.class);
                startActivity(intent);
            }
        });
    }
}