package com.example.asynctask

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_update_customer.*
import kotlinx.android.synthetic.main.activity_view_hotel.*
import kotlinx.android.synthetic.main.list_hotel_item.*
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class ViewHotel : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_hotel)
        var intent : Intent = getIntent()
        var url ="http://192.168.1.4:8080/webservice/HotelData.php"
        var ho :Hotel = intent.getSerializableExtra("dataHotel") as Hotel
        var cs :Customer = intent.getSerializableExtra("dataCustomer") as Customer
        room.setText("Tổng số phòng :"+(ho.Room*ho.floor).toString())
        edtAdressKs.setText(ho.Adress)
        edtNameKs.setText(ho.Name)
        idHotel.setText(ho.id.toString())
        btnUpdateRoom.setOnClickListener(){
            updateHotel().execute(url)

        }
        qlyPhong.setOnClickListener(){

            var intent :Intent = Intent(this,controlRoom::class.java)
            intent.putExtra("dataHotel",ho );
            intent.putExtra("dataCustomer",cs );
            startActivity(intent)
        }
        Back.setOnClickListener(){
            var intent :Intent = Intent(this,controlHotel::class.java)
          var  Cs:Customer=Customer(ho.ParentId,"","","")
            intent.putExtra("dataCustomer",Cs );
            intent.putExtra("dataHotel",ho);
            startActivity(intent)
        }
    }
    inner class updateHotel: AsyncTask<String, Void, String>(){


        override fun doInBackground(vararg prams: String?): String {
            return updateData(prams[0]);
        }
        override fun onPostExecute(result: String?) {

            super.onPostExecute(result)
//
            Toast.makeText(this@ViewHotel,result.toString(), Toast.LENGTH_LONG).show()

        }
        private  fun updateData(Url:String?):String{

            var content: StringBuilder = StringBuilder()
            val url: URL = URL(Url)
            val urlConnection : HttpURLConnection = url.openConnection() as HttpURLConnection
            try {
                urlConnection.readTimeout =10000
                urlConnection.connectTimeout=15000
                urlConnection.requestMethod = "POST"
                var builder = Uri.Builder()
                    .appendQueryParameter("name",edtNameKs.text.toString().trim())
                    .appendQueryParameter("id",idHotel.text.toString())
                    .appendQueryParameter("typeId","2")
                    .appendQueryParameter("adress",edtAdressKs.text.toString().trim())
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
