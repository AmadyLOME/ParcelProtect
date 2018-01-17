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
