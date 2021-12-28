package com.abdulhaseeb.musicplayer.viewModel

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdulhaseeb.musicplayer.repository.AudioRepo
import com.abdulhaseeb.musicplayer.repository.data.AudioData

@RequiresApi(Build.VERSION_CODES.Q)
class MainViewModel(
    val context: Application
) : AndroidViewModel(context) {

    val _getAllAudioList = MutableLiveData<List<AudioData>>()


    init {
         val audioRepo: AudioRepo = AudioRepo(context)
        _getAllAudioList.value = audioRepo.getAudioList()
    }

}