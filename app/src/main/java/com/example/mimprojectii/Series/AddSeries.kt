package com.example.mimprojectii.Series

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
    var dodane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_series)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser?.uid

        val firebase = FirebaseDatabase.getInstance()
        myRef = firebase.getReference("${user}").child("Series")

        addseries_button.setOnClickListener {
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
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.hasChild("${tytul}")){
                    if(!dodane) {
                        Toast.makeText(
                            applicationContext,
                            "Serial ${tytul} juz istnieje w bazie!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                else{
                    dodane = true
                    myRef.child("${tytul}").setValue(firebaseInput)
                    if(dodane) {
                        Toast.makeText(
                            applicationContext,
                            "Dodano serial ${tytul}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    val intent = Intent(applicationContext, FirebaseActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }
}
