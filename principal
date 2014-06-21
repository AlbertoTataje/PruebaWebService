<?php 
  // session_start();
  
  //Recibir de android


    $conexion=mysql_connect("localhost","Beto","root");
  if (!$conexion) {
    die("No se pudo realizar la coneccion<br>");
  }




   
   $usuario=$_POST['usuario'];
   $password=$_POST['password'];
   

     mysql_select_db("Login",$conexion);
   


// echo "Usuario: ".$_SESSION['usuario']."<br>";
 //echo "Contrasenya: ".$_SESSION['contrasenya']."<br>";
//Crear una coneccion
 

$Insertar2="INSERT INTO logeo VALUES('".$usuario."','".$password."')";
/*$Insertar1="INSERT INTO ordenes VALUES('','Ceviche',5,120)";*/
//Insertar 

mysql_query($Insertar2,$conexion);


mysql_close($conexion);


 ?>
