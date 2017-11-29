package com.example.amady.parcelprotect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.app.Fragment;

/**
 * Created by Amady on 05/10/2017.
 */

public class MapDeverrouiller extends AppCompatActivity {

    private GoogleMap mMap;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapdeverouiller);

        // Boutton deverouiller
        Button btdeverrouiller = (Button) findViewById(R.id.deverouiller);
        // Boutton MAP
        Button btMAP = (Button) findViewById(R.id.map);

        btdeverrouiller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapDeverrouiller.this, QrCode.class);
                startActivity(intent);
            }
        });

        btMAP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapDeverrouiller.this, Map.class);
                startActivity(intent);
            }
        });

    }
}
