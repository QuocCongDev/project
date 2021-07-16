package com.example.asynctask

import java.io.Serializable

data class Hotel(val id:Int,val Name :String,val Adress:String,val ParentId:Int,val floor:Int,val Room:Int,val CreatedDate :String):Serializable {

    
}