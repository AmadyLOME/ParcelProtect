package com.example.amady.parcelprotect;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.cast.framework.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity
{
    public ProgressDialog progressDialog;
    public ProgressDialog progressDialogSucces;

    private EditText UserEditText;
    private EditText PassEditText;

    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // initialisation d'une progress bar
        progressDialog = new ProgressDialog(this);
        progressDialogSucces = new ProgressDialog(this);
        // Affichage de message
        progressDialog.setMessage("Veuillez attendre la connexion à la base...");
        progressDialogSucces.setMessage("Bienvenue dans votre espace");

        // Récupération des éléments de la vue définis dans le xml
        UserEditText = (EditText) findViewById(R.id.username);
        PassEditText = (EditText) findViewById(R.id.password);

        //bouton de connexion
        Button button = (Button) findViewById(R.id.okbutton);

        //requestQueue
        final RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Server user login url
        final String loginURL = "https://upmost-limps.000webhostapp.com/arona/loginFunction.php";


        // Définition du listener du bouton
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                int usersize = UserEditText.getText().length();
                int passsize = PassEditText.getText().length();
                String userLogin = UserEditText.getText().toString();
                String userPassword = PassEditText.getText().toString();
                // si les deux champs sont remplis
                if (usersize > 0 && passsize > 0) {
                    progressDialog.show();
                    // on affecte la valeur de user et pass les identifiants rentrées par l'utilisateur
                    String email = UserEditText.getText().toString();
                    String password = PassEditText.getText().toString();
                    // On appelle la fonction doLogin qui va communiquer avec le PHP et vérifier les logs
                    //LoginCheckup
                    checkLogin(userLogin, userPassword, loginURL, requestQueue);
                    //checkLogin("arona776@gmail.com", "bgarona", loginURL, requestQueue);
                }
                else {
                    createDialog("Erreur", "L'email ou le mot de passe n'est pas valide!");
                }
            }
        });

        // Boutton d'enregistrement
        button = (Button) findViewById(R.id.registered);
        // Création du listener du bouton d'enregistrement (on sort de l'appli)
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(Login.this, Enregistrement.class);
                startActivity(intent);
            }
        });
    }


    /**
     * fonctions de verification des element login et mp
     * */
    private void checkLogin(final String email, final String password, String loginURL, RequestQueue requestQueue) {
        //Insert dans php
        StringRequest request = new StringRequest(Request.Method.POST, loginURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d("Response: ", response);
                if (response.equals("Connexion en cours")) {
                    progressDialogSucces.show();
                    Intent intent = new Intent( Login.this, InterfaceChoix.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent( Login.this, Login.class);
                    startActivity(intent);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("email", email);
                // parameters.put("activation", activationState);
                parameters.put("password",md5(password));

                return parameters;
            }
        };
        requestQueue.add(request);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void createDialog(String title, String text)
    {
        // Création d'un popup affichant un message
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

