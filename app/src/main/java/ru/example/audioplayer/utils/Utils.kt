package ru.example.audioplayer.utils

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import java.util.concurrent.TimeUnit
import java.util.logging.SimpleFormatter

fun formatTime(millsec:Int): String{
    val minutes = millsec / 1000 / 60
    val seconds = millsec / 1000 % 60
    var time = String.format("%02d:%02d", minutes, seconds)

    return time

}

fun getArtistNameFromUri(context: Context, uri: Uri): String{
    val metadata = arrayOf(
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.TITLE
    )
 var artistName: String= "Lupa "
    val cursor:Cursor? = context.contentResolver.query(uri,
        metadata,
        null,
        null,
        null)

    if (cursor!=null && cursor.moveToFirst()){
        artistName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))

    }
    if (cursor!=null){
        cursor.close()
    }
    return artistName
}


fun getArtistMusicTitleFromUri(context: Context, uri: Uri): String{
    val metadata = arrayOf(
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.TITLE
    )
    var artistAlbum: String=" Pupin"
    val cursor:Cursor? = context.contentResolver.query(uri,
        metadata,
        null,
        null,
        null)

    if (cursor!=null && cursor.moveToFirst()){
        artistAlbum = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE).toString()
    }
    if (cursor!=null){
        cursor.close()
    }
    return artistAlbum
}