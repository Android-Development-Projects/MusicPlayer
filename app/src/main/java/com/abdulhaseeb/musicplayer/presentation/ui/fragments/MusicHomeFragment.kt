package com.abdulhaseeb.musicplayer.presentation.ui.fragments

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.abdulhaseeb.musicplayer.utils.Constants.TAG
import java.util.jar.Manifest


class MusicHomeFragment : Fragment() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted ->
        if(isGranted){
            checkIfUserRequestedDontAskAgain()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestPermission()
    }

    private fun requestPermission() {
        when{
            ActivityCompat.checkSelfPermission(
                requireContext(),
                READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED ->{
                Log.i(TAG, "in check self permission")
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                READ_EXTERNAL_STORAGE
            ) -> {
                Log.i(TAG, "in Rationale")
                AlertDialog.Builder(requireContext()).apply {
                    setTitle("Request Permission")
                    setMessage("You have to provide READ_EXTERNAL_STORAGE permission!")
                    setPositiveButton("Grant Permission", DialogInterface.OnClickListener{ _, _ ->
                        requestPermissionLauncher.launch(READ_EXTERNAL_STORAGE)
                    })
                    setNegativeButton("No", DialogInterface.OnClickListener{_,_->
                        requestPermissionLauncher.launch(READ_EXTERNAL_STORAGE)
                    })
                    show()
                }
            } else ->{
                //Permission has not been asked yet
                requestPermissionLauncher.launch(READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun checkIfUserRequestedDontAskAgain(){
        val rationalFlagRead =
            shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)

    }
}