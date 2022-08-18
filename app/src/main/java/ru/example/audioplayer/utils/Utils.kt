package ru.example.audioplayer.utils

import java.util.concurrent.TimeUnit
import java.util.logging.SimpleFormatter

fun formatTime(millsec:Int): String{
    val minutes = millsec / 1000 / 60
    val seconds = millsec / 1000 % 60
    var time = String.format("%02d:%02d", minutes, seconds)

    return time

}