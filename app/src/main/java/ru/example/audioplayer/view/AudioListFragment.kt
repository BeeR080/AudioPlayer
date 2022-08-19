package ru.example.audioplayer.view


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.icu.util.TimeUnit
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.app.NotificationCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.example.audioplayer.R
import ru.example.audioplayer.adapter.AdapterListMusicBottomSheet
import ru.example.audioplayer.data.MusicList
import ru.example.audioplayer.databinding.FragmentAudioListBinding
import ru.example.audioplayer.utils.formatTime
import kotlin.concurrent.thread


class AudioListFragment : Fragment() {

    private lateinit var binding : FragmentAudioListBinding
    private lateinit var audioPlayer : MediaPlayer
    private var currentMusic = mutableListOf(
        R.raw.skillet_herro,
        R.raw.dead_blonde_malchik_na_devyatke
    )
    private var currentTrack = 0
    val adapter = AdapterListMusicBottomSheet()
    private var testList:List<MusicList> = listOf(
        MusicList("Skillet","Hero"),
        MusicList("DjAbra","SuperReutov"),
        MusicList("Arctic Monkeys","Jalulambab"))


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAudioListBinding.inflate(layoutInflater)

        controlAudioPlayer()
        controlButtonsPrevandNext()



        binding.audioSeekbar.apply {
            max = audioPlayer.duration
            progress = 0

        }

        //Music List Adapter
        val rvListMusicBsh = binding.playerRecyclerMusiclist
        rvListMusicBsh.adapter = adapter
        rvListMusicBsh.layoutManager = LinearLayoutManager(requireContext())

        adapter.setData(testList)


        return binding.root
    }






    fun controlAudioPlayer(){
        audioPlayer = MediaPlayer.create(requireContext(),
            currentMusic[currentTrack])
        binding.playerPlay.apply{
            setOnClickListener {
                if(!audioPlayer.isPlaying){
                   audioPlayer.start()
                    binding.playerMusicName.setText(currentMusic.toString())
                    val musicDuration = audioPlayer.duration
                    binding.playerMaxTime.setText(formatTime(musicDuration))
                    setBackgroundResource(R.drawable.ic_player_pause)
                    showMusicNotification()


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
                     binding.playerCurrentTime.setText(formatTime(audioPlayer.currentPosition))
                     binding.audioSeekbar.progress = audioPlayer.currentPosition
                     delay(100)

                audioPlayer.setOnCompletionListener {
                    binding.playerPlay.setBackgroundResource(R.drawable.ic_player_play)

                }
                 }
            }

        }





    }




    fun showMusicNotification(){
        val notificationManager =
            requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
                notificationManager.createNotificationChannel(notificationChannel)
        }
        val notification = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setContentTitle("Skillet")
            .setContentText("Hero")
            .setSmallIcon(R.drawable.ic_music)
            .build()
        notificationManager.notify(1,notification)

    }
    fun controlButtonsPrevandNext(){
        audioPlayer = MediaPlayer.create(requireContext(),
            currentMusic[currentTrack])
        binding.playerPrev.setOnClickListener {
            audioPlayer.stop()
            currentTrack--
            controlAudioPlayer()
            binding.playerMusicName.setText(currentMusic.toString())
            val musicDuration = audioPlayer.duration
            binding.playerMaxTime.setText(formatTime(musicDuration))
            binding.playerPlay.setBackgroundResource(R.drawable.ic_player_pause)
            audioPlayer.start()
            Log.d("music", "$currentTrack")

        }
        binding.playerNext.setOnClickListener {
            audioPlayer.stop()
            currentTrack++
            controlAudioPlayer()
            val musicDuration = audioPlayer.duration
            binding.playerMaxTime.setText(formatTime(musicDuration))
            binding.playerPlay.setBackgroundResource(R.drawable.ic_player_pause)
            audioPlayer.start()
            Log.d("music", "$currentTrack")


        }
    }




    companion object{
        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "MusicChannel"
    }





}





