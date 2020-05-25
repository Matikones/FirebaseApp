package com.example.mimprojectii.Movies

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mimprojectii.Games.EditGame
import com.example.mimprojectii.Movies.DatabaseRowMovie
import com.example.mimprojectii.R
import kotlinx.android.synthetic.main.database_row_games.view.*
import kotlinx.android.synthetic.main.database_row_movies.view.*

class AdapterMovie(private val dataArray: ArrayList<DatabaseRowMovie>, var context: Context?): RecyclerView.Adapter<AdapterMovie.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterMovie.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.database_row_movies, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataArray.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tytul.text = dataArray[holder.adapterPosition].tytul
        holder.ogladane.isChecked = dataArray[holder.adapterPosition].ogladane

        holder.itemView.showmovieinfo.setOnClickListener {
            var intent = Intent(context, EditMovie::class.java)
            intent.putExtra("tyt", holder.tytul.text.toString())
            context!!.startActivity(intent)
        }
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tytul = view.findViewById(R.id.titlemovie) as TextView
        val ogladane = view.findViewById(R.id.checkmovie) as CheckBox
    }
}