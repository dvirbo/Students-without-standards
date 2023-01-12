package com.se.sws;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class searchPostActivity extends AppCompatActivity {

    EditText institution, description;
    Button search;
    Button back;
    boolean flag;
    Intent _intent;
    private String uid;
    private String UserName;
    private String description_user;
    private String ins; // which institution to go after press Button
    private  String from;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post);

        institution = findViewById(R.id.loginEmail);
        description = findViewById(R.id.loginPassword);
        search = findViewById(R.id.accessibility_custom_action_0);
        from = "search_post";
        _intent = getIntent();
        flag = _intent.getBooleanExtra("isAdmin", false);
        uid = _intent.getStringExtra("uid");
        UserName = _intent.getStringExtra("UserName");

        back = findViewById(R.id.go_back);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(searchPostActivity.this, MainActivity.class);
            intent.putExtra("isAdmin", flag);
            intent.putExtra("uid", uid);
            intent.putExtra("UserName", UserName);
            startActivity(intent);
        });


        search.setOnClickListener(view -> {
                    ins = institution.getText().toString();
                    description_user = description.getText().toString();

                    if (ins.isEmpty()) {
                        ins = "BackUp";
                    } else {
                        switch (ins) {
                            case "ariel":
                                ins = "AR";
                                break;
                            case "ben gurion":
                                ins = "BGU";
                                break;
                            case "haifa":
                                ins = "Haifa";
                                break;
                            case "Reicman":
                                ins = "Reicman";
                                break;
                            case "Technion":
                                ins = "Technion";
                                break;
                            case "tel aviv":
                                ins = "tlv";
                                break;
                            case "hebrew":
                                ins = "heb";
                                break;
                            default:
                                ins = "BackUp";


                        }
                        System.out.println(ins);
                    }

                    if (description_user.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "add a query to the search", Toast.LENGTH_SHORT).show();
                    }


                    Intent intent = new Intent(searchPostActivity.this, InstitutionsActivity.class);
                    intent.putExtra("isAdmin", flag);
                    intent.putExtra("uid", uid);
                    intent.putExtra("UserName", UserName);
                    intent.putExtra("ins", ins);
                    intent.putExtra("desc", description_user);
                    System.out.println("desc" + description_user);
                    System.out.println("ins" + ins);
                    System.out.println("end search");
                    intent.putExtra("from", from);
                    startActivity(intent);

                }


        );


    }


}