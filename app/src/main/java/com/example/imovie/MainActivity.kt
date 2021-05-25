package com.example.imovie

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.imovie.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.fragment_home.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        (application as? MyApplication)?.appComponent?.inject(this)

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        configActionBar(binding.root.toolbar)
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

    private fun configActionBar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setLogo(R.drawable.ic_launcher_foreground)
    }
}
