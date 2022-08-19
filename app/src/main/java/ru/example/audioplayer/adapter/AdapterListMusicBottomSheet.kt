package ru.example.audioplayer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_audio_list.view.*
import kotlinx.android.synthetic.main.music_list.view.*
import kotlinx.coroutines.NonDisposableHandle
import kotlinx.coroutines.NonDisposableHandle.parent
import ru.example.audioplayer.R
import ru.example.audioplayer.data.MusicList

class AdapterListMusicBottomSheet:
    RecyclerView.Adapter<AdapterListMusicBottomSheet.MusicViewHolder>() {


    var musicList = emptyList<MusicList>()

   class MusicViewHolder(itemv:View):RecyclerView.ViewHolder(itemv){

   }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.music_list,
               parent,
                false)
        return MusicViewHolder(view)

    }
    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val currentitem = musicList[position]

        holder.itemView.musiclist_singername.setText(currentitem.singerName)
        holder.itemView.musiclist_trackname.setText(currentitem.signerTrack)
    }

    override fun getItemCount(): Int {
        return musicList.size
        notifyDataSetChanged()
    }

    fun setData(listMusic: List<MusicList>){
        musicList = listMusic

    }



}
