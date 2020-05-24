package com.example.mimprojectii.Movies

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
import com.example.mimprojectii.Games.AdapterGames
import com.example.mimprojectii.Games.DatabaseRowGame

import com.example.mimprojectii.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_books.*
import kotlinx.android.synthetic.main.fragment_games.*
import kotlinx.android.synthetic.main.fragment_movies.*
import java.util.ArrayList

class MoviesFragment : Fragment() {

    private lateinit var listOfItems: ArrayList<DatabaseRowMovie>
    private lateinit var myRef: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val firebase = FirebaseDatabase.getInstance()

        auth = FirebaseAuth.getInstance()
        var user = auth.currentUser?.uid

        myRef = firebase.getReference("${user}").child("Movies")

        recyclerViewMovies.layoutManager = GridLayoutManager(context, 1)

        add_movie.setOnClickListener {
            val intent = Intent(context, AddMovie::class.java)
            startActivity(intent)
        }


        myRef.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listOfItems = ArrayList()
                for(i in dataSnapshot.children){
                    val newRow = i.getValue(DatabaseRowMovie::class.java)
                    listOfItems.add(newRow!!)
                }
                setupAdapter(listOfItems)
            }
        })
    }

    private fun setupAdapter(arrayData: ArrayList<DatabaseRowMovie>){
        recyclerViewMovies.adapter = AdapterMovie(arrayData)
    }
}

