package com.example.asynctask

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.util.ArrayList

class roomAdapter(var context : Context, var mangRoom: ArrayList<Room>,var ho :Hotel): BaseAdapter() {
    class viewHolder(row: View) {
        var btnRoom: Button


        init {

            btnRoom = row.findViewById(R.id.btnRoom) as Button
        }

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?
        var viewHolder: viewHolder
        if (convertView == null) {
            var layoutinflater: LayoutInflater = LayoutInflater.from(context)
            view = layoutinflater.inflate(R.layout.list_room, null);
            viewHolder = viewHolder(view)
            view.tag = viewHolder

        } else {

            view = convertView
            viewHolder = convertView.tag as viewHolder

        }
        var cs: Room = getItem(position) as Room
        viewHolder.btnRoom.text = cs.code.toString()
        if(cs.type==1){
            viewHolder.btnRoom.setBackgroundColor(Color.parseColor("#4db35c"));
        }else{

            viewHolder.btnRoom.setBackgroundColor(Color.parseColor("#939995"));
        }


        viewHolder.btnRoom.setOnClickListener(){
            val intent : Intent = Intent(context,viewRoom::class.java)
            intent.putExtra("dataRoom",cs);
            intent.putExtra("dataHotel",ho)

            context.startActivity(intent)
        }
        return view as View;

    }

    override fun getItem(position: Int): Any {
        return mangRoom.get(position);
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    override fun getCount(): Int {
        return mangRoom.size;
    }
}