package ru.example.audioplayer.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.example.audioplayer.R
import ru.example.audioplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment,AudioListFragment())
            .commit()
    }
}