<?php

include('connectionfile.php');
	
	$email=$_REQUEST['Email'];
	$password=$_REQUEST['Pwd'];
	
	
	
$sql = "INSERT INTO login(Email,Pwd) VALUES ('$email','$password')";
$k=mysqli_query($web,$sql);
if($k)
{
	$val['responce']="Insert Success";
	print(json_encode($val));
}
else
{
	$val['responce']="Insert problem";
	print(json_encode($val));
}

// include('connectionfile.php');
// $db = new Database();
// if(isset($_POST['Email']) && isset(['Pwd'])){
// 	if($db->dbConnect()){
// 		if($db->logIn("login",$_POST['Email'], $_POST['Pwd']){
// 			echo 'Login Success';
// 		}else echo 'Username or Password wrong';
// 	}else echo 'Error: Database Connection';	
// }else echo 'All fields are required';
?>
