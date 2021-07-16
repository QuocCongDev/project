package com.example.asynctask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*

class homeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        var intent : Intent = getIntent()
        var Cs :Customer = intent.getSerializableExtra("dataCustomer") as Customer
        textView4.setText(Cs.Name.toString())
        btnQlyKs.setOnClickListener(){
          var  a :Intent= Intent(this,controlHotel::class.java)
            a.putExtra("dataCustomer", Cs);
            startActivity(a)

        }
        btnQlyNv.setOnClickListener(){
            var  a :Intent= Intent(this,controlCustomer::class.java)
            a.putExtra("dataCustomer", Cs);
            startActivity(a)

        }
    }
}
