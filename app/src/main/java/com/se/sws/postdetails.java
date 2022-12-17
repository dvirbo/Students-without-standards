package com.se.sws;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.se.sws.boards.ArielUniversity;
import com.se.sws.boards.BenGurion;
import com.se.sws.boards.HaifaUni;
import com.se.sws.boards.HebrewUni;
import com.se.sws.boards.ReichmanUni;
import com.se.sws.boards.TechnionUni;
import com.se.sws.boards.TelAvivUni;

public class postdetails extends AppCompatActivity {

    private TextView mtitleofnotedetail,mcontentofnotedetail, mphoneofnotedetail;
    private String university;
    private Boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postdetails);
        mtitleofnotedetail=findViewById(R.id.titleofnotedetail);
        mcontentofnotedetail=findViewById(R.id.contentofnotedetail);
        mphoneofnotedetail = findViewById(R.id.phoneofnotedetail);
        Intent data=getIntent();

        // Fill every detail on screen
        mcontentofnotedetail.setText(data.getStringExtra("content"));
        mtitleofnotedetail.setText(data.getStringExtra("title"));
        mphoneofnotedetail.setText(data.getStringExtra("phone"));

        // Will be used in back-arrow function (which board to return)
        this.university = data.getStringExtra("university");
        this.flag = data.getBooleanExtra("isAdmin",false);
    }

    // Which board to return to
    public void backArrow(View view){
        Intent intent = null;
        switch (university) {
            case "AR":
                intent = new Intent(postdetails.this, ArielUniversity.class);
                break;
            case "BGU":
                intent = new Intent(postdetails.this, BenGurion.class);
                break;
            case "Haifa":
                intent = new Intent(postdetails.this, HaifaUni.class);
                break;
            case "Reicman":
                intent = new Intent(postdetails.this, ReichmanUni.class);
                break;
            case "Technion":
                intent = new Intent(postdetails.this, TechnionUni.class);
                break;
            case "tlv":
                intent = new Intent(postdetails.this, TelAvivUni.class);
                break;
            case "heb":
                intent = new Intent(postdetails.this, HebrewUni.class);
        }
        assert intent != null;
        intent.putExtra("isAdmin",flag); // If the user who clicked on the posts is an admin or not
        startActivity(intent);
    }


}