package com.example.mimprojectii

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.database.*
import com.example.mimprojectii.Database.FirebaseActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        //dodane by z kazdym uruchomieniem apki trzeba bylo sie zalogowac
        auth.signOut()

        button_login.setOnClickListener {
           Login()
        }

        button_register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun Login() {
        if (email_login.text.toString().isEmpty()) {
            email_login.error = "Wprowadz email"
            email_login.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email_login.text.toString()).matches()) {
            email_login.error = "Wprowadz poprawny email"
            email_login.requestFocus()
            return
        }

        if (password_login.text.toString().isEmpty()) {
            password_login.error = "Wprowadz haslo"
            password_login.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(email_login.text.toString(), password_login.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    updateUI(null)
                    Toast.makeText(this, "Błędny email lub hasło", Toast.LENGTH_LONG).show()
                    password_login.setText("")
                }
            }
    }

    override fun onStart() {
        super.onStart()
        val currentuser = auth.currentUser
        updateUI(currentuser)
    }

    private fun updateUI(currentuser: FirebaseUser?) {
        if(currentuser != null){
            val intent = Intent(this, FirebaseActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


}
