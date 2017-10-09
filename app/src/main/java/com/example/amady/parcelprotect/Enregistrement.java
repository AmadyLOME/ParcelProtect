package com.example.amady.parcelprotect;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Amady on 16/09/2017.
 */

public class Enregistrement extends AppCompatActivity {
    // Lien vers votre page d'enregistrement dans la base
    private static final String	UPDATE_URL	= "https://files.000webhost.com/public_html/registered.php";

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

        // Button pour envoyer les élements dans la base
        Button signIn = (Button) findViewById(R.id.btenregistrer);
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
                // Vérifications de la contenabilité des champs ainsi que de l'équivalence des mot-passe
                if ((nomsize > 0) && (prenomsize > 0) && (emailsize > 0) && (telephonezisize > 0) && (pseudosize > 0) && (mpsize > 0) && (cmpsize > 0) && (champCmp == champMp))
                {
                    String name = champNom.getText().toString();
                    String surname = champPrenom.getText().toString();
                    String mail = champNom.getText().toString();
                    String phone = champPrenom.getText().toString();
                    String pseudo = champNom.getText().toString();
                    String pass = champPrenom.getText().toString();

                    // On appelle la fonction doregistered qui va communiquer avec le PHP et inserer les informations
                    //doLogin(name, surname,mail,phone,pseudo,pass);
                }
                else
                    createDialog("Attention", "Un des champs n'est pas renseigné!");
            }
        });
    }

    // Méthode d'annulation
    private void quit(boolean success, Intent i)
    {
        //On envoie un résultat qui va permettre de quitter l'appli
        //setResult((success) ? Activity.RESULT_OK : Activity.RESULT_CANCELED, i);
        //finish();

    }

    private void createDialog(String title, String text)
    {
        // Création d'une popup affichant un message
        AlertDialog ad = new AlertDialog.Builder(this)
                .setPositiveButton("Ok", null).setTitle(title).setMessage(text)
                .create();
        ad.show();

    }

    // méthode de verification base de données
    private void doLogin(final String login, final String pass)
    {
        //on code le password avec le modèle md5
        final String pw = md5(pass);
        // Création d'un thread
        Thread t = new Thread()
        {
            public void run()
            {

                Looper.prepare();
                // On se connecte au serveur afin de communiquer avec le PHP
                DefaultHttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 15000);
                HttpResponse response;
                HttpEntity entity;
                try
                {
                    // On établit un lien avec le script PHP
                    HttpPost post = new HttpPost(UPDATE_URL);
                    //declaration du tableau conteneur des valeurs de mp et pw
                    List<NameValuePair> nvps = new ArrayList<NameValuePair>();

                    nvps.add(new BasicNameValuePair("username", login));

                    nvps.add(new BasicNameValuePair("password", pw));

                    post.setHeader("Content-Type", "application/x-www-form-urlencoded");
                    // On passe les paramètres login et password qui vont être récupérés
                    // par le script PHP en post
                    post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
                    // On récupère le résultat du script
                    response = client.execute(post);

                    entity = response.getEntity();

                    InputStream is = entity.getContent();
                    // On appelle une fonction définie plus bas pour traduire la réponse
                    read(is);
                    is.close();

                    if (entity != null)
                        entity.consumeContent();

                }
                catch (Exception e)
                {

                    progressDialog.dismiss();
                    createDialog("Erreur", "La connexion à la base n'a pas pu être faite!");

                }

                Looper.loop();

            }

        };
        //boucle sur thread
        t.start();
    }

    private void read(InputStream in)
    {
        // On traduit le résultat d'un flux
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp;
        try
        {
            sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            // Cette classe est définie plus bas
            //Enregistrement.LoginContentHandler uch = new Login.LoginContentHandler();
            //xr.setContentHandler(uch);
            xr.parse(new InputSource(in));
        }
        catch (ParserConfigurationException e)
        {

        }
        catch (SAXException e)
        {

        }
        catch (IOException e)
        {
        }

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

    private class LoginContentHandler extends DefaultHandler
    {
        // Classe traitant le message de retour du script PHP
        private boolean	in_loginTag		= false;
        private int			userID;
        private boolean	error_occured	= false;

        public void startElement(String n, String l, String q, Attributes a)

                throws SAXException

        {

            if (l == "login")
                in_loginTag = true;
            if (l == "error")
            {

                progressDialog.dismiss();

                switch (Integer.parseInt(a.getValue("value")))
                {
                    case 1:
                        createDialog("Erreur", "Pas de Connexion à la base");
                        break;
                    case 2:
                        createDialog("Erreur", "Il y'a des tables manquantes dans la base");
                        break;
                    case 3:
                        createDialog("Erreur", "Invalide username ou mot de passe");
                        break;
                }
                error_occured = true;

            }

            if (l == "user" && in_loginTag && a.getValue("id") != "")
                // Dans le cas où tout se passe bien on récupère l'ID de l'utilisateur et on lance l'activité choix
                userID = Integer.parseInt(a.getValue("id"));
            Intent intent = new Intent(Enregistrement.this, InterfaceChoix.class);
            startActivity(intent);
        }

        public void endElement(String n, String l, String q) throws SAXException
        {
            // on renvoie l'id si tout est ok
            if (l == "login")
            {
                in_loginTag = false;

                if (!error_occured)
                {
                    progressDialog.dismiss();
                    Intent i = new Intent();
                    i.putExtra("userid", userID);
                    quit(true, i);
                }
            }
        }

        public void characters(char ch[], int start, int length)
        {
        }

        public void startDocument() throws SAXException
        {
        }

        public void endDocument() throws SAXException
        {
        }

    }
}
