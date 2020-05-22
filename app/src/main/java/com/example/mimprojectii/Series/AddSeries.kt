package com.example.mimprojectii.Series

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mimprojectii.Books.Adapter
import com.example.mimprojectii.Books.DatabaseRowBook
import com.example.mimprojectii.Database.FirebaseActivity
import com.example.mimprojectii.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_series.*
import kotlinx.android.synthetic.main.database_row_series.*
import kotlinx.android.synthetic.main.fragment_books.*
import java.util.*
import kotlin.collections.ArrayList

class AddSeries : AppCompatActivity() {

    private lateinit var myRef: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var listOfItems: ArrayList<DatabaseRowSeries>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_series)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser?.uid

        val firebase = FirebaseDatabase.getInstance()
        myRef = firebase.getReference("${user}")

        addseries_button.setOnClickListener {
            val intent = Intent(applicationContext, FirebaseActivity::class.java)
            startActivity(intent)
            addSeries()
        }

        cancelseries_botton.setOnClickListener {
            val intent = Intent(applicationContext, FirebaseActivity::class.java)
            startActivity(intent)
        }

    }

    fun addSeries() {
        val tytul = add_title.text.toString()
        val sezony = add_sezon.text.toString()
        val ogladane = add_status.isChecked
        val firebaseInput = DatabaseRowSeries(tytul, sezony, ogladane)
        myRef.child("Series").child("${Date().time}").setValue(firebaseInput)
    }
}
