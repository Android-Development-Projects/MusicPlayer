package com.abdulhaseeb.musicplayer.repository.data

import android.net.Uri

data class AudioData(
    val uri: Uri,
    val bucketName: String,
    val bucketId: Long,
    val title: String,
    val name: String,
    val album: String,
    val artist: String,
    val duration: Long,
    val date: String,
    val size: Long,
)