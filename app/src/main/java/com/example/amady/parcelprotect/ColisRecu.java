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

public class ColisRecu extends AppCompatActivity {

    private JSONArray RecuTab;

    final String selectAllColisRecu = "https://upmost-limps.000webhostapp.com/arona/selectAllColisRecu.php";


    @Override
    public void onCreate(Bundle savedInstanceState) {

        // ListView
        ListView colisRecu;

        final char idColis[] = new char[1];

        super.onCreate(savedInstanceState);
        setContentView(R.layout.colisrecu);

        //----------------------------------ColisEnvoye---------------------------//
        try {
            RecuTab = new JsonTaskColisRecu().execute(selectAllColisRecu).get();
        } catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
        }
        Colis ColisList[] = new Colis[RecuTab.length()];
        for (int i = 0; i < RecuTab.length(); i++) {
            JSONObject Colis;

            try {
                Colis = RecuTab.getJSONObject(i);
                int ColisID = Colis.getInt("idEnvoi");
                int statutLivraison = Colis.getInt("statutLivraison");

                ColisList[i] = new Colis(ColisID, statutLivraison);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        //essaie remplissage listview
        String[] id_colisrecu = new String[ColisList.length];
        for (int i = 0; i < ColisList.length; i++) {
            id_colisrecu[i] = ColisList[i].toString();
        }

        colisRecu = (ListView) findViewById(R.id.listrecu);

        final ArrayAdapter<String> listDesColisR = new ArrayAdapter<>(ColisRecu.this, android.R.layout.simple_list_item_1, id_colisrecu);
        colisRecu.setAdapter(listDesColisR);
        colisRecu.setItemsCanFocus(true);

        colisRecu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                ListView lv = (ListView) arg0;
                TextView tv = (TextView) lv.getChildAt(arg2);
                String infoHarnaisSelected = tv.getText().toString();
                idColis[0] = infoHarnaisSelected.charAt(3);
                Toast.makeText(ColisRecu.this, "L'id du harnais selectionné est :"+ idColis[0], Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ColisRecu.this, MapDeverrouiller.class);
                startActivity(intent);
            } });


    }
}
