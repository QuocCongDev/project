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

class HotelAdapter(var context : Context, var mangHotel: ArrayList<Hotel>): BaseAdapter() {
    class viewHolder(row: View) {
        var nameHotel: TextView
        var delHotel: ImageView
        var edtHotel: ImageView

        init {
            nameHotel = row.findViewById(R.id.Id) as TextView
            delHotel = row.findViewById(R.id.imageEdit) as ImageView
            edtHotel = row.findViewById(R.id.imageDel) as ImageView
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
        var cs: Hotel = getItem(position) as Hotel
        viewHolder.nameHotel.text = cs.Name.toString()

        viewHolder.edtHotel.setOnClickListener(){
            val intent : Intent = Intent(context,UpdateCustomer::class.java)
            intent.putExtra("dataCustomer",cs);
            context.startActivity(intent)
        }
        return view as View;

    }

    override fun getItem(position: Int): Any {
        return mangHotel.get(position);
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    override fun getCount(): Int {
        return mangHotel.size;
    }
}