<?php
# FileName="Connection_php_mysql.htm"
# Type="MYSQL"
# HTTP="true"
$hostname_web = "localhost";
$database_web = "loginregistration";
$username_web = "root";
$password_web = "";
$web = new mysqli($hostname_web,$username_web,$password_web,$database_web);

if(mysqli_connect_errno()){
		printf("Connection fail: %s/n", mysqli_connect_error());
	
	}
	session_start();

?>