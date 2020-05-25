package com.example.mimprojectii.Games

import android.content.Intent
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mimprojectii.R
import kotlinx.android.synthetic.main.database_row_games.view.*
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class AdapterGames(private val dataArray: ArrayList<DatabaseRowGame>, var context: Context?): RecyclerView.Adapter<AdapterGames.MyViewHolder>() {

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

        holder.itemView.showgameinfo.setOnClickListener {
            var intent = Intent(context, EditGame::class.java)
            intent.putExtra("tyt", holder.tytul.text.toString())
            context!!.startActivity(intent)
        }
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tytul = view.findViewById(R.id.titlegame) as TextView
        val grane = view.findViewById(R.id.checkgame) as CheckBox
    }
}