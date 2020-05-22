package com.example.mimprojectii.Games

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mimprojectii.Books.Adapter
import com.example.mimprojectii.Books.BooksFragment
import com.example.mimprojectii.Books.DatabaseRowBook
import com.example.mimprojectii.Database.FirebaseActivity
import com.example.mimprojectii.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_book.*
import kotlinx.android.synthetic.main.activity_add_book.add_title
import kotlinx.android.synthetic.main.activity_add_game.*
import kotlinx.android.synthetic.main.fragment_books.*
import java.util.*

class AddGame : AppCompatActivity() {

    private lateinit var myRef: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var listOfItems: ArrayList<DatabaseRowGame>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_game)
        auth = FirebaseAuth.getInstance()
        val firebase = FirebaseDatabase.getInstance()

        var user = auth.currentUser?.uid

        myRef = firebase.getReference("${user}")

        addgame_button.setOnClickListener {
            val intent = Intent(applicationContext, FirebaseActivity::class.java)
            startActivity(intent)
            supportFragmentManager.beginTransaction().replace(R.id.container, GamesFragment())
            addGame()
        }

        cancelgame_botton.setOnClickListener {
            val intent = Intent(applicationContext, FirebaseActivity::class.java)
            startActivity(intent)
            supportFragmentManager.beginTransaction().replace(R.id.container, GamesFragment())
        }
    }

    fun addGame(){
        val gametitle = add_title.text.toString()
        val gamestatus = add_gamestatus.isChecked
        val firebaseInput = DatabaseRowGame(gametitle, gamestatus)
        myRef.child("Games").child("${Date().time}").setValue(firebaseInput)
    }
}
