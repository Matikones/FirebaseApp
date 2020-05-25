package com.example.mimprojectii.Series

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mimprojectii.Database.FirebaseActivity
import com.example.mimprojectii.Games.DatabaseRowGame
import com.example.mimprojectii.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_edit_game.*
import kotlinx.android.synthetic.main.activity_edit_series.*

class EditSeries : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var myRef: DatabaseReference
    private var isedit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_series)
        isedit = false
        val dane: String?
        dane = intent.getStringExtra("tyt")
        Toast.makeText(applicationContext, "${dane}", Toast.LENGTH_LONG).show()

        auth = FirebaseAuth.getInstance()
        var user = auth.currentUser?.uid

        val firebase = FirebaseDatabase.getInstance()
        myRef = firebase.getReference("${user}").child("Series")

        new_title_series.hint = dane

        edit_series.setOnClickListener {
            isedit = true
            editSeries()
        }

        delete_series.setOnClickListener {
            myRef.child(dane).removeValue()
            Toast.makeText(applicationContext, "Serial ${dane} zostal usuniety", Toast.LENGTH_LONG).show()
            onBackPressed()
            finish()
        }

        cancel_series.setOnClickListener {
            onBackPressed()
        }
    }





    fun editSeries() {

        if(new_title_series.text.toString().isEmpty()){
            new_title_series.error = "Wprowadz tytuł"
            return
        }

        if(new_sezons.text.toString().isEmpty()){
            new_sezons.error = "Wprowadz sezony"
            return
        }

        val dane: String?
        dane = intent.getStringExtra("tyt")
        myRef.child(dane).removeValue()

        val serietitle = new_title_series.text.toString()
        val serienumb = new_sezons.text.toString()
        val serietatus = new_series_status.isChecked
        val firebaseInput = DatabaseRowSeries(serietitle, serienumb, serietatus)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (isedit) {
                    Toast.makeText(
                        applicationContext,
                        "Serial ${serietitle} został edytowany !",
                        Toast.LENGTH_LONG
                    ).show()
                    onBackPressed()
                    myRef.child("${serietitle}").setValue(firebaseInput)
                    isedit = false
                }
            }
        })
    }
}
