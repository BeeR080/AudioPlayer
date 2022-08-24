package ru.example.audioplayer.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ru.example.audioplayer.databinding.FragmentAudioListBinding
import ru.example.audioplayer.view.AudioListFragment


class AudioService : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context!!.sendBroadcast(Intent("TRACKS")
            .putExtra("TRACKS",intent!!.action))


    }


}