<?php 
$connect = mysqli_connect("localhost","root","","managerhotel");

mysqli_query($connect,"SET NAME 'utf8'");
$name = $_POST['Name'];
$id = (int)$_POST['id'];
$userName = $_POST['userName'];
$password = $_POST['Password'];
$query = "UPDATE CUSTOMER SET USERNAME = '$userName',NAME='$name',PASSWORD ='$password' WHERE id ='$id' ";
if(mysqli_query($connect,$query)){
	echo "1";
}else{
	echo "0";
}


 ?>