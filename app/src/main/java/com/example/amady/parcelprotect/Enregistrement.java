package com.example.amady.parcelprotect;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arona on 16/09/2017.
 * Created by Amady on 16/09/2017.
 */

public class Enregistrement extends AppCompatActivity {

    public ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enregistrement);

        // initialisation d'une barre de progression
        progressDialog = new ProgressDialog(this);

        // Affichage de message
        progressDialog.setMessage("Nous vous enregistrons dans la base de données...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        // Element de remplissage
        final EditText champNom = (EditText) findViewById(R.id.nomClient);
        final EditText champPrenom = (EditText) findViewById(R.id.prenomClient);
        final EditText champEmail = (EditText) findViewById(R.id.emailClient);
        final EditText champTelephone = (EditText) findViewById(R.id.telephoneClient);
        final EditText champPseudo = (EditText) findViewById(R.id.pseudoClient);
        final EditText champMp = (EditText) findViewById(R.id.mpClient);
        final EditText champCmp = (EditText) findViewById(R.id.cmpClient);

        //requestQueue
        final RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Button pour envoyer les élements dans la base
        Button signIn = (Button) findViewById(R.id.btenregistrer);

        // Url du fichier insert.php
        final String insertUrl = "https://upmost-limps.000webhostapp.com/arona/insert.php";
        // Url du fichier showClient.php
        final String showUrl = "https://upmost-limps.000webhostapp.com/arona/showClient.php";
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // récupération des longueurs de données dans les champs
                int nomsize = champNom.getText().length();
                int prenomsize = champPrenom.getText().length();
                int emailsize = champEmail.getText().length();
                int telephonezisize = champTelephone.getText().length();
                int pseudosize = champPseudo.getText().length();
                int mpsize = champMp.getText().length();
                int cmpsize = champCmp.getText().length();
                String mdp1 = champMp.getText().toString();
                String mdp2 = champCmp.getText().toString();

                boolean mdpTrue = (mdp1 == mdp2);

                Log.d("TAG","-----------Boolean: " + Boolean.toString(mdpTrue));
                Log.d("TAG","-----------Boolean: " + mdp1 + mdp2);


                // Vérifications de la contenabilité des champs ainsi que de l'équivalence des mot-passe
                if ((nomsize > 0) && (prenomsize > 0) && (emailsize > 0) && (telephonezisize > 0) && (pseudosize > 0) && (mpsize > 0) && (cmpsize > 0) ) {
                    final String name = champNom.getText().toString();
                    final String surname = champPrenom.getText().toString();
                    final String mail = champEmail.getText().toString();
                    final String phone = champTelephone.getText().toString();
                    final String pseudo = champPseudo.getText().toString();
                    final String pass = champMp.getText().toString();
                    String passConfirme = champCmp.getText().toString();
                    Log.d("Test", pass + " " + passConfirme + pass.equals(passConfirme));

                    // On appelle la fonction doregistered qui va communiquer avec le PHP et inserer les informations
                    //doLogin(name, surname,mail,phone,pseudo,pass)
                    //Mail de confirmation
                    sendConfirmationEmail(mail);

                    //Insert dans php
                    StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("nom", surname);
                            parameters.put("prenom", name);
                            parameters.put("email", mail);
                            parameters.put("telephone", phone);
                            parameters.put("pseudo", pseudo);
                            parameters.put("password",md5(pass));

                            return parameters;
                        }
                    };
                    requestQueue.add(request);
                } else {
                    createDialog("Attention", "Un des champs n'est pas renseigné!");
                }
            }
        });
    }

    private void sendConfirmationEmail(String email) {
        //setting content for email
        String subject = "Bienvenue chez ParcelProtect";
        String message = "Bonjour,\nNous vous remercions de votre demande de création de compte.\n" +
                "Afin de compléter la procédure, veuillez cliquer sur le lien suivant : coller le lien ici\n" +
                "Une fois que votre compte est activé, vous pouvez vous connecter et gerer vos colis.\n\n" +
                "\nCordialement,\nL'équipe #TEAMHARNAIS";

        SendMail sm = new SendMail(this, email, subject, message);

        //Executing sendmail to send the mail
        sm.execute();
    }

    private void createDialog(String title, String text)
    {
        // Création d'une popup affichant un message
        AlertDialog ad = new AlertDialog.Builder(this)
                .setPositiveButton("Ok", null).setTitle(title).setMessage(text)
                .create();
        ad.show();

    }

    private String md5(String in)
    {
        MessageDigest digest;
        try
        {
            digest = MessageDigest.getInstance("MD5");

            digest.reset();

            digest.update(in.getBytes());

            byte[] a = digest.digest();

            int len = a.length;

            StringBuilder sb = new StringBuilder(len << 1);

            for (int i = 0; i < len; i++)
            {

                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));

                sb.append(Character.forDigit(a[i] & 0x0f, 16));

            }
            return sb.toString();

        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return null;

    }
}
