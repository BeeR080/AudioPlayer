package ru.example.audioplayer.view

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import ru.example.audioplayer.R
import ru.example.audioplayer.databinding.ActivityMainBinding
import ru.example.audioplayer.service.AudioService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment, AudioListFragment())
            .commit()


    }
}