package com.example.mimprojectii.Games

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mimprojectii.Books.Adapter
import com.example.mimprojectii.Books.AddBook
import com.example.mimprojectii.Books.DatabaseRowBook

import com.example.mimprojectii.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_books.*
import kotlinx.android.synthetic.main.fragment_games.*
import java.util.ArrayList

class GamesFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var myRef: DatabaseReference
    private lateinit var listOfItems: ArrayList<DatabaseRowGame>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_games, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        var user = auth.currentUser?.uid

        val firebase = FirebaseDatabase.getInstance()
        myRef = firebase.getReference("${user}").child("Games")

        recyclerViewGames.layoutManager = GridLayoutManager(context, 1)

        add_game.setOnClickListener {
            val intent = Intent(context, AddGame::class.java)
            startActivity(intent)
        }

        myRef.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listOfItems = ArrayList()
                for(i in dataSnapshot.children){
                    val newRow = i.getValue(DatabaseRowGame::class.java)
                    listOfItems.add(newRow!!)
                }
                setupAdapter(listOfItems)
            }
        })
    }

    private fun setupAdapter(arrayData: ArrayList<DatabaseRowGame>){
        recyclerViewGames.adapter = AdapterGames(arrayData)
    }
}
