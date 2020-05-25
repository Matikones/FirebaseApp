package com.example.mimprojectii.Movies

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
import kotlinx.android.synthetic.main.activity_edit_movie.*

class EditMovie : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var myRef: DatabaseReference
    private var isedit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_movie)
        isedit = false
        val dane: String?
        dane = intent.getStringExtra("tyt")
        Toast.makeText(applicationContext, "${dane}", Toast.LENGTH_LONG).show()

        auth = FirebaseAuth.getInstance()
        var user = auth.currentUser?.uid

        val firebase = FirebaseDatabase.getInstance()
        myRef = firebase.getReference("${user}").child("Movies")

        new_title_movie.hint = dane

        edit_movie.setOnClickListener {
            isedit = true
            editMovie()
        }

        delete_movie.setOnClickListener {
            myRef.child(dane).removeValue()
            Toast.makeText(applicationContext, "Film ${dane} zostal usuniety", Toast.LENGTH_LONG).show()
            onBackPressed()
        }

        cancel_movie.setOnClickListener {
            onBackPressed()
        }

    }


    fun editMovie() {

        if(new_title_movie.text.toString().isEmpty()){
            new_title_movie.error = "Wprowadz tytuł"
            return
        }
        val dane: String?
        dane = intent.getStringExtra("tyt")
        myRef.child(dane).removeValue()
        val movietitle = new_title_movie.text.toString()
        val moviestatus = new_movie_status.isChecked
        val firebaseInput = DatabaseRowMovie(movietitle, moviestatus)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (isedit) {
                    Toast.makeText(
                        applicationContext,
                        "Film ${movietitle} został edytowany !",
                        Toast.LENGTH_LONG
                    ).show()
                    onBackPressed()
                    myRef.child("${movietitle}").setValue(firebaseInput)
                    isedit = false
                }
            }
        })
    }
}
