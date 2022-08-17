package ru.example.audioplayer.view

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.example.audioplayer.R
import ru.example.audioplayer.databinding.FragmentAudioListBinding


class AudioListFragment : Fragment() {

    private lateinit var binding : FragmentAudioListBinding
    private lateinit var audioPlayer : MediaPlayer
    private var currentMusic = mutableListOf(R.raw.skillet_herro)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        audioPlayer = MediaPlayer.create(requireContext(), currentMusic[0])
        binding = FragmentAudioListBinding.inflate(layoutInflater)

        controlAudioPlayer()
        binding.audioSeekbar.apply {
            max = audioPlayer.duration
            progress = 0

        }


        return binding.root
    }



    fun controlAudioPlayer(){
        binding.playerPlay.apply{
            setOnClickListener {
                if(!audioPlayer.isPlaying){
                   audioPlayer.start()
                    setBackgroundResource(R.drawable.ic_player_pause)

            }else{
                audioPlayer.pause()
                setBackgroundResource(R.drawable.ic_player_play)
            }

        }
            binding.audioSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(p0: SeekBar?, position: Int, changed: Boolean) {
                    if(changed){
                        audioPlayer.seekTo(position)
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }

            })
            lifecycleScope.launch {
                 while (true) {
                     binding.audioSeekbar.progress = audioPlayer.currentPosition
                     delay(100)

                audioPlayer.setOnCompletionListener {
                    binding.playerPlay.setBackgroundResource(R.drawable.ic_player_play)

                }
                 }
            }

        }




    }





}





