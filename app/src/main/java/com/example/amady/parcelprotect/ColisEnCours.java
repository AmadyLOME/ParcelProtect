package com.example.amady.parcelprotect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by Amady on 29/11/2017.
 */

public class ColisEnCours extends AppCompatActivity {

    // variable definissantles listview
    private ListView listreception;
    private ListView listenvoie;

    //essaie remplissage listview
    private String[] id_harnaisenvoie = new String[]{
            "000001","000002","000003"
    }; 

    private String[] id_harnaisreception = new String[]{
            "000001","000002","000003"
    };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colisencours);

        listreception = (ListView) findViewById(R.id.listreception);
        listenvoie = (ListView) findViewById(R.id.listenvoie);

        final ArrayAdapter<String> adapterreception = new ArrayAdapter<String>(ColisEnCours.this,android.R.layout.simple_list_item_1,id_harnaisreception);
        listreception.setAdapter(adapterreception);

        final ArrayAdapter<String> adapterenvoie = new ArrayAdapter<String>(ColisEnCours.this,android.R.layout.simple_list_item_1,id_harnaisenvoie);
        listenvoie.setAdapter(adapterenvoie);

    }
}
