<?php 
$connect = mysqli_connect("localhost","root","","managerhotel");

mysqli_query($connect,"SET NAME 'utf8'");
$name = $_POST['Name'];
$userName = $_POST['userName'];
$password = $_POST['Password'];
$query = "INSERT INTO Customer (Name,UserName,Password) VALUES('$name','$userName','$password')";
if(mysqli_query($connect,$query)){
	echo "1";
}else{
	echo "0";
}


 ?>