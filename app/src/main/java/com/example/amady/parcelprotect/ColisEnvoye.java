package com.example.amady.parcelprotect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by Amady on 05/10/2017.
 */

public class ColisEnvoye extends AppCompatActivity {
    // variable definissant les listview
    private ListView listenvoyes;

    //essaie remplissage listview
    private String[] id_harnaisenvoyes = new String[]{
            "000001","000002","000003"
    };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colisenvoye);

        listenvoyes = (ListView) findViewById(R.id.listenvoyé);

        final ArrayAdapter<String> adapterenvoyes = new ArrayAdapter<String>(ColisEnvoye.this,android.R.layout.simple_list_item_1,id_harnaisenvoyes);
        listenvoyes.setAdapter(adapterenvoyes);

    }
}
