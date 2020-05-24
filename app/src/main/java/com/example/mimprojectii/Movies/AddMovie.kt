package com.example.mimprojectii.Movies


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mimprojectii.Books.DatabaseRowBook
import com.example.mimprojectii.Database.FirebaseActivity
import com.example.mimprojectii.R
import com.google.android.material.elevation.ElevationOverlayProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_book.*
import kotlinx.android.synthetic.main.activity_add_movie.*
import kotlinx.android.synthetic.main.activity_main.*
import org.intellij.lang.annotations.MagicConstant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat

class AddMovie : AppCompatActivity() {

    private var movie: String = ""
    private var canAdd: Boolean = false
    var dodane: Boolean = false
    private lateinit var myRef: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)

        auth = FirebaseAuth.getInstance()
        val firebase = FirebaseDatabase.getInstance()

        var user = auth.currentUser?.uid

        myRef = firebase.getReference("${user}").child("Movies")

        searchmovie_button.setOnClickListener {
            movie = search_text.text.toString()
            APIConnect()
        }

        add_movie.setOnClickListener {
            if(canAdd) {
                addMovie()
                movie = ""
            }
        }

        cancel_movie.setOnClickListener {
            val intent = Intent(applicationContext, FirebaseActivity::class.java)
            startActivity(intent)
        }
    }
   // https://www.omdbapi.com/?t=Shrek&apikey=2ccc7b8a
    fun APIConnect(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val jsonPlaceHolder: JsonPlaceHolder = retrofit.create(JsonPlaceHolder::class.java)

        val call: Call<Post> = jsonPlaceHolder.getPosts(movie, "2ccc7b8a")

        //Toast.makeText(applicationContext, "${call.request().url()}", Toast.LENGTH_LONG).show()

        call.enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (!response.isSuccessful) {
                    Toast.makeText(applicationContext, "Film nie istnieje lub błąd sieci", Toast.LENGTH_LONG).show()
                    return
                }
                val posts = response.body()!!
                canAdd = true
                movie_name.text = posts.getTitle()
            }

            override fun onFailure(
                call: Call<Post>,
                t: Throwable
            ) {
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun addMovie(){
        val movietitle = movie_name.text.toString()
        val moviestatus = movie_status.isChecked
        val firebaseInput = DatabaseRowMovie(movietitle, moviestatus)

        myRef.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.hasChild("${movietitle}")){
                    if(!dodane) {
                        Toast.makeText(
                            applicationContext,
                            "Film ${movietitle} juz istnieje w bazie!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                else{
                    dodane = true
                    myRef.child("${movietitle}").setValue(firebaseInput)
                    if(dodane) {
                        Toast.makeText(
                            applicationContext,
                            "Dodano film ${movietitle}",
                            Toast.LENGTH_LONG
                        ).show()
                        val intent = Intent(applicationContext, FirebaseActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        })
    }
}