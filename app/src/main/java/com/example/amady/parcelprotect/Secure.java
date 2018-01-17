package com.example.amady.parcelprotect;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by Amady on 06/10/2017.
 */

public class Secure extends AppCompatActivity {

    // variable definissant les listview
    private ListView listenvoyes;

    private JSONArray HarnaisTab;

    // harnais user login url
    final String showHarnais = "https://upmost-limps.000webhostapp.com/arona/showHarnais.php";


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secure);

        // ListView
        ListView listeHarnais;
        ListView listeDestinataire = (ListView) findViewById(R.id.listedestinataire);

        // EditText
        EditText codeDesactivationAppWeb = (EditText) findViewById(R.id.codedesactwebapp);
        // Boutton de l'interface choix
        Button btOk = (Button) findViewById(R.id.buttOk);
        // démmarage de l'activité interface choix pour
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog("Succès","Votre Colis est en cours d'envoie\n Le code est envoyé au destinataire");
            }
        });

        try {
            HarnaisTab = new JsonTask().execute(showHarnais).get();
        } catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
        }
        Harnais harnaisList[] = new Harnais[HarnaisTab.length()];
        for (int i = 0; i < HarnaisTab.length(); i++) {
            JSONObject Harnais;

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

        }
        //essaie remplissage listview
        String[] id_harnaisenvoyes = new String[harnaisList.length];
        for (int i = 0; i < harnaisList.length; i++) {
            id_harnaisenvoyes[i] = harnaisList[i].toString();
        }

        listeHarnais = (ListView) findViewById(R.id.listeharnais);

        final ArrayAdapter<String> listDesHarnais = new ArrayAdapter<>(Secure.this, android.R.layout.simple_list_item_1, id_harnaisenvoyes);
        listeHarnais.setAdapter(listDesHarnais);
        listeHarnais.setItemsCanFocus(true);
    }

    private void createDialog(String title, String text)
    {
        // Création d'un popup affichant un message
        AlertDialog ad = new AlertDialog.Builder(this)
                .setPositiveButton("Ok", null).setTitle(title).setMessage(text)
                .create();
        ad.show();
    }
}
