package com.example.imovie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.imovie.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        (application as? MyApplication)?.appComponent?.inject(this)

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}
