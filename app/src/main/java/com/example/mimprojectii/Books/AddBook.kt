package com.example.mimprojectii.Books

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mimprojectii.Database.FirebaseActivity
import com.example.mimprojectii.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_book.*
import kotlinx.android.synthetic.main.fragment_books.*
import java.util.*


class AddBook : AppCompatActivity() {

    private lateinit var myRef: DatabaseReference
    private lateinit var auth: FirebaseAuth
    var dodane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        auth = FirebaseAuth.getInstance()
        val firebase = FirebaseDatabase.getInstance()

        var user = auth.currentUser?.uid

        myRef = firebase.getReference("${user}").child("Books")

        addbook_button.setOnClickListener {
            dodane = true
            addBook()
        }

        cancelbook_botton.setOnClickListener {
            onBackPressed()
        }
    }

    fun addBook(){

        if(add_title.text.toString().isEmpty()){
            add_title.error = "Wprowadz tytuł"
            return
        }

        if(add_author.text.toString().isEmpty()){
            add_author.error = "Wprowadz autora"
            return
        }

        val booktitle = add_title.text.toString()
        val bookauthor = add_author.text.toString()
        val bookstatus = add_status.isChecked
        val firebaseInput = DatabaseRowBook(booktitle, bookauthor, bookstatus)

        myRef.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.hasChild("${booktitle}")){
                    if(dodane) {
                        Toast.makeText(
                            applicationContext,
                            "Ksiazka ${booktitle} juz istnieje w bazie!",
                            Toast.LENGTH_LONG
                        ).show()
                        dodane = false
                    }
                }
                else{
                    if(dodane) {
                        Toast.makeText(
                            applicationContext,
                            "Dodano ksiazke ${booktitle}",
                            Toast.LENGTH_LONG
                        ).show()
                        myRef.child("${booktitle}").setValue(firebaseInput)
                        onBackPressed()
                        dodane = false
                    }
                }
            }
        })
    }
}
