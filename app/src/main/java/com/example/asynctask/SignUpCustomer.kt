package com.example.asynctask

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sign_up_customer.*
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class SignUpCustomer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_customer)
        val urlString="http://192.168.1.4:8080/webservice/InsertCustomer.php"
        val btn :Button
        btn = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener(){
        insertData().execute(urlString)

        }
        btnBack.setOnClickListener(){

            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
    inner class insertData: AsyncTask<String,Void,String>(){


        override fun doInBackground(vararg prams: String?): String {
            return PostData(prams[0]);
        }
        override fun onPostExecute(result: String?) {

            super.onPostExecute(result)
//        if(result.equals("1")){
//            Toast.makeText(this@SignUpCustomer,"thêm mới thành công",Toast.LENGTH_LONG).show()
//        }else{
//
//            Toast.makeText(this@SignUpCustomer,"thêm mới thất bại",Toast.LENGTH_LONG).show()
//        }
            Toast.makeText(this@SignUpCustomer,result.toString(),Toast.LENGTH_LONG).show()

        }
        private  fun PostData(Url:String?):String{

            var content: StringBuilder = StringBuilder()
            val url: URL = URL(Url)
            val urlConnection : HttpURLConnection = url.openConnection() as HttpURLConnection
            try {
                urlConnection.readTimeout =10000
                urlConnection.connectTimeout=15000
                urlConnection.requestMethod = "POST"
                var builder = Uri.Builder()
                    .appendQueryParameter("Name",txtName.text.toString().trim())
                    .appendQueryParameter("userName",txtPass.text.toString().trim())
                    .appendQueryParameter("Password",txtUserName.text.toString().trim())
                val query = builder.build().encodedQuery
                val os = urlConnection.outputStream
                val writer = BufferedWriter(OutputStreamWriter(os,"UTF-8"))
                writer.write(query)
                writer.flush()
                writer.close()
                os.close()
                urlConnection.connect()
            }catch (e1:IOException){
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

}catch(e:IOException){
 e.printStackTrace()
            return "ERORR"
}finally {
    urlConnection.disconnect()
}

        }


    }

}
