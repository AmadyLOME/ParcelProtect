package com.example.amady.parcelprotect;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Harnais extends Object {
    int HarnaisId;
    int HarnaisQrCode;
    int HarnaisAdresseMac;
    int HarnaisStatus;

    public Harnais(int HarnaisID,int HarnaisQrCode,int HarnaisAdresseMac,int HarnaisStatus){
        this.HarnaisId = HarnaisID;
        this.HarnaisQrCode = HarnaisQrCode;
        this.HarnaisAdresseMac = HarnaisAdresseMac ;
        this.HarnaisStatus = HarnaisStatus;

        Log.d("HARNAISINFO", "id:" + HarnaisID + "\nQrcode:" + HarnaisQrCode + "\nMAC:" + HarnaisAdresseMac + "\nStatus:"+ HarnaisStatus);
    }

    public int getHarnaisId() {
        return this.HarnaisId;
    }

    public int getHarnaisQrCode() {
        return HarnaisQrCode;
    }

    public int getHarnaisAdresseMac() {
        return HarnaisAdresseMac;
    }

    public int getHarnaisStatus() {
        return HarnaisStatus;
    }

    public String toString() {
        return String.valueOf(HarnaisId);
    }
}
