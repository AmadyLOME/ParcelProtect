[1mdiff --git a/app/src/main/java/com/example/amady/parcelprotect/ColisEnCours.java b/app/src/main/java/com/example/amady/parcelprotect/ColisEnCours.java[m
[1mindex 8d609f5..184a5e3 100644[m
[1m--- a/app/src/main/java/com/example/amady/parcelprotect/ColisEnCours.java[m
[1m+++ b/app/src/main/java/com/example/amady/parcelprotect/ColisEnCours.java[m
[36m@@ -1,11 +1,23 @@[m
 package com.example.amady.parcelprotect;[m
 [m
[32m+[m[32mimport android.content.Intent;[m
 import android.os.Bundle;[m
 import android.support.v7.app.AppCompatActivity;[m
 import android.widget.ArrayAdapter;[m
 import android.widget.EditText;[m
 import android.widget.ListView;[m
 [m
[32m+[m[32mimport com.android.volley.AuthFailureError;[m
[32m+[m[32mimport com.android.volley.Request;[m
[32m+[m[32mimport com.android.volley.Response;[m
[32m+[m[32mimport com.android.volley.VolleyError;[m
[32m+[m[32mimport com.android.volley.toolbox.JsonArrayRequest;[m
[32m+[m[32mimport com.android.volley.toolbox.StringRequest;[m
[32m+[m
[32m+[m[32mimport org.json.JSONObject;[m
[32m+[m
[32m+[m[32mimport java.util.*;[m
[32m+[m
 /**[m
  * Created by Amady on 29/11/2017.[m
  */[m
[36m@@ -24,6 +36,8 @@[m [mpublic class ColisEnCours extends AppCompatActivity {[m
     private String[] id_harnaisreception = new String[]{[m
             "000001","000002","000003"[m
     };[m
[32m+[m[32m    // Url du fichier showHarnais php[m
[32m+[m[32m    final String showHarnais = "https://upmost-limps.000webhostapp.com/arona/showHarnais.php";[m
 [m
     @Override[m
     public void onCreate(Bundle savedInstanceState)[m
[36m@@ -31,6 +45,27 @@[m [mpublic class ColisEnCours extends AppCompatActivity {[m
         super.onCreate(savedInstanceState);[m
         setContentView(R.layout.colisencours);[m
 [m
[32m+[m[32m        //Insert dans php[m
[32m+[m[32m        StringRequest request = new StringRequest(Request.Method.POST, showHarnais, new Response.Listener<String>() {[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void onResponse(String response) {[m
[32m+[m
[32m+[m[32m            }[m
[32m+[m[32m        }, new Response.ErrorListener() {[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void onErrorResponse(VolleyError error) {[m
[32m+[m
[32m+[m[32m            }[m
[32m+[m[32m        });[m
[32m+[m[32m        requestQueue.add(request);[m
[32m+[m[32m        Intent intent = new Intent(Enregistrement.this, Login.class);[m
[32m+[m[32m        startActivity(intent);[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m                else {[m
[32m+[m[32m        createDialog("Attention", "Un des champs n'est pas renseigné!");[m
[32m+[m[32m    }[m
[32m+[m
         listreception = (ListView) findViewById(R.id.listreception);[m
         listenvoie = (ListView) findViewById(R.id.listenvoie);[m
 [m
