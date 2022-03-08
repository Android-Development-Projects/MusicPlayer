package com.abdulhaseeb.musicplayer.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulhaseeb.musicplayer.databinding.ItemSongsLayoutBinding
import com.abdulhaseeb.musicplayer.repository.data.AudioData
import com.abdulhaseeb.musicplayer.utils.Constants.TAG

class AudioListAdapter(
    private val audioList: List<AudioData>,
    val itemOnClick : (AudioData) -> Unit
) : RecyclerView.Adapter<AudioListAdapter.ViewHolder>() {

    class ViewHolder(
        val binding: ItemSongsLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemSongsLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = audioList[position]
        holder.binding.apply {
            tvSongName.text = currentItem.name
            tvArtistName.text = currentItem.artist
            tvAlbumName.text = currentItem.album
            Log.i(TAG, "in adapter $tvSongName")
        }
        holder.itemView.setOnClickListener {
            itemOnClick(currentItem)
        }
    }

    override fun getItemCount(): Int = audioList.size

}