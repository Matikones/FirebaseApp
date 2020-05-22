package com.example.mimprojectii.Books

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_books.view.*
import com.example.mimprojectii.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.fragment_books.*
import java.util.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_firebase.*

class BooksFragment : Fragment() {

    private lateinit var listOfItems: ArrayList<DatabaseRowBook>
    private lateinit var myRef: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_books, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val firebase = FirebaseDatabase.getInstance()

        auth = FirebaseAuth.getInstance()
        var user = auth.currentUser?.uid

        myRef = firebase.getReference("${user}").child("Books")

        recyclerView.layoutManager = GridLayoutManager(context, 1)

        add_book.setOnClickListener {
            val intent = Intent(context, AddBook::class.java)
            startActivity(intent)
        }

        myRef.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listOfItems = ArrayList()
                for(i in dataSnapshot.children){
                    val newRow = i.getValue(DatabaseRowBook::class.java)
                    listOfItems.add(newRow!!)
                }
                setupAdapter(listOfItems)
            }
        })
    }

    private fun setupAdapter(arrayData: ArrayList<DatabaseRowBook>){
        recyclerView.adapter = Adapter(arrayData)
    }
}
