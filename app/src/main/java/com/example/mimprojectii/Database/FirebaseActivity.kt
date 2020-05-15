package com.example.mimprojectii.Database

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Layout
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.mimprojectii.Books.BooksFragment
import com.example.mimprojectii.Games.GamesFragment
import com.example.mimprojectii.MainActivity
import com.example.mimprojectii.Movies.MoviesFragment
import com.example.mimprojectii.R
import com.example.mimprojectii.Series.SeriesFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.app_bar_firebase.*

class FirebaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var auth: FirebaseAuth
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase)

        auth = FirebaseAuth.getInstance()

        toolbar = findViewById(R.id.toolbarFb)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.title = "Projekt"
        //toolbar.setTitleTextColor(Color.YELLOW)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
        this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.firebase, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_logout -> {
                auth.signOut()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this, "Wylogowano pomyślnie", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.nav_games ->{
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, GamesFragment())
                    .commitNow()
                toolbar.title = "Gry"
            }
            R.id.nav_movies ->{
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MoviesFragment())
                    .commitNow()
                toolbar.title = "Filmy"
            }
            R.id.nav_series ->{
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SeriesFragment())
                    .commitNow()
                toolbar.title = "Seriale"
            }
            R.id.nav_books ->{
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, BooksFragment())
                    .commitNow()
                toolbar.title = "Książki"
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return super.onOptionsItemSelected(item)
    }
}
