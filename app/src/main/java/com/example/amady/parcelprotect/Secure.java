package com.example.amady.parcelprotect;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        //essaie remplissage listview
        String[] harnais = new String[]{
                "000001","000002","000003"
        };
        String[] destinataire = new String[]{
                "000001","000002","000003"
        };

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
