package com.example.asynctask

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up_customer.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var url1 :String="http://192.168.1.4:8080/webservice/getData.php"

           getData().execute(url1)
        btn.setOnClickListener(){

            val intent: Intent = Intent(this, SignUpCustomer::class.java)
            startActivity(intent)
        }
        val intent :Intent = Intent(this,loginScreen::class.java)
        startActivity(intent)


    }

    inner class getData: AsyncTask<String,Void,String>(){


        override fun doInBackground(vararg prams: String?): String {
            return getUrl(prams[0]);
        }
        override fun onPostExecute(result: String?) {

            super.onPostExecute(result)
            var jsonArray :JSONArray = JSONArray(result);
            var arraymonAn : ArrayList<Customer> = ArrayList();
            arraymonAn.clear()
            for(cs in 0..jsonArray.length()-1){

                var objcs :JSONObject = jsonArray.getJSONObject(cs)
                arraymonAn.add(Customer(objcs.getInt("id"),objcs.getString("name"),objcs.getString("user_name"),objcs.getString("password")))
            }
            listview.adapter = customAdapter(this@MainActivity,arraymonAn)



        }
        private  fun getUrl(Url:String?):String{

            var content: StringBuilder = StringBuilder()
            val url: URL = URL(Url)
            val urlConnection : HttpURLConnection = url.openConnection() as HttpURLConnection
            val inputsteam: InputStream = urlConnection.inputStream
            val inputsteamReader : InputStreamReader = InputStreamReader(inputsteam)
            val buff: BufferedReader = BufferedReader(inputsteamReader) as BufferedReader
            var line :String =""
            try {
                do {
                    line= buff.readLine()
                    if(line!=null){
                        content.append(line)
                    }
                }while (line != null)

                buff.close()
            }
            catch (e: Exception){
                Log.d("AAA",e.toString())
            }
            return content.toString()
        }


    }

}

