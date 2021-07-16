<?php 
$connect = mysqli_connect("localhost","root","","managerhotel");

mysqli_query($connect,"SET NAME 'utf8'");

$IdType = (Int)$_POST['typeId'];
$query = "";
// $data = mysqli_query($connect,$query);
// $arrayHotel = array();
// while ($row = mysqli_fetch_assoc($data)) {
// 	$customer = new Hotel($row['Id'],$row['Name'],$row['adress'],$row['Phone'],$row['ParentId'],$row['Floor'],$row['Room'],$row['CreatedDate']);
// 	array_push($arrayHotel, $customer->getHotel());
	
// }

// 	echo json_encode($arrayHotel);

if($IdType==1){
$ParentId =$_POST['ParentId'];
$query ="SELECT * FROM Hotel WHERE ParentId = '$ParentId'";
$data = mysqli_query($connect,$query);
$arrayHotel = array();
while ($row = mysqli_fetch_assoc($data)) {
	$customer = new Hotel($row['Id'],$row['Name'],$row['Adress'],$row['Phone'],$row['ParentId'],$row['Floor'],$row['Room'],$row['CreatedDate']);
	array_push($arrayHotel, $customer->getHotel());
	
}

	echo json_encode($arrayHotel);
}
else if($IdType==2){
$name = $_POST['name'];
$adress = $_POST['adress'];
$id = (int)$_POST['id'];
$query = "UPDATE Hotel SET name = '$name',adress='$adress' WHERE id ='$id' ";
if(mysqli_query($connect,$query)){
	echo "1";
}else{
	echo "0";
}

}
else if($IdType==3){
$name = $_POST['name'];
$adress = $_POST['adress'];
$phone = $_POST['phone'];
$parentId = (int)$_POST['parentId'];
$floor = (int)$_POST['floor'];
$room = (int)$_POST['room'];
$query = "INSERT INTO Hotel (Name,Adress,Phone,ParentId,Floor,Room) VALUES('$name','$adress','$phone','$parentId','$floor','$room')";
if(mysqli_query($connect,$query)){
echo "1";
}else{
echo "0";	
}
}else if($IdType==4){
$hotelID = (int)$_POST['hotelID'];
$floor = (int)$_POST['floor'];
$room = (int)$_POST['room'];

$query = $query ="SELECT * FROM Room WHERE HotelId = '$hotelID'";
	$arrayRoom = array();
$dataRoom = mysqli_query($connect,$query);
while ($row = mysqli_fetch_assoc($dataRoom)) {
	$Room = new Room($row['Id'],$row['HotelId'],$row['Code'],$row['Type']);
	array_push($arrayRoom, $Room->getRoom());	
}
if(count($arrayRoom)==0){
$code="";	
$type=1;
for($i=1;$i<=$floor;$i++){
	for($j=1;$j<=$room;$j++){
		
		$code ="P$i.0$j";	
		$query2 = "INSERT INTO Room (HotelId,Code,Type) VALUES('$hotelID','$code','$type')";
		mysqli_query($connect,$query2);	
	}
}
while ($row = mysqli_fetch_assoc($dataRoom)) {
	$Room = new Room($row['Id'],$row['HotelId'],$row['Code'],$row['Type']);
	array_push($arrayRoom, $Room->getRoom());	
}
echo json_encode($arrayRoom);
}
else{
	echo json_encode($arrayRoom);
}
}else if($IdType==5){
	$hotelID = (int)$_POST['hotelID'];
	$query = $query ="SELECT * FROM Room WHERE HotelId = '$hotelID'";
	$arrayRoom = array();
	while ($row = mysqli_fetch_assoc($data)) {
	$Room = new Room($row['Id'],$row['HotelId'],$row['Code'],$row['Type']);
	array_push($arrayRoom, $Room->getRoom());
	
}
	echo json_encode($arrayRoom);
}

class Hotel{
	public $id,$name,$adress,$phone,$parentId,$floor,$room,$CreatedDate;

	function __construct($id,$name,$adress,$phone,$parentId,$floor,$room,$CreatedDate){

		$this->id = $id;
		$this->name = $name;
		$this->adress = $adress;
		$this->phone = $phone;
		$this->parentId = $parentId;
		$this->floor = $floor;
		$this->room = $room;
		$this->CreatedDate = $CreatedDate;	

	}

	function getHotel() {
		return [
			'id' => $this->id,
			'name' => $this->name,
			'adress' => $this->adress,
			'phone' => $this->phone,
			'parentId' => $this->parentId,
			'floor' => $this->floor,
			'room' => $this->room,
			'CreatedDate' => $this->CreatedDate,
			
		];
	}

}
class Room{
	public $id,$hotelId,$code,$type;

	function __construct($id,$hotelId,$code,$type){

		$this->id = $id;
		$this->hotelId = $hotelId;
		$this->code = $code;
		$this->type = $type;
		

	}

	function getRoom() {
		return [
			'id' => $this->id,
			'hotelId' => $this->hotelId,
			'code' => $this->code,
			'type' => $this->type,
			
			
		];
	}

}

 ?>