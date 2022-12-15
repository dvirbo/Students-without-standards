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

public class Universities extends AppCompatActivity {
    boolean flag;
    Intent _intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universities);

        _intent = getIntent();
        flag = _intent.getBooleanExtra("isAdmin", false);

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

    public void logoutAdmin(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    public void arielUniversity(View view){
        Intent intent = new Intent(getApplicationContext(), ArielUniversity.class);
        intent.putExtra("isAdmin",flag);
        startActivity(intent);
    }

    public void UniversitiesBoard(View view){
        Intent intent;
        View temp_ariel = findViewById(R.id.ariel_uni);
        View temp_ben_gurion = findViewById(R.id.ben_gurion_uni);
        if (view == findViewById(R.id.ariel_uni)){
            intent = new Intent(getApplicationContext(), ArielUniversity.class);
            intent.putExtra("isAdmin",flag);
            startActivity(intent);
        }else if(view == findViewById(R.id.ben_gurion_uni)){
            intent = new Intent(getApplicationContext(), BenGurion.class);
            intent.putExtra("isAdmin",flag);
            startActivity(intent);
        }
        /**
         * TODO:
         *  Add more else if with the rest of Universities
         */
//        Intent intent = new Intent(getApplicationContext(), ArielUniversity.class);
//        intent.putExtra("isAdmin",flag);

    }


}