<?php 
   
   //Obtenemos la conexion
    $conexion=mysql_connect("localhost","Beto","root");
    if (!$conexion) {
	    die("No se pudo Conectar la base de datos".mysql_error()."<br>");
    }

 //Crear la base de datos
  
   $db="CREATE DATABASE Login";
    if( mysql_query($db,$conexion)){
    	echo "La base de datos ha sido creado";
    }

 //Crear las tablas
    mysql_select_db("Login",$conexion);
    $table="CREATE TABLE logeo( 
	user varchar(40) NOT NULL,
	password varchar(40) NOT NULL)";

mysql_query($table,$conexion);
mysql_close();
//Contenido de Prueba de la Tabla logeo
//Preparar

mysql_select_db("Login",$conexion);
$Insertar="INSERT INTO logeo VALUES('Beto','root')";
$Insertar1="INSERT INTO logeo VALUES('Luis','root')";
//Insertar 
mysql_query($Insertar,$conexion);
mysql_query($Insertar1,$conexion);
//Cerrar
mysql_close($conexion);
 echo "Creacion Exitosa";
 ?>
