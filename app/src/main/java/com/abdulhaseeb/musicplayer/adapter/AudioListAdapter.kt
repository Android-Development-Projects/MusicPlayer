package com.abdulhaseeb.musicplayer.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulhaseeb.musicplayer.databinding.ItemSongsLayoutBinding
import com.abdulhaseeb.musicplayer.repository.data.AudioData
import com.abdulhaseeb.musicplayer.utils.Constants.TAG

class AudioListAdapter(
    private val audioList : List<AudioData>
):RecyclerView.Adapter<AudioListAdapter.ViewHolder>(){

    private lateinit var itemClickListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        itemClickListener = listener
    }

    class ViewHolder(
        val binding: ItemSongsLayoutBinding,
        listener: onItemClickListener
    ) :RecyclerView.ViewHolder(binding.root){
        init {
                binding.root.setOnClickListener{
                    listener.onItemClick(adapterPosition)
                }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
            ItemSongsLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClickListener
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = audioList[position]
        holder.binding.apply {
            tvSongName.text = currentItem.name
            tvArtistName.text = currentItem.artist
            tvAlbumName.text = currentItem.album
            Log.i(TAG, "in adapter $tvSongName")
        }
    }

    override fun getItemCount(): Int = audioList.size

}