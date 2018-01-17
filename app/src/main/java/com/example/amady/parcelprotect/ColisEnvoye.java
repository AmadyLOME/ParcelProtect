package com.example.amady.parcelprotect;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.ExecutionException;

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
    private JSONArray HarnaisTab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // harnais user login url
        final String showHarnais = "https://upmost-limps.000webhostapp.com/arona/showHarnais.php";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.colisenvoye);

        try {
            HarnaisTab = new JsonTask().execute(showHarnais).get();
        } catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
        }
        Harnais harnaisList[] = new Harnais[HarnaisTab.length()];
        for (int i = 0; i < HarnaisTab.length(); i++) {
            JSONObject Harnais = null;

            try {
                Harnais = HarnaisTab.getJSONObject(i);
                int HarnaisID = Harnais.getInt("idHarnais");
                int HarnaisQrCode = Harnais.getInt("QRcode");
                int HarnaisAdresseMac = Harnais.getInt("adresseMAC");
                int HarnaisStatus = Harnais.getInt("status");

                harnaisList[i] = new Harnais(HarnaisID, HarnaisQrCode, HarnaisAdresseMac, HarnaisStatus);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("TAG", "onPostExecute: " + harnaisList[0].getHarnaisId());
            Log.d("TAG", "onPostExecute: " + harnaisList[1].getHarnaisQrCode());
        }

        listenvoyes = (ListView) findViewById(R.id.listenvoyé);

        final ArrayAdapter<String> adapterenvoyes = new ArrayAdapter<String>(ColisEnvoye.this, android.R.layout.simple_list_item_1, id_harnaisenvoyes);
        listenvoyes.setAdapter(adapterenvoyes);
    }
}

