package com.example.imovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.imovie.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.fragment_home.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        (application as? MyApplication)?.appComponent?.inject(this)

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        setSupportActionBar(binding.root.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setLogo(R.drawable.ic_launcher_foreground)

        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.drawable.ic_launcher_foreground -> Log.i("MainActivity", "logo clicked")
            R.id.ic_favorite_movies -> Log.i("MainActivity", "favorite clicked")
        }
        return true
    }
}
