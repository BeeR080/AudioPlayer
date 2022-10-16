package ru.example.audioplayer.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore



fun formatTime(millsec:Int): String{
    val minutes = millsec / 1000 / 60
    val seconds = millsec / 1000 % 60
    var time = String.format("%02d:%02d", minutes, seconds)

    return time
}




@SuppressLint("Range")
fun getArtistNameFromUri(context: Context, uri: Uri): String{

    val metadata = arrayOf(
        MediaStore.Audio.Media.ARTIST
    )
 var artistName: String= "Lupa"
    val cursor:Cursor? = context.applicationContext.contentResolver.query(
        uri,
        metadata,
         null,
        null,
        null)

    if (cursor!=null && cursor.moveToFirst()){
        cursor.moveToFirst()
        artistName = cursor.getString(cursor.getColumnIndex("artist"))

    }
    if (cursor!=null){
        cursor.close()
    }
    return artistName
}



@SuppressLint("Range")
fun getArtistMusicTitleFromUri(context: Context, uri: Uri): String{
    val activity = Activity()
    val metadata = arrayOf(
        MediaStore.Audio.Media.TITLE
    )
    var artistAlbum: String= "Pupin"
    val cursor:Cursor? = context.applicationContext.contentResolver!!.query(
        uri,
        metadata,
        null,
        null,
        null)

    if (cursor!=null && cursor.moveToFirst()){
        cursor.moveToFirst()
        artistAlbum = cursor.getString(cursor.getColumnIndex("title"))
    }
    if (cursor!=null){
        cursor.close()
    }
    return artistAlbum
}