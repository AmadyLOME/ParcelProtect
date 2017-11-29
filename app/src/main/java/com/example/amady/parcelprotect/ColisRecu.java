package com.example.amady.parcelprotect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Amady on 05/10/2017.
 */

public class ColisRecu extends AppCompatActivity {
    // variable definissant les listview
    private ListView listrecus;

    //essaie remplissage listview
    private String[] id_harnaisrecus = new String[]{
            "000001","000002","000003"
    };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colisrecu);

        listrecus = (ListView) findViewById(R.id.listrecu);

        final ArrayAdapter<String> adapterecus = new ArrayAdapter<String>(ColisRecu.this,android.R.layout.simple_list_item_1,id_harnaisrecus);
        listrecus.setAdapter(adapterecus);

    }
}
