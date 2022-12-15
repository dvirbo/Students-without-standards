package com.se.sws.boards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.se.sws.AddProducts;
import com.se.sws.R;

public class ArielUniversity extends AppCompatActivity {
    boolean flag;
    Intent _intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ariel_university);

        _intent = getIntent();
        flag = _intent.getBooleanExtra("isAdmin", false);

        ImageView imageAddItemMain = findViewById(R.id.imageAddPostMain);
        imageAddItemMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddProducts.class);
                startActivity(intent);
            }
        });

        if (flag) {
            imageAddItemMain.setVisibility(View.VISIBLE);
        } else {
            imageAddItemMain.setVisibility(View.GONE);
        }
    }


}