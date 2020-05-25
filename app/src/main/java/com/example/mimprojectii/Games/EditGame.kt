package com.example.mimprojectii.Games

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mimprojectii.Database.FirebaseActivity
import com.example.mimprojectii.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_book.*
import kotlinx.android.synthetic.main.activity_add_game.*
import kotlinx.android.synthetic.main.activity_edit_game.*

class EditGame : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var myRef: DatabaseReference
    private var isedit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_game)
        isedit = false
        val dane: String?
        dane = intent.getStringExtra("tyt")
        Toast.makeText(applicationContext, "${dane}", Toast.LENGTH_LONG).show()

        auth = FirebaseAuth.getInstance()
        var user = auth.currentUser?.uid

        val firebase = FirebaseDatabase.getInstance()
        myRef = firebase.getReference("${user}").child("Games")

        new_title_game.hint = dane

        edit_game.setOnClickListener {
            isedit = true
            editGame()
        }

        delete_game.setOnClickListener {
            myRef.child(dane).removeValue()
            Toast.makeText(applicationContext, "Gra ${dane} zostala usunieta", Toast.LENGTH_LONG).show()
            onBackPressed()
        }

        cancel_game.setOnClickListener {
            onBackPressed()
        }
    }


    fun editGame() {

        if(new_title_game.text.toString().isEmpty()){
            new_title_game.error = "Wprowadz tytuł"
            return
        }
        val dane: String?
        dane = intent.getStringExtra("tyt")
        myRef.child(dane).removeValue()
        val gametitle = new_title_game.text.toString()
        val gamestatus = new_game_status.isChecked
        val firebaseInput = DatabaseRowGame(gametitle, gamestatus)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (isedit) {
                    Toast.makeText(
                        applicationContext,
                        "Gra ${gametitle} została edytowana !",
                        Toast.LENGTH_LONG
                    ).show()
                    onBackPressed()
                    myRef.child("${gametitle}").setValue(firebaseInput)
                    isedit = false
                }
            }
        })
    }
}
