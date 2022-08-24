package ru.example.audioplayer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MusicList::class], version = 1, exportSchema = false)
abstract class MusicDatabase: RoomDatabase() {

    abstract fun  musicListDao(): MusicDao

    companion object{
        @Volatile
        private var INSTANCE:MusicDatabase? = null

        fun getDatabase(context: Context): MusicDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MusicDatabase::class.java,
                    "music_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}