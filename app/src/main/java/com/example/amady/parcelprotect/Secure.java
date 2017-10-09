package com.example.amady.parcelprotect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by Amady on 06/10/2017.
 */

public class Secure extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secure);

        // ListView
        ListView listeHarnais = (ListView) findViewById(R.id.listeharnais);
        ListView listeDestinataire = (ListView) findViewById(R.id.listedestinataire);
        // Boutton de l'interface choix
        Button btOk = (Button) findViewById(R.id.buttOk);
        // démmarage de l'activité interface choix pour
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Secure.this, InterfaceChoix.class);
                startActivity(intent);
            }
        });

    }
}
