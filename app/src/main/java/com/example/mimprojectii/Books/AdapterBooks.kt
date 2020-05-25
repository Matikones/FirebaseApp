package com.example.mimprojectii.Books

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mimprojectii.Games.EditGame
import com.example.mimprojectii.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.database_row_books.view.*
import kotlinx.android.synthetic.main.database_row_games.view.*

class Adapter(private val dataArray: ArrayList<DatabaseRowBook>, var context: Context?): RecyclerView.Adapter<Adapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.database_row_books, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataArray.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tytulTV.text = dataArray[holder.adapterPosition].tytul
        holder.autorTV.text = dataArray[holder.adapterPosition].autor
        holder.czytaneCB.isChecked = dataArray[holder.adapterPosition].czytane

        holder.itemView.showbookinfo.setOnClickListener {
            var intent = Intent(context, EditBook::class.java)
            intent.putExtra("tyt", holder.tytulTV.text.toString())
            intent.putExtra("aut", holder.autorTV.text.toString())
            context!!.startActivity(intent)
        }
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tytulTV = view.findViewById(R.id.titlebook) as TextView
        val autorTV = view.findViewById(R.id.authorbook) as TextView
        val czytaneCB = view.findViewById(R.id.checkbook) as CheckBox
    }
}