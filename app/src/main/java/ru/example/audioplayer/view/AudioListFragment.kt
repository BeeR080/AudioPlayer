package ru.example.audioplayer.view



import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.example.audioplayer.R
import ru.example.audioplayer.adapter.AdapterListMusicBottomSheet
import ru.example.audioplayer.data.MusicList
import ru.example.audioplayer.data.MusicViewModel
import ru.example.audioplayer.databinding.FragmentAudioListBinding
import ru.example.audioplayer.service.AudioService
import ru.example.audioplayer.utils.formatTime



class AudioListFragment : Fragment() {

    private lateinit var binding : FragmentAudioListBinding
    lateinit var vmMusic: MusicViewModel
    private lateinit var audioPlayer: MediaPlayer

    private var currentMusic = mutableListOf(
        R.raw.skillet_herro,
        R.raw.dead_blonde_malchik_na_devyatke,
    )


    private var recieverMusic = AudioService()
    private var currentTrack = 0

    private var getContent =registerForActivityResult(ActivityResultContracts.GetContent()){uri: Uri?->
        vmMusic.addMusic(MusicList(10,uri.toString(),"TestMusic2123"))
    }
    private val adapter = AdapterListMusicBottomSheet()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAudioListBinding.inflate(layoutInflater)
        vmMusic = ViewModelProvider(this).get(MusicViewModel::class.java)
        vmMusic.getAllMusic.observe(viewLifecycleOwner, Observer{music->
            adapter.setData(music)
        })
         audioPlayer = MediaPlayer()

        controlAudioPlayer()
        //controlButtonsPrevandNext()
        addMusic()


        binding.audioSeekbar.apply {
            max = audioPlayer.duration
            progress = 0

        }

        //Music List Adapter
        val rvListMusicBsh = binding.playerRecyclerMusiclist
        rvListMusicBsh.adapter = adapter
        rvListMusicBsh.layoutManager = LinearLayoutManager(requireContext())





        return binding.root

    }
    fun selectMusic(){
        getContent.launch(TYPE_MUSIC)
    }

    fun addMusic(){
        binding.playerAddMusic.setOnClickListener {
            selectMusic()
        }
    }




    fun controlAudioPlayer(){
       val musicUri = Uri.parse(" content://com.android.providers.downloads.documents/document/msf%3A25")
         audioPlayer.setDataSource(requireContext(),musicUri)

        audioPlayer = MediaPlayer.create(requireContext(),
           musicUri!!)

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
        val intentNext = Intent(requireContext(),AudioListFragment::class.java)
            .setAction(ACTION_NEXT)
        val pendingIntentNext = PendingIntent.getBroadcast(
            requireContext(),
            0,
            intentNext,
            PendingIntent.FLAG_UPDATE_CURRENT)
        val intentFilter = IntentFilter("TRACKS")
        activity?.registerReceiver(recieverMusic,intentFilter)
        val notificationManager =
            requireActivity()
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

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
            .addAction(R.drawable.ic_player_prev,"Prev",pendingIntentNext)
            .addAction(R.drawable.ic_player_pause,"Play",pendingIntentNext)
            .addAction(R.drawable.ic_player_next,"Next",pendingIntentNext)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                .setShowActionsInCompactView(0, 1, 2))
            .setSmallIcon(R.drawable.ic_music)
            .build()

        notificationManager.notify(1,notification)

    }




    fun controlButtonsPrevandNext(){
       /* audioPlayer = MediaPlayer.create(requireContext(),
            currentMusic[currentTrack])*/
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

        private const val ACTION_NEXT = "actionnext"
        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "MusicChannel"
        private const val TYPE_MUSIC = "audio/*"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.unregisterReceiver(recieverMusic)
    }





}





