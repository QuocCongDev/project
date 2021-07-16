package com.example.asynctask

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.util.ArrayList

class customAdapter(var context : Context, var mangCustomer: ArrayList<Customer>): BaseAdapter() {
    class viewHolder(row: View) {
        var txtId: TextView
        var txtName: TextView
        var txtUserName: TextView
        var txtPassword: TextView
        var imgEdit: ImageView
        var imgDel: ImageView

        init {
            txtId = row.findViewById(R.id.Id) as TextView
            txtName = row.findViewById(R.id.name) as TextView
            txtUserName = row.findViewById(R.id.username) as TextView
            txtPassword = row.findViewById(R.id.Pasword) as TextView
            imgEdit = row.findViewById(R.id.imageEdit) as ImageView
            imgDel = row.findViewById(R.id.imageDel) as ImageView
        }

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?
        var viewHolder: viewHolder
        if (convertView == null) {
            var layoutinflater: LayoutInflater = LayoutInflater.from(context)
            view = layoutinflater.inflate(R.layout.listcustomer, null);
            viewHolder = viewHolder(view)
            view.tag = viewHolder

        } else {

            view = convertView
            viewHolder = convertView.tag as viewHolder

        }
        var cs: Customer = getItem(position) as Customer
        viewHolder.txtId.text = cs.Id.toString()
        viewHolder.txtUserName.text = cs.userName.toString()
        viewHolder.txtName.text = cs.Name.toString()
        viewHolder.txtPassword.text = cs.Password.toString()
        viewHolder.imgEdit.setOnClickListener(){
            val intent : Intent = Intent(context,UpdateCustomer::class.java)
            intent.putExtra("dataCustomer",cs);
            context.startActivity(intent)
        }
        return view as View;

    }

    override fun getItem(position: Int): Any {
        return mangCustomer.get(position);
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    override fun getCount(): Int {
        return mangCustomer.size;
    }
}