package ru.example.audioplayer.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MusicViewModel(application: Application): AndroidViewModel(application) {

    val getAllMusic: LiveData<List<MusicList>>
    private var repository: MusicRepository


init {
    val musicDao = MusicDatabase.getDatabase(application).musicListDao()
     repository = MusicRepository(musicDao)
    getAllMusic = repository.getAllMusic
}

     fun addMusic(music:MusicList){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMusic(music)

        }
    }
}