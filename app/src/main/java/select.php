<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
    require 'connection.php';
    selectXXX();
}


function selectXXX()
{
    global $connect;

    $reponse = $bdd->query('SELECT * FROM Client');

    mysqli_query($connect,$query) or die (mysqli_error($connect));
    mysqli_close($connect);

}
?>