package com.se.sws.boards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.se.sws.AddProducts;
import com.se.sws.R;

public class TechnionUni extends AppCompatActivity {

    boolean flag;
    Intent _intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technion_uni);
        _intent = getIntent();
        flag = _intent.getBooleanExtra("isAdmin", false);

        ImageView imageAddItemMain = findViewById(R.id.imageAddPostMain_Technion);
        imageAddItemMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddProducts.class);
                intent.putExtra("University","Technion");
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






