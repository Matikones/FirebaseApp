package com.example.mimprojectii.Books

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mimprojectii.Database.FirebaseActivity
import com.example.mimprojectii.Games.DatabaseRowGame
import com.example.mimprojectii.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_edit_book.*
import kotlinx.android.synthetic.main.activity_edit_game.*

class EditBook : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var myRef: DatabaseReference
    private var isedit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_book)
        val dane: String?
        val aut: String?
        dane = intent.getStringExtra("tyt")
        aut = intent.getStringExtra("aut")
        Toast.makeText(applicationContext, "${dane}", Toast.LENGTH_LONG).show()

        auth = FirebaseAuth.getInstance()
        var user = auth.currentUser?.uid

        val firebase = FirebaseDatabase.getInstance()
        myRef = firebase.getReference("${user}").child("Books")

        new_title_book.hint = dane
        new_author.hint = aut

        edit_book.setOnClickListener {
            isedit = true
            editBook()
        }

        delete_book.setOnClickListener {
            myRef.child(dane).removeValue()
            Toast.makeText(applicationContext, "Ksiazka ${dane} zostala usunieta", Toast.LENGTH_LONG).show()
            onBackPressed()
        }

        cancel_book.setOnClickListener {
            onBackPressed()
        }
    }


    fun editBook() {

        if(new_title_book.text.toString().isEmpty()){
            new_title_book.error = "Wprowadz tytuł"
            return
        }

        if(new_author.text.toString().isEmpty()){
            new_author.error = "Wprowadz autora"
            return
        }
        val dane: String?
        dane = intent.getStringExtra("tyt")
        myRef.child(dane).removeValue()

        val booktitle = new_title_book.text.toString()
        val author = new_author.text.toString()
        val booktatus = new_book_status.isChecked
        val firebaseInput = DatabaseRowBook(booktitle, author, booktatus)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(isedit) {
                    Toast.makeText(
                        applicationContext,
                        "Ksiazka ${booktitle} została edytowana !",
                        Toast.LENGTH_LONG
                    ).show()

                    myRef.child("${booktitle}").setValue(firebaseInput)
                    onBackPressed()
                    isedit = false
                }
            }
        })
    }
}
