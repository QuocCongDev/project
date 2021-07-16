package com.example.asynctask

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up_customer.*
import kotlinx.android.synthetic.main.activity_update_customer.*
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class UpdateCustomer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_customer)
        val url: String ="http://192.168.1.4:8080/webservice/updateCustomer.php"
        var intent :Intent = getIntent()
        var Cs :Customer = intent.getSerializableExtra("dataCustomer") as Customer
        edtName.setText(Cs.Name)
        edtUserName.setText(Cs.userName)
        edtPassword.setText(Cs.Password)
        csId.setText(Cs.Id.toString())
        btnUpdate.setOnClickListener(){

            updateData().execute(url)
        }

    }
    inner class updateData: AsyncTask<String, Void, String>(){


        override fun doInBackground(vararg prams: String?): String {
            return updateData(prams[0]);
        }
        override fun onPostExecute(result: String?) {

            super.onPostExecute(result)
//
            Toast.makeText(this@UpdateCustomer,result.toString(), Toast.LENGTH_LONG).show()

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
                    .appendQueryParameter("Name",edtName.text.toString().trim())
                    .appendQueryParameter("id",csId.text.toString().trim())
                    .appendQueryParameter("userName",edtPassword.text.toString().trim())
                    .appendQueryParameter("Password",edtUserName.text.toString().trim())
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
