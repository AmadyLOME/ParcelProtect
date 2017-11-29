package com.example.amady.parcelprotect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class InterfaceChoix extends AppCompatActivity {
    private SessionManager session;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interfacechoix);

        // Boutton de l'interface choix
        Button btenvoye = (Button) findViewById(R.id.buttenvoye);
        Button btrecu = (Button) findViewById(R.id.buttrecu);
        Button btsuivi = (Button) findViewById(R.id.buttsuivi);

        btenvoye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InterfaceChoix.this, ColisEnvoye.class);
                startActivity(intent);
            }
        });
        btrecu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InterfaceChoix.this, ColisRecu.class);
                startActivity(intent);
            }
        });
        btsuivi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InterfaceChoix.this, MapDeverrouiller.class);
                startActivity(intent);
            }
        });
    }

}
