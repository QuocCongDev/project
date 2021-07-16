<?php 
$connect = mysqli_connect("localhost","root","","managerhotel");
mysqli_query($connect,"SET NAME 'utf8'");
$IdType = (Int)$_POST['typeId'];
if($IdType==1){
$arrayTypeRoom = array();
$hotelId=(Int)$_POST['hotelId'];	
$query = "SELECT * FROM typeRoom WHERE hotelId ='$hotelId'";
$data = mysqli_query($connect,$query);
	while ($row = mysqli_fetch_assoc($data)) {
	$typeRoom = new typeRoom($row['Id'],$row['Name'],$row['priceHour'],$row['priceDay'],$row['hotelId']);
	array_push($arrayTypeRoom, $typeRoom->getTypeRoom());	
}
echo json_encode($arrayTypeRoom);
}else if($IdType==2){
$name = $_POST['name'];
$priceHour = $_POST['priceHour'];
$priceDay = $_POST['priceDay'];
$hotelId = $_POST['hotelId'];
$query = "INSERT INTO typeroom (Name,priceHour,priceDay,hotelid) VALUES('$name','$priceHour','$priceDay','$hotelId')";
if(mysqli_query($connect,$query)){
	echo "1";
}else{
	echo "0";
}
}
class typeRoom{
	public $id, $name, $priceHour, $priceDay,$hotelId;

	function __construct($id,$name,$priceHour,$priceDay,$hotelId){

		$this->id = $id;
		$this->name = $name;
		$this->priceHour = $priceHour;
		$this->priceDay = $priceDay;
		$this->hotelId = $hotelId;
		

	}

	function getTypeRoom() {
		return [
			'id' => $this->id,
			'name' => $this->name,
			'priceHour' => $this->priceHour,
			'priceDay' => $this->priceDay,
			'hotelId' => $this->hotelId,
		];
	}

}
?>