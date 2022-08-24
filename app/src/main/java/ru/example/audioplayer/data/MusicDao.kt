package ru.example.audioplayer.data

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMusic(music:MusicList)

    @Delete
    suspend fun deleteMusic(music: MusicList)

    @Query("SELECT * FROM my_tracks ORDER BY id ASC")
    fun getAllMusic(): LiveData<List<MusicList>>





}