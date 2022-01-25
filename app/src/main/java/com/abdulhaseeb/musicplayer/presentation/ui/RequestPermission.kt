package com.abdulhaseeb.musicplayer.presentation.ui

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.abdulhaseeb.musicplayer.utils.Constants.TAG

class RequestPermissionClass {
    fun requestPermission(
        requestPermissionLauncher: ActivityResultLauncher<String>,
        requireActivity: FragmentActivity,
        requireContext: Context
    ) {
        when {
            ActivityCompat.checkSelfPermission(
                requireContext,
                READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i(TAG, "in check self permission")
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity,
                READ_EXTERNAL_STORAGE
            ) -> {
                Log.i(TAG, "in show request permission rationale")
                AlertDialog.Builder(requireContext).apply {
                    setTitle("Request Permission")
                    setMessage("You have to provide READ_EXTERNAL_STORAGE permission!")
                    setCancelable(false)
                    setPositiveButton("Grant Permission", DialogInterface.OnClickListener { _, _ ->
                        requestPermissionLauncher.launch(READ_EXTERNAL_STORAGE)
                    })
                    setNegativeButton("No", DialogInterface.OnClickListener { _, _ ->
                        requestPermissionLauncher.launch(READ_EXTERNAL_STORAGE)
                    })
                    show()
                }
            }
            else -> {
                //PERMISSION IS NOT GRANTED YET
                requestPermissionLauncher.launch(READ_EXTERNAL_STORAGE)
            }
        }
    }
}