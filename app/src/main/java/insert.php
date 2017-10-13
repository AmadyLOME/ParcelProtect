<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
    require 'connection.php';
    createProfil();
}


function createProfil()
{
    global $connect;

    $idClient = $_POST["idClient"];
    $nom = $_POST["nom"];
    $prenom = $_POST["prenom"];
    $email = $_POST["email"];
    $telephone = $_POST["telephone"];
    $pseudo = $_POST["pseudo"];
    $password = $_POST["password"];
    $activation = $_POST["activation"];

    $query = " INSERT INTO `Client`(`idClient`, `nom`, `prenom`, `email`, `telephone`, `pseudo`, `password`, `activation`) VALUES ([idClient],[nom],[prenom],[email],[telephone],[pseudo],[password],[activation]);";

    mysqli_query($connect,$query) or die (mysqli_error($connect));
    mysqli_close($connect);

}
?>