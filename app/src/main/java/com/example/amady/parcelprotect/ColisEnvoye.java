package com.example.amady.parcelprotect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by Amady on 05/10/2017.
 */

public class ColisEnvoye extends AppCompatActivity {

    private JSONArray EnvoiTab;

    final String selectAllColisEnvoye = "https://upmost-limps.000webhostapp.com/arona/selectAllColisEnvoye.php";


    @Override
    public void onCreate(Bundle savedInstanceState) {

        // ListView
        ListView colisEnvoye;

        final char idColis[] = new char[1];

        super.onCreate(savedInstanceState);
        setContentView(R.layout.colisenvoye);

        //----------------------------------ColisEnvoye---------------------------//
        try {
            EnvoiTab = new JsonTaskColisEnvoye().execute(selectAllColisEnvoye).get();
        } catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
        }
        Colis ColisList[] = new Colis[EnvoiTab.length()];
        for (int i = 0; i < EnvoiTab.length(); i++) {
            JSONObject Colis;

            try {
                Colis = EnvoiTab.getJSONObject(i);
                int ColisID = Colis.getInt("idEnvoi");
                int statutLivraison = Colis.getInt("statutLivraison");

                ColisList[i] = new Colis(ColisID, statutLivraison);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        //essaie remplissage listview
        String[] id_colisenvoye = new String[ColisList.length];
        for (int i = 0; i < ColisList.length; i++) {
            id_colisenvoye[i] = ColisList[i].toString();
        }

        colisEnvoye = (ListView) findViewById(R.id.listenvoyé);

        final ArrayAdapter<String> listDesColis = new ArrayAdapter<>(ColisEnvoye.this, android.R.layout.simple_list_item_1, id_colisenvoye);
        colisEnvoye.setAdapter(listDesColis);
        colisEnvoye.setItemsCanFocus(true);

        colisEnvoye.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                ListView lv = (ListView) arg0;
                TextView tv = (TextView) lv.getChildAt(arg2);
                String infoHarnaisSelected = tv.getText().toString();
                idColis[0] = infoHarnaisSelected.charAt(3);
                Toast.makeText(ColisEnvoye.this, "L'id du harnais selectionné est :"+ idColis[0], Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ColisEnvoye.this, MapDeverrouiller.class);
                startActivity(intent);
            } });


    }
}

