package com.se.sws;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Not being used, only to write & test some functions maybe later will
 */
public class AdminPanel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    public void logoutAdmin(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    public void getStats(View view){
        // messaging system via Firebase code here <<<<<
        startActivity(new Intent(getApplicationContext(),Stats.class));
    }

    /**
     * addItem -> once clicked will direct us to Universities class
     * @param view
     */
    public void addItem(View view){
        // messaging system via Firebase code here <<<<<
        startActivity(new Intent(getApplicationContext(),Universities.class));
    }
}