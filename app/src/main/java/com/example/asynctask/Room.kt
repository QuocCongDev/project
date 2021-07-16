package com.example.asynctask

import java.io.Serializable

data class Room(val id:Int,val code:String,val hotelId:Int,val type :Int): Serializable {
}