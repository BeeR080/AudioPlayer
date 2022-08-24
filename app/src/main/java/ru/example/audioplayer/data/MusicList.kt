package ru.example.audioplayer.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "my_tracks")
data class MusicList(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val singerName:String,
    val signerTrack:String

)
