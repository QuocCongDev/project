package com.example.asynctask

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.ArrayList

class typeRoomAdapter(var context : Context, var mangTypeRoom: ArrayList<typeRoom>): BaseAdapter() {
    class viewHolder(row: View) {
        var nameTypeRoom: TextView
        var prHTypeRoom: TextView
        var prDTypeRoom: TextView
        var edtTypeRoom: ImageView
        var delTypeRoom: ImageView


        init {
            nameTypeRoom = row.findViewById(R.id.nameTypeRoom) as TextView
            prHTypeRoom = row.findViewById(R.id.prHTypeRoom) as TextView
            prDTypeRoom = row.findViewById(R.id.prDTypeRoom) as TextView
            edtTypeRoom = row.findViewById(R.id.edtTypeRoom) as ImageView
            delTypeRoom = row.findViewById(R.id.delTypeRoom) as ImageView

        }

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?
        var viewHolder: viewHolder
        if (convertView == null) {
            var layoutinflater: LayoutInflater = LayoutInflater.from(context)
            view = layoutinflater.inflate(R.layout.type_room_hotel, null);
            viewHolder = viewHolder(view)
            view.tag = viewHolder

        } else {

            view = convertView
            viewHolder = convertView.tag as viewHolder

    }
        var tr: typeRoom = getItem(position) as typeRoom
        viewHolder.nameTypeRoom.text = tr.name.toString()
        viewHolder.prHTypeRoom.text = tr.priceHour.toString()
        viewHolder.prDTypeRoom.text = tr.priceDay.toString()

        viewHolder.edtTypeRoom.setOnClickListener(){
            val a : Intent = Intent(context,UpdateTypeRoom::class.java)
            a.putExtra("dataTypeRoom",tr);
            context.startActivity(a)
        }
        return view as View;

    }

    override fun getItem(position: Int): Any {
        return mangTypeRoom.get(position);
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    override fun getCount(): Int {
        return mangTypeRoom.size;
    }
}


