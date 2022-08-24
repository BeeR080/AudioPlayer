package ru.example.audioplayer.data

import androidx.lifecycle.LiveData

class MusicRepository(private val musicDao: MusicDao) {

    val getAllMusic: LiveData<List<MusicList>> = musicDao.getAllMusic()

    suspend fun addMusic(music:MusicList){
        musicDao.addMusic(music)
    }

    suspend fun deleteMusic(music: MusicList){
        musicDao.deleteMusic(music)
    }
}