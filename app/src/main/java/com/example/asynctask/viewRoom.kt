package com.example.asynctask

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_control_room.*
import kotlinx.android.synthetic.main.activity_view_hotel.*
import kotlinx.android.synthetic.main.activity_view_room.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


class viewRoom : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_room)
        var intent : Intent = getIntent()
        var url ="http://192.168.1.4:8080/webservice/typeRoomData.php"
        var ho :Hotel = intent.getSerializableExtra("dataHotel") as Hotel
        var room :Room = intent.getSerializableExtra("dataRoom") as Room
        setCodeRoom.setText(room.code)
        setHotelName.setText(ho.Name)
        getTypeRoom().execute(url)


    }
    fun getSelectedUser(v: View?) {
        val tr: typeRoom = spinner.selectedItem as typeRoom
        displayUserData(tr)
    }

    private fun displayUserData(tr: typeRoom) {
        val name: String? = tr.name
        val id : Int = tr.id
        setprHRoom.setText("giá phòng theo giờ :" + tr.priceHour.toString())
        setprDRoom.setText( "giá phòng theo ngày :" +tr.priceDay.toString())

    }
    inner class getTypeRoom: AsyncTask<String, Void, String>(){


        override fun doInBackground(vararg prams: String?): String {
            return getData(prams[0]);
        }
        override fun onPostExecute(result: String?) {

            super.onPostExecute(result)

            Toast.makeText(this@viewRoom,result.toString(), Toast.LENGTH_LONG).show()
            var jsonArray : JSONArray = JSONArray(result);
            var arrayTypeRoom : ArrayList<typeRoom> = ArrayList();
            arrayTypeRoom.clear()
            for(cs in 0..jsonArray.length()-1){

                var objcs : JSONObject = jsonArray.getJSONObject(cs)
                arrayTypeRoom.add(typeRoom(objcs.getInt("id"),objcs.getString("name"),objcs.getInt("priceHour"),objcs.getInt("priceDay"),objcs.getInt("hotelId")))
            }

            val adapter: ArrayAdapter<typeRoom> = ArrayAdapter<typeRoom>(
                applicationContext,
                android.R.layout.simple_spinner_dropdown_item,arrayTypeRoom
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val tr: typeRoom = parent.selectedItem as typeRoom
                    displayUserData(tr)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
        private  fun getData(Url:String?):String{
            var ho :Hotel = intent.getSerializableExtra("dataHotel") as Hotel
            var content: StringBuilder = StringBuilder()
            val url: URL = URL(Url)
            val urlConnection : HttpURLConnection = url.openConnection() as HttpURLConnection
            try {
                urlConnection.readTimeout =10000
                urlConnection.connectTimeout=15000
                urlConnection.requestMethod = "POST"
                var builder = Uri.Builder()

                    .appendQueryParameter("typeId","1")
                    .appendQueryParameter("hotelId",ho.id.toString().trim())
                val query = builder.build().encodedQuery
                val os = urlConnection.outputStream
                val writer = BufferedWriter(OutputStreamWriter(os,"UTF-8"))
                writer.write(query)
                writer.flush()
                writer.close()
                os.close()
                urlConnection.connect()
            }catch (e1: IOException){
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
                    try{
                        do {
                            line = reader.readLine()
                            if (line != null) {
                                result.append(line)
                            }
                        } while (line != null)

                        reader.close()
                    } catch (e:Exception){ }
                    return result.toString()
                }else{
                    return "ERORR"

                }

            }catch(e: IOException){
                e.printStackTrace()
                return "ERORR"
            }finally {
                urlConnection.disconnect()
            }

        }


    }


}
