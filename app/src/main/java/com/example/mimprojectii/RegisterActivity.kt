package com.example.mimprojectii

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        button_singup.setOnClickListener {
            Register()
        }
    }

    private fun Register() {
        if (email_singup.text.toString().isEmpty()) {
            email_singup.error = "Wprowadz email"
            email_singup.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email_singup.text.toString()).matches()) {
            email_singup.error = "Wprowadz poprawny email"
            email_singup.requestFocus()
            return
        }

        if (password_singup.text.toString().isEmpty()) {
            password_singup.error = "Wprowadz haslo"
            password_singup.requestFocus()
            return
        }

        if(password_singup.text.toString().length < 6){
            password_singup.error = "Haslo musi miec przynajmniej 6 znakow"
            password_singup.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(
            email_singup.text.toString(),
            password_singup.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        baseContext,
                        "Rejestracja pomyslna",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
            .addOnFailureListener {
                Toast.makeText(
                    baseContext,
                    "Rejestracja nie powiodła się, proszę spróbować ponownie",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

}
