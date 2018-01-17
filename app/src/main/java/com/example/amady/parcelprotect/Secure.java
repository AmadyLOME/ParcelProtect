package com.example.amady.parcelprotect;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * Created by Amady on 06/10/2017.
 */

public class Secure extends AppCompatActivity {

    // variable definissant les listview
    private ListView listenvoyes;

    private JSONArray HarnaisTab;
    private JSONArray clientTab;

    // harnais user login url
    final String selectAllClient = "https://upmost-limps.000webhostapp.com/arona/selectAllClient.php";
    final String showHarnais = "https://upmost-limps.000webhostapp.com/arona/showHarnais.php";


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secure);

        // ListView
<<<<<<< HEAD
        ListView listeHarnais = (ListView) findViewById(R.id.listeharnais);
        ListView listeDestinataire = (ListView) findViewById(R.id.listedestinataire);

        //essaie remplissage listview
        String[] harnais = new String[]{
                "000001","000002","000003"
        };
        String[] destinataire = new String[]{
                "000001","000002","000003"
        };
=======
        ListView listeHarnais;
        ListView listeDestinataire;
>>>>>>> f9f0ad0b51e5d1d56be7b7d2407f9fa9a446526f

        // EditText
        EditText codeDesactivationAppWeb = (EditText) findViewById(R.id.codedesactwebapp);
        final String codeDesactivationHarnais = String.valueOf(codeDesactivationAppWeb);
        // Boutton de l'interface choix
        Button btOk = (Button) findViewById(R.id.buttOk);
        // démmarage de l'activité interface choix pour
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog("Succès","Votre Colis est en cours d'envoie\n Le code est envoyé au destinataire");
            }
        });
        //----------------------------------Harnais---------------------------//

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

        //----------------------------------client---------------------------//

        try {
            clientTab = new JsonTaskSelectAllClient().execute(selectAllClient).get();
        }
        catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
        }
        Client clientlist[] = new Client[clientTab.length()];
        for (int i = 0; i < clientTab.length(); i++) {
            JSONObject Client;

            try {
                Client = clientTab.getJSONObject(i);
                int ClientID = Client.getInt("idClient");
                String nomClient = Client.getString("nom");
                String prenomClient = Client.getString("prenom");
                String emailClient = Client.getString("email");
                int telephoneClient = Client.getInt("telephone");
                String pseudoClient = Client.getString("pseudo");

                clientlist[i] = new Client(ClientID, nomClient, prenomClient, emailClient, telephoneClient, pseudoClient);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("TAG",clientlist[0].nomClient + " " + clientlist[0].prenomClient);

        }
        //essaie remplissage listview
        String[] id_selectAllClient = new String[clientlist.length];
        for (int i = 0; i < clientlist.length; i++) {
            id_selectAllClient[i] = clientlist[i].toString();
        }

        listeDestinataire = (ListView) findViewById(R.id.listedestinataire);

        final ArrayAdapter<String> listDesClients = new ArrayAdapter<>(Secure.this, android.R.layout.simple_list_item_1, id_selectAllClient);
        listeDestinataire.setAdapter(listDesClients);
        listeDestinataire.setItemsCanFocus(true);
    }

    private void sendConfirmationEmail(String email, String codeDesactivationHarnais) {
        //setting content for email
        String subject = "Confirmation Envoie N° + ";
        String message = "Bonjour,\n\nNous vous remercions pour cette commande d'envoie avec le harnais numéro:.\n" +
                "Afin de compléter la procédure, veuillez cliquer sur le lien suivant : http://upmost-limps.000webhostapp.com/php/validationEmail.php\n\n" +
                "Votre code d'activation est le : "+ codeDesactivationHarnais +
                "\n\nUne fois que votre compte est activé, vous pouvez vous connecter et gerer vos colis.\n\n" +
                "\nCordialement,\nL'équipe #TEAMHARNAIS";

        SendMail sm = new SendMail(this, email, subject, message);

        //Executing sendmail to send the mail
        sm.execute();
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
