package com.example.asynctask

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_control_room.*
import kotlinx.android.synthetic.main.activity_control_type_room.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class controlTypeRoom : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control_type_room)
        var intent : Intent = getIntent()
        var cs :Customer = intent.getSerializableExtra("dataCustomer") as Customer
        var ho :Hotel= intent.getSerializableExtra("dataHotel") as Hotel
        val url: String ="http://192.168.1.4:8080/webservice/typeRoomData.php"
        button6.setOnClickListener(){

            var inAddTypeRoom :Intent= Intent(this,create_type_room::class.java)
            inAddTypeRoom.putExtra("dataCustomer", cs);
            inAddTypeRoom.putExtra("dataHotel", ho);
            startActivity(inAddTypeRoom)
        }
        getData().execute(url)
    }
    inner class getData: AsyncTask<String, Void, String>(){


        override fun doInBackground(vararg prams: String?): String {
            return getdataTypeRoom(prams[0]);
        }
        override fun onPostExecute(result: String?) {

            super.onPostExecute(result)
            Toast.makeText(this@controlTypeRoom,result,Toast.LENGTH_LONG).show()
            var jsonArray : JSONArray = JSONArray(result);
            var arrayTypeRoom : ArrayList<typeRoom> = ArrayList();
            arrayTypeRoom.clear()
            for(cs in 0..jsonArray.length()-1){

                var objcs : JSONObject = jsonArray.getJSONObject(cs)
                arrayTypeRoom.add(typeRoom(objcs.getInt("id"),objcs.getString("name"),objcs.getInt("priceHour"),objcs.getInt("priceDay"),objcs.getInt("hotelId")))
            }
            typeRoom.adapter = typeRoomAdapter(this@controlTypeRoom,arrayTypeRoom)


        }
        private fun getdataTypeRoom(Url: String?): String {
            var ho :Hotel = intent.getSerializableExtra("dataHotel") as Hotel
            var content: StringBuilder = StringBuilder()
            val url: URL = URL(Url)
            val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            try {
                urlConnection.readTimeout = 10000
                urlConnection.connectTimeout = 15000
                urlConnection.requestMethod = "POST"
                var builder = Uri.Builder()
                    .appendQueryParameter("typeId", "1")
                    .appendQueryParameter("hotelId", ho.id.toString())
                val query = builder.build().encodedQuery
                val os = urlConnection.outputStream
                val writer = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
                writer.write(query)
                writer.flush()
                writer.close()
                os.close()
                urlConnection.connect()
            } catch (e1: IOException) {
                e1.printStackTrace()
                return "ERROR"
            }
            try {
                val respon_code = urlConnection.responseCode
                if (respon_code == HttpURLConnection.HTTP_OK) {
                    val input = urlConnection.inputStream
                    val reader = BufferedReader(InputStreamReader(input))
                    val result = StringBuilder()
                    var line: String
                    try {
                        do {
                            line = reader.readLine()
                            if (line != null) {
                                result.append(line)
                            }
                        } while (line != null)

                        reader.close()
                    } catch (e: Exception) {
                    }
                    return result.toString()
                } else {
                    return "ERORR"

                }

            } catch (e: IOException) {
                e.printStackTrace()
                return "ERORR"
            } finally {
                urlConnection.disconnect()
            }

        }

    }


}
