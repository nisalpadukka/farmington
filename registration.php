<?php

	include('connectionfile.php');
	
	$fname=$_REQUEST['FName'];
	$lname=$_REQUEST['LName'];
	$password=$_REQUEST['Pwd'];
	$phone=$_REQUEST['Phone'];
	$email=$_REQUEST['Email'];
	
	
	
$sql = "INSERT INTO registration(FName,LName,Pwd,Phone,Email) VALUES ('$fname','$lname','$password','$phone','$email')";
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
?>
