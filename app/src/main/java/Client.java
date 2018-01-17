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
}