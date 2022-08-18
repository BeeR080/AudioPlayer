package ru.example.audioplayer.utils

import java.util.concurrent.TimeUnit
import java.util.logging.SimpleFormatter

fun formatTime(millsec:Int): String{
    val minutes = TimeUnit.MINUTES.toMinutes(millsec.toLong()).toString()
    val seconds = TimeUnit.SECONDS.toSeconds(millsec.toLong()).toString()
    var time = "$minutes:$seconds"

    return time

}