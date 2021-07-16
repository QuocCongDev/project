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

class HotelAdapter2(var context : Context, var mangHotel: ArrayList<Hotel>,var custom :Customer): BaseAdapter() {
    class viewHolder(row: View) {
        var nameHotel: TextView
        var delHotel: ImageView
        var edtHotel: ImageView

        init {
            nameHotel = row.findViewById(R.id.nameHotel) as TextView
            delHotel = row.findViewById(R.id.delHotel) as ImageView
            edtHotel = row.findViewById(R.id.edtHotel) as ImageView
        }

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?
        var viewHolder: viewHolder
        if (convertView == null) {
            var layoutinflater: LayoutInflater = LayoutInflater.from(context)
            view = layoutinflater.inflate(R.layout.list_hotel_item, null);
            viewHolder = viewHolder(view)
            view.tag = viewHolder

        } else {

            view = convertView
            viewHolder = convertView.tag as viewHolder

        }
        var ho: Hotel = getItem(position) as Hotel
        viewHolder.nameHotel.text = ho.Name.toString()

        viewHolder.edtHotel.setOnClickListener(){
            val intent : Intent = Intent(context,ViewHotel::class.java)
            intent.putExtra("dataHotel",ho);
            intent.putExtra("dataCustomer",custom);

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