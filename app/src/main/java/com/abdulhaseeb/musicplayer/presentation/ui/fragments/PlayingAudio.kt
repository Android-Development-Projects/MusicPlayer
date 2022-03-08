package com.abdulhaseeb.musicplayer.presentation.ui.fragments

import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.abdulhaseeb.musicplayer.R
import com.abdulhaseeb.musicplayer.databinding.FragmentPlayingAudioBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


class PlayingAudio : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentPlayingAudioBinding
    private val args: PlayingAudioArgs by navArgs()
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayingAudioBinding.inflate(inflater, container, false)
        val view = binding.root
        lifecycleScope.launch {
            val audioFileDetails = args.audioFile
            audioFileDetails?.let {
                val uri = it.uri
                val audioName = it.name
                val artistName = it.artist
                val folderName = it.album
                val duration = it.duration
                val totalDuration = durationLongToCalenderTime(duration)
                /*val formattedDuration: String = String.format(
                    "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(duration) % TimeUnit.HOURS.toMinutes(1),
                    TimeUnit.MILLISECONDS.toSeconds(duration) % TimeUnit.MINUTES.toSeconds(1)
                )*/


                binding.apply {
                    mediaPlayer = MediaPlayer.create(requireContext(), uri)
                    tvPlayingSongName.text = audioName
                    tvPlayingArtistName.text = artistName
                    tvPlayingFolderName.text = folderName
                    tvPlayingSongTotalTime.text = totalDuration

                    mediaPlayer.start()

                    imageViewPlayPauseButton.setImageResource(R.drawable.ic_pause)
                    imageViewPlayPauseButton.setOnClickListener {
                        playAudio(uri)
                    }
                    seekBarPlayingSong.setOnSeekBarChangeListener(object :
                        SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(
                            seekBar: SeekBar?,
                            progress: Int,
                            fromUser: Boolean
                        ) {
                            mediaPlayer.seekTo(progress)
                        }

                        override fun onStartTrackingTouch(seekBar: SeekBar?) {
                        }

                        override fun onStopTrackingTouch(seekBar: SeekBar?) {
                        }
                    })
                }
            }
            while (mediaPlayer.isPlaying){
                delay(50)
                val currentPosition = durationLongToCalenderTime(mediaPlayer.currentPosition.toLong())
                binding.tvCurrentPositionTime.text = currentPosition
                            }
        }
        return view
    }


    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
    }

    private fun durationLongToCalenderTime(duration: Long): String {
        val calendarTime = Calendar.getInstance()
        val date = Date(duration)
        calendarTime.time = date
        val s = calendarTime.get(Calendar.SECOND)
        val m = calendarTime.get(Calendar.MINUTE)

        return "$m:$s"
    }

    private fun playAudio(uri: Uri) {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
            binding.imageViewPlayPauseButton.setImageResource(R.drawable.ic_pause)
        } else if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            binding.imageViewPlayPauseButton.setImageResource(R.drawable.ic_play)
        }
        mediaPlayer.setOnCompletionListener {
            whenCompleted(uri)
        }
    }

    private fun whenCompleted(uri: Uri) {
/*
        mediaPlayer.stop()
*/
        binding.imageViewPlayPauseButton.setImageResource(R.drawable.ic_play)
    }
}
