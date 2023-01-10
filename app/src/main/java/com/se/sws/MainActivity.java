package com.se.sws;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    boolean flag;
    Intent _intent;
    boolean newMessage;
    private RelativeLayout move1;
    String uid;
    String UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _intent = getIntent();
        flag = _intent.getBooleanExtra("isAdmin", false);
        uid = _intent.getStringExtra("uid");
        newMessage = _intent.getBooleanExtra("newMsg",false);
        UserName = _intent.getStringExtra("UserName");


        /*
        For example here i chose to use the button by clicking on it, it will
        pass me to the AboutUs page, which gives details about our project.
         */
        move1 = findViewById(R.id.MenuInfoButton);
        //Moves you to the Information page
        move1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Here how it actually moves us from the Current page which is Universities
                CLASS to the InfoAboutUs CLASS
                 */
                Intent intent = new Intent(MainActivity.this , InfoActivity.class);
                intent.putExtra("isAdmin",flag);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });

        RelativeLayout uni = findViewById(R.id.MenuUniversitiesButton);
        uni.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this , UniversitiesActivity.class);
            intent.putExtra("isAdmin",flag);
            intent.putExtra("uid",uid);
            intent.putExtra("UserName", UserName);
            startActivity(intent);
        });

        // menu_search_button
        RelativeLayout search = findViewById(R.id.menu_search_button);
        search.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this , searchPostActivity.class);
            intent.putExtra("isAdmin",flag);
            intent.putExtra("uid",uid);
            intent.putExtra("UserName", UserName);
            startActivity(intent);
        });



        ImageView msg = findViewById(R.id.MenuMessageButtonImage);
        if (!newMessage){
            msg.setImageResource(R.drawable.ic_email_read_24);
            Toast.makeText(MainActivity.this,"No new messages!",Toast.LENGTH_SHORT).show();
        }else{
            msg.setImageResource(R.drawable.ic_email_unread_24);
            Toast.makeText(MainActivity.this,"New messages!",Toast.LENGTH_SHORT).show();
        }

        /*
         Add maybe?? vvv


        Here i chose to move us using the Button to the Contact page

        move1 = findViewById(R.id.contact);
        //Moves you to the Information page
        move1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Here how it actually moves us from the Universities class to the Contact class

                Intent intent = new Intent(Universities.this ,Contact.class);
                intent.putExtra("isAdmin",flag);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
         */
    }

    /*
    Here is the code line which logs the USER out to the Login class.
    */
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        // Called when the activity has detected the user's press of the back key.
        android.os.Process.killProcess(android.os.Process.myPid());
    }



}
