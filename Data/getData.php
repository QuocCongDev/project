<?php 
$connect = mysqli_connect("localhost","root","","managerhotel");

mysqli_query($connect,"SET NAME 'utf8'");
$query = "SELECT * FROM customer";
$data = mysqli_query($connect,$query);
$arrayCustomer = array();

while ($row = mysqli_fetch_assoc($data)) {
	$customer = new Customer($row['Id'],$row['Name'],$row['UserName'],$row['Password']);
	array_push($arrayCustomer, $customer->getCustomer());
	
}
echo json_encode($arrayCustomer);


class Customer{
	public $id, $name, $userName, $password;

	function __construct($id,$name,$userName,$password){

		$this->id = $id;
		$this->name = $name;
		$this->userName = $userName;
		$this->password = $password;
		

	}

	function getCustomer() {
		return [
			'id' => $this->id,
			'name' => $this->name,
			'user_name' => $this->userName,
			'password' => $this->password,
		];
	}

}
 ?>
