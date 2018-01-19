package com.example.amady.parcelprotect;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
import java.util.Map;

/**
 * Created by asow on 18/01/2018.
 */

public class ClientEnConnexion {
    static int clientID;
    static int telephone;
    static String nomClient;
    static String pseudo;
    static String emailClient;
    static String prenomClient;


    ClientEnConnexion(final String email, String getClientInfo, RequestQueue requestQueue) {
        this.emailClient = email;
        //this.clientID = 1;
        //Select dans php
        StringRequest request = new StringRequest(Request.Method.POST, getClientInfo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject convert = new JSONObject(response);
                    JSONArray client = convert.getJSONArray("Client");
                    JSONObject clientInUse = client.getJSONObject(0);
                    setClientID(clientInUse.getInt("idClient"));
                    setTelephone(clientInUse.getInt("telephone"));
                    setNomClient(clientInUse.getString("nom"));
                    setPrenomClient(clientInUse.getString("prenom"));
                    setPseudo(clientInUse.getString("pseudo"));
                    Log.d("TAG", getNomClient());
                    Log.d("TAG", getPrenomClient());
                    Log.d("TAG", getPseudo());
                    Log.d("TAG", String.valueOf(getTelephone()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("email", email);
                return parameters;
            }
        };
        requestQueue.add(request);
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public static String getNomClient() {
        return nomClient;
    }

    public static void setNomClient(String nomClient) {
        ClientEnConnexion.nomClient = nomClient;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPrenomClient() {
        return prenomClient;
    }

    public void setPrenomClient(String prenomClient) {
        this.prenomClient = prenomClient;
    }
}