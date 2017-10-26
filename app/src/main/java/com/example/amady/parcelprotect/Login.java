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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.cast.framework.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;



public class Login extends AppCompatActivity
{
    // Lien vers votre page php sur votre serveur
    // Server user login url
    public static String URL_LOGIN = "https://upmost-limps.000webhostapp.com/amady/login.php";

    public ProgressDialog				progressDialog;

    public String TAG;

    private SessionManager session;

    private EditText						UserEditText;

    private EditText						PassEditText;

    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // initialisation d'une progress bar
        progressDialog = new ProgressDialog(this);
        // Affichage de message
        progressDialog.setMessage("Veuillez attendre la connexion à la base...");
        //progressDialog.setIndeterminate(true);
        //progressDialog.setCancelable(false);

        // Session manager
        //session = new SessionManager(getApplicationContext());

        // Récupération des éléments de la vue définis dans le xml
        UserEditText = (EditText) findViewById(R.id.username);
        PassEditText = (EditText) findViewById(R.id.password);
        //bouton de connexion
        Button button = (Button) findViewById(R.id.okbutton);

        // Définition du listener du bouton
        button.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v)
            {
                int usersize = UserEditText.getText().length();
                int passsize = PassEditText.getText().length();
                // si les deux champs sont remplis
                if (usersize > 0 && passsize > 0)
                {
                    progressDialog.show();
                    // on affecte la valeur de user et pass les identifiants rentrées par l'utilisateur
                    String email = UserEditText.getText().toString();
                    String password = PassEditText.getText().toString();
                    // On appelle la fonction doLogin qui va communiquer avec le PHP et vérifier les logs
                    checkLogin(email,md5(password));
                }
                else
                    createDialog("Erreur", "L'email ou le mot de passe n'est pas valide!");
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
    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        progressDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        //session.setlogin(true);

                        // Now store the user in SQLite
                        //String uid = jObj.getString("uid");
                        //JSONObject user = jObj.getJSONObject("user");
                        //String name = user.getString("name");
                        //String email = user.getString("email");
                        //String created_at = user.getString("created_at");
                        // Launch main activity
                        //on lance l'activité Interface choix
                        Intent intent = new Intent(Login.this, InterfaceChoix.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
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

