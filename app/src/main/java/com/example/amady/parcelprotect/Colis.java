package com.example.amady.parcelprotect;

import android.util.Log;

/**
 * Created by Amady on 19/01/2018.
 */

public class Colis extends Object {

    int colisID;
    int statutLivraison;

    public Colis(int colisID, int statutLivraison){
        this.colisID = colisID;
        this.statutLivraison = statutLivraison;

    }

    public int getHarnaisId() {
        return this.colisID;
    }

    public int getHarnaisQrCode() {
        return this.statutLivraison;
    }

    public String toString() {
        return "id:" + String.valueOf(colisID) + " - @Statut de livraison : " + statutLivraison ;
    }
}
