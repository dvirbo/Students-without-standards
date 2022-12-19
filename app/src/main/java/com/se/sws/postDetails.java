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

/**
 * Whenever we extend a post we use this class
 */
public class postDetails extends AppCompatActivity {

    private TextView mTitleOfPostDetail, mContentOfPostDetail, mPhoneOfPostDetail;
    private String university; // The university pressed on when entering universities
    private Boolean flag; // is the user admin or not

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postdetails);
        // Gets the id of 3 TextViews in the XML file
        mTitleOfPostDetail = findViewById(R.id.titleofnotedetail);
        mContentOfPostDetail = findViewById(R.id.contentofnotedetail);
        mPhoneOfPostDetail = findViewById(R.id.phoneofnotedetail);
        Intent data=getIntent(); // instead of writing getIntent.getStringExtra, getBooleanExtra etc...

        // Fill every detail on screen
        mContentOfPostDetail.setText(data.getStringExtra("content"));
        mTitleOfPostDetail.setText(data.getStringExtra("title"));
        mPhoneOfPostDetail.setText(data.getStringExtra("phone"));

        // Will be used in back-arrow function (which board to return)
        this.university = data.getStringExtra("university");
        this.flag = data.getBooleanExtra("isAdmin",false);
    }

    // Which board to return to
    public void backArrow(View view){
        Intent intent = null;
        switch (university) {
            case "AR":
                intent = new Intent(postDetails.this, ArielUniversity.class);
                break;
            case "BGU":
                intent = new Intent(postDetails.this, BenGurion.class);
                break;
            case "Haifa":
                intent = new Intent(postDetails.this, HaifaUni.class);
                break;
            case "Reicman":
                intent = new Intent(postDetails.this, ReichmanUni.class);
                break;
            case "Technion":
                intent = new Intent(postDetails.this, TechnionUni.class);
                break;
            case "tlv":
                intent = new Intent(postDetails.this, TelAvivUni.class);
                break;
            case "heb":
                intent = new Intent(postDetails.this, HebrewUni.class);
        }
        assert intent != null;
        intent.putExtra("isAdmin",flag); // If the user who clicked on the posts is an admin or not
        startActivity(intent);
    }


}