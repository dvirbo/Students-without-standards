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
import com.se.sws.boards.HaifaUni;
import com.se.sws.boards.HebrewUni;
import com.se.sws.boards.ReichmanUni;
import com.se.sws.boards.TechnionUni;
import com.se.sws.boards.TelAvivUni;

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


    public void UniversitiesBoard(View view){
        Intent intent = null;

        if (view == findViewById(R.id.ariel_uni)){  // ariel:
            intent = new Intent(getApplicationContext(), ArielUniversity.class);
        }
        else if(view == findViewById(R.id.ben_gurion_uni)){ // BGU:
            intent = new Intent(getApplicationContext(), BenGurion.class);
        }
        else if(view == findViewById(R.id.tel_aviv_uni)){  //TLV
            intent = new Intent(getApplicationContext(), TelAvivUni.class);
        }
        else if(view == findViewById(R.id.technion_uni)){ // tecnion
            intent = new Intent(getApplicationContext(), TechnionUni.class);
        }
        else if(view == findViewById(R.id.reichman_uni)){ // reichman
            intent = new Intent(getApplicationContext(), ReichmanUni.class);
        }
        else if(view == findViewById(R.id.haifa_uni)){ // haifa
            intent = new Intent(getApplicationContext(), HaifaUni.class);
        }
        else if(view == findViewById(R.id.hebrew_uni)){
            intent = new Intent(getApplicationContext(), HebrewUni.class);
        }

        assert intent != null;
        intent.putExtra("isAdmin",flag);
        startActivity(intent);


    }


}