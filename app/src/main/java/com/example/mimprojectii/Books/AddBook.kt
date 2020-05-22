package com.example.mimprojectii.Books

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        auth = FirebaseAuth.getInstance()
        val firebase = FirebaseDatabase.getInstance()

        var user = auth.currentUser?.uid

        myRef = firebase.getReference("${user}")

        addbook_button.setOnClickListener {
            val intent = Intent(applicationContext, FirebaseActivity::class.java)
            startActivity(intent)
            addBook()
        }

        cancelbook_botton.setOnClickListener {
            val intent = Intent(applicationContext, FirebaseActivity::class.java)
            startActivity(intent)
        }
    }

    fun addBook(){
        val booktitle = add_title.text.toString()
        val bookauthor = add_author.text.toString()
        val bookstatus = add_status.isChecked
        val firebaseInput = DatabaseRowBook(booktitle, bookauthor, bookstatus)
        myRef.child("Books").child("${Date().time}").setValue(firebaseInput)
    }
}
