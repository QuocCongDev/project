package com.example.asynctask

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.ArrayList

class checkTypeRoomAdapter(var context : Context, var mangRoom: ArrayList<Room>,var typeRoom:typeRoom?): BaseAdapter() {
    class viewHolder(row: View) {
        var checkRoomName: TextView
        var checkTypeRoom :CheckBox

        init {

            checkRoomName = row.findViewById(R.id.checkRoomName) as TextView
            checkTypeRoom = row.findViewById(R.id.checkTypeRoom) as CheckBox
        }

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?
        var viewHolder: viewHolder
        if (convertView == null) {
            var layoutinflater: LayoutInflater = LayoutInflater.from(context)
            view = layoutinflater.inflate(R.layout.check_type_room, null);
            viewHolder = viewHolder(view)
            view.tag = viewHolder

        } else {

            view = convertView
            viewHolder = convertView.tag as viewHolder

        }
        var cs: Room = getItem(position) as Room
        viewHolder.checkRoomName.text = cs.code
        if(cs.type == typeRoom?.id){
            viewHolder.checkTypeRoom.isSelected;

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