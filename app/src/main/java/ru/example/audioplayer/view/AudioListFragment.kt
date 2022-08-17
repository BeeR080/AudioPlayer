package ru.example.audioplayer.view

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.example.audioplayer.R
import ru.example.audioplayer.databinding.FragmentAudioListBinding


class AudioListFragment : Fragment() {

    private lateinit var binding :  FragmentAudioListBinding
    private  var audioPlayer :   MediaPlayer? = null
    private var currentMusic = mutableListOf(R.raw.dota2rampa_88c162695170679)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAudioListBinding.inflate(layoutInflater)

        audioPlayer = MediaPlayer.create(requireContext(), currentMusic[0])

        controlAudioPlayer()




        return binding.root
    }

    fun controlAudioPlayer(){

        binding.playerPlay.apply{
            setOnClickListener {
                if(!audioPlayer!!.isPlaying){
                   audioPlayer?.start()
                    setBackgroundResource(R.drawable.ic_player_pause)


            }else{
                audioPlayer?.pause()
                setBackgroundResource(R.drawable.ic_player_play)
            }

        }
        }
    }
}





