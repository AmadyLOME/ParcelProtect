package com.example.amady.parcelprotect;
/**
 * Created by asow on 18/01/2018.
 */

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.Attributes;

public class JsonTaskGetClientInfo extends AsyncTask<String, String, JSONArray> {

    @Override
    protected JSONArray doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(params[0]);

            connection = (HttpURLConnection) url.openConnection();
            //(String)put(new Attributes.Name(email), value);
            connection.connect();

            StringBuffer buffer = new StringBuffer();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();

            JSONObject parentObj = new JSONObject(finalJson);
            JSONArray parrentArray = parentObj.getJSONArray("Client");

            return parrentArray;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        super.onPostExecute(result);
    }
}


