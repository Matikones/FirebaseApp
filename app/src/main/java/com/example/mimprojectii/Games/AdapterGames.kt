package com.example.mimprojectii.Games

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mimprojectii.R

class AdapterGames(private val dataArray: ArrayList<DatabaseRowGame>): RecyclerView.Adapter<AdapterGames.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterGames.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.database_row_games, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataArray.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tytul.text = dataArray[holder.adapterPosition].game
        holder.grane.isChecked = dataArray[holder.adapterPosition].grane
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tytul = view.findViewById(R.id.titlegame) as TextView
        val grane = view.findViewById(R.id.checkgame) as CheckBox
    }
}