package com.example.mimprojectii.Series

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mimprojectii.R

class AdapterSeries(private val dataArray: ArrayList<DatabaseRowSeries>): RecyclerView.Adapter<AdapterSeries.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterSeries.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.database_row_series, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataArray.size
    }

    override fun onBindViewHolder(holder: AdapterSeries.MyViewHolder, position: Int) {
        holder.tytulTv.text = dataArray[holder.adapterPosition].tytul
        holder.liczbaTv.text = dataArray[holder.adapterPosition].sezony
        holder.ogladane.isChecked = dataArray[holder.adapterPosition].ogladane
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tytulTv = view.findViewById(R.id.titleseries) as TextView
        var liczbaTv = view.findViewById(R.id.numberofsezon) as TextView
        val ogladane = view.findViewById(R.id.checkseries) as CheckBox
    }
}