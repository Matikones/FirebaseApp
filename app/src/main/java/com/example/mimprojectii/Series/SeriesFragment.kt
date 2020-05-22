package com.example.mimprojectii.Series

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.example.mimprojectii.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_series.*
import kotlinx.android.synthetic.main.fragment_books.*
import kotlinx.android.synthetic.main.fragment_series.*

class SeriesFragment : Fragment() {

    private lateinit var myRef: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var listOfItems: ArrayList<DatabaseRowSeries>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_series, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firebase = FirebaseDatabase.getInstance()

        auth = FirebaseAuth.getInstance()
        var user = auth.currentUser?.uid

        myRef = firebase.getReference("${user}").child("Series")

        recyclerViewSeries.layoutManager = GridLayoutManager(context, 1)

        add_series.setOnClickListener{
            val intent = Intent(context, AddSeries::class.java)
            startActivity(intent)
        }

        myRef.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listOfItems = java.util.ArrayList()
                for(i in dataSnapshot.children){
                    val newRow = i.getValue(DatabaseRowSeries::class.java)
                    listOfItems.add(newRow!!)
                }
                setupAdapter(listOfItems)
            }
        })
    }

    private fun setupAdapter(arrayData: ArrayList<DatabaseRowSeries>){
        recyclerViewSeries.adapter = AdapterSeries(arrayData)
    }
}
