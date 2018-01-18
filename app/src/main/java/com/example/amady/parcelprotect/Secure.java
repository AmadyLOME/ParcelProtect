package com.example.amady.parcelprotect;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.text.DateFormat;

/**
 * Created by Amady on 06/10/2017.
 */

public class Secure extends AppCompatActivity {

    private JSONArray HarnaisTab;
    private JSONArray clientTab;
    private JSONArray PointRelaisTab;

    // harnais user login url
    final String selectAllClient = "https://upmost-limps.000webhostapp.com/arona/selectAllClient.php";
    final String showHarnais = "https://upmost-limps.000webhostapp.com/arona/showHarnais.php";
    final String selectAllPointRelais = "https://upmost-limps.000webhostapp.com/arona/selectAllPointRelais.php";

    //Varibale à envoyer
    public char idExpediteur;
    public char idDestinataire;
    public Boolean statutLivraison ;
    // date envoie
    public Date dateE = new Date();
    DateFormat mediumDateFormatE = DateFormat.getDateTimeInstance(
            DateFormat.MEDIUM,
            DateFormat.MEDIUM);
    public String dateEnvoie = String.valueOf(mediumDateFormatE.format(dateE));
    // date reception
    public Date dateR = new Date();
    DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(
            DateFormat.MEDIUM,
            DateFormat.MEDIUM);
    public String dateReception = String.valueOf(mediumDateFormat.format(dateR));
    //facturation
    public Boolean statutFacturation;

    public char idHarnais;
    public char idRelais;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secure);

        // ListView
        ListView listeHarnais;
        ListView listeDestinataire;
        ListView listeRelais;

        // EditText
        EditText codeDesactivationAppWeb = (EditText) findViewById(R.id.codedesactwebapp);
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

        listeHarnais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                ListView lv = (ListView) arg0;
                TextView tv = (TextView) lv.getChildAt(arg2);
                String infoHarnaisSelected = tv.getText().toString();
                idHarnais = infoHarnaisSelected.charAt(3);
                Toast.makeText(Secure.this, "L'id du harnais selectionné est :"+ idHarnais, Toast.LENGTH_LONG).show();
            } });


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
            id_selectAllClient[i] = "id:" + clientlist[i].getClientID() + " - " + clientlist[i].toString();
        }

        listeDestinataire = (ListView) findViewById(R.id.listedestinataire);

        final ArrayAdapter<String> listDesClients = new ArrayAdapter<>(Secure.this, android.R.layout.simple_list_item_1, id_selectAllClient);
        listeDestinataire.setAdapter(listDesClients);
        listeDestinataire.setItemsCanFocus(true);

        listeDestinataire.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                ListView liv = (ListView) arg0;
                TextView tiv = (TextView) liv.getChildAt(arg2);
                String destinataireSelectionné = tiv.getText().toString();
                idDestinataire = destinataireSelectionné.charAt(3);
                Toast.makeText(Secure.this, "L'id du Destinataire selectionné est :"+ idDestinataire, Toast.LENGTH_LONG).show();
            } });
        //----------------------------------Point Relais---------------------------//
        try {
            PointRelaisTab = new JsonTaskPointRelais().execute(selectAllPointRelais).get();
        }
        catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
        }
        PointRelais PointRelaislist[] = new PointRelais[PointRelaisTab.length()];
        for (int i = 0; i < PointRelaisTab.length(); i++) {
            JSONObject PointRelais;

            try {
                PointRelais = PointRelaisTab.getJSONObject(i);
                int PointRelaisID = PointRelais.getInt("idRelais");
                String nomRelais = PointRelais.getString("NomRelais");
                int AdresseID = PointRelais.getInt("Adresse_idAdresse");


                PointRelaislist[i] = new PointRelais(PointRelaisID, nomRelais, AdresseID);
            } catch (JSONException e) {
                e.printStackTrace();
            }
           Log.d("TAG",PointRelaislist[0].PointRelaisID + " " + PointRelaislist[0].nomRelais);
        }
        //essaie remplissage listview
        String[] id_selectedRelais = new String[PointRelaislist.length];
        for (int i = 0; i < PointRelaislist.length; i++) {
            id_selectedRelais[i] = PointRelaislist[i].toString();
        }

        listeRelais = (ListView) findViewById(R.id.listeRelais);

        final ArrayAdapter<String> listDesRelais = new ArrayAdapter<>(Secure.this, android.R.layout.simple_list_item_1, id_selectedRelais);
        listeRelais.setAdapter(listDesRelais);
        listeRelais.setItemsCanFocus(true);

        listeRelais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                ListView liv1 = (ListView) arg0;
                TextView tiv1 = (TextView) liv1.getChildAt(arg2);
                String RelaisSelectionné = tiv1.getText().toString();
                idRelais = RelaisSelectionné.charAt(3);
                Toast.makeText(Secure.this, "L'id du Relais selectionné est :"+ idRelais, Toast.LENGTH_LONG).show();
            } });
        //----------------------------------Envoie de la commande---------------------------//

        final String code = codeDesactivationAppWeb.getText().toString();
        // Url du fichier insertenvoie.php
        final String insertEnvoie = "https://upmost-limps.000webhostapp.com/arona/insertEnvoi.php";

        //requestQueue
        final RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Boutton d'envoie du colis
        Button btOk = (Button) findViewById(R.id.buttOk);

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //Insert dans php
                    StringRequest request = new StringRequest(Request.Method.POST, insertEnvoie, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected java.util.Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();

                            parameters.put("Client_idExpediteur", String.valueOf(idExpediteur));
                            parameters.put("Client_idDestinataire", String.valueOf(idDestinataire));
                            parameters.put("statutLivraison", String.valueOf(statutLivraison));
                            parameters.put("dateEnvoi", dateEnvoie);
                            parameters.put("dateLivraison", dateReception);
                            parameters.put("statutFacturation", String.valueOf(statutFacturation));
                            parameters.put("codeOuverture", String.valueOf(code));
                            parameters.put("Harnais_idHarnais", String.valueOf(idHarnais));
                            parameters.put("Relais_idRelais", String.valueOf(idRelais));

                            return parameters;
                        }
                    };
                    requestQueue.add(request);
                }
        });
    }

    private void sendConfirmationEmail(String email, String codeDesactivationHarnais) {
        //setting content for email
        // lien vers webapp
        String link = null;
        String subject = "Confirmation Envoie N° + ";
        String message = "Bonjour,\n\nNous vous remercions pour cette commande d'envoie avec le harnais numéro:.\n" +
                "Afin de compléter la procédure, veuillez cliquer sur le lien suivant" + link + "\n\n" +
                "Votre code d'activation est le suivant : "+ codeDesactivationHarnais +
                "\n\nMerci d'avoir choisi ParcelProtect, vous pouvez vous connecter et continuer à sécuriser vos colis.\n\n" +
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
