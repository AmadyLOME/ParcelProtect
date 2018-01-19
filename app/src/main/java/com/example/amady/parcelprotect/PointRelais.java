package com.example.amady.parcelprotect;

/**
 * Created by Amady on 17/01/2018.
 */
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class PointRelais extends Object{

    int PointRelaisID;
    String nomRelais;
    int AdresseID;

    public PointRelais(int PointRelaisID, String nomRelais, int AdresseID ) {
        this.PointRelaisID = PointRelaisID;
        this.nomRelais = nomRelais;
        this.AdresseID = AdresseID;
    }

    public int getPointRelaisID() {
        return PointRelaisID;
    }
    public void setPointRelaisID(int PointRelaisID) {
        this.PointRelaisID = PointRelaisID;
    }

    public String getNomRelais() {
        return nomRelais;
    }
    public void setNomRelais(String nomRelais) {
        this.nomRelais = nomRelais;
    }

    public int getAdresseID() {
        return AdresseID;
    }
    public void setAdresseRelaisID(int AdresseID) {
        this.AdresseID = AdresseID;
    }

    public String toString() { return "id:" + String.valueOf(PointRelaisID) + " - " + nomRelais;}
}
