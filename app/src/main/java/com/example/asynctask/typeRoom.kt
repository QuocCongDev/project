package com.example.asynctask

import java.io.Serializable

data class typeRoom(var id:Int,var name: String,var priceDay: Int?, var priceHour :Int?,var hotelId:Int?):
    Serializable {
    override fun toString(): String {
        return name
    }
}