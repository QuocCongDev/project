package com.example.asynctask

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login_screen.*
import kotlinx.android.synthetic.main.activity_sign_up_customer.*
import kotlinx.android.synthetic.main.activity_update_customer.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class loginScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        val url: String = "http://192.168.1.4:8080/webservice/loginCustomer.php"

        btnLogin.setOnClickListener() {

            loginApp().execute(url)

        }
    }

    inner class loginApp : AsyncTask<String, Void, String>() {


        override fun doInBackground(vararg prams: String?): String {
            return loginCus(prams[0]);
        }

        override fun onPostExecute(result: String?) {

            super.onPostExecute(result)
            if (result.equals("1")) {
                Toast.makeText(
                    this@loginScreen,
                    "User Name or Password wrong please check again",
                    Toast.LENGTH_LONG
                ).show()
            } else {

                var jsonArray: JSONArray = JSONArray(result);
                var objcs: JSONObject = jsonArray.getJSONObject(0)
                var Customer: Customer = Customer(
                    objcs.getInt("id"),
                    objcs.getString("name"),
                    objcs.getString("user_name"),
                    objcs.getString("password")
                )
                val intent: Intent = Intent(this@loginScreen, homeActivity::class.java)
                intent.putExtra("dataCustomer", Customer);
                startActivity(intent)
            }

        }

        private fun loginCus(Url: String?): String {

            var content: StringBuilder = StringBuilder()
            val url: URL = URL(Url)
            val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            try {
                urlConnection.readTimeout = 10000
                urlConnection.connectTimeout = 15000
                urlConnection.requestMethod = "POST"
                var builder = Uri.Builder()
                    .appendQueryParameter("password", txtPassword.text.toString().trim())
                    .appendQueryParameter("userName", lgUserName.text.toString().trim())
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
