package com.example.amady.parcelprotect;

/**
 * Created by asow on 17/01/2018.
 */


public class Client extends Object {
    int ClientID;
    String nomClient;
    String prenomClient;
    String emailClient;
    int telephoneClient;
    String pseudoClient;

    public Client(int ClientID, String nomClient, String prenomClient, String emailClient, int telephoneClient, String pseudoClient) {
        this.ClientID = ClientID;
        this.nomClient = nomClient;
        this.prenomClient = prenomClient;
        this.emailClient = emailClient;
        this.telephoneClient = telephoneClient;
        this.pseudoClient = pseudoClient;
    }

    public int getClientID() {
        return ClientID;
    }

    public void setClientID(int clientID) {
        ClientID = clientID;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getPrenomClient() {
        return prenomClient;
    }

    public void setPrenomClient(String prenomClient) {
        this.prenomClient = prenomClient;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    public int getTelephoneClient() {
        return telephoneClient;
    }

    public void setTelephoneClient(int telephoneClient) {
        this.telephoneClient = telephoneClient;
    }

    public String getPseudoClient() {
        return pseudoClient;
    }

    public void setPseudoClient(String pseudoClient) {
        this.pseudoClient = pseudoClient;
    }

    public String toString() {
        return nomClient + " " + prenomClient + " - " + telephoneClient;
    }
}