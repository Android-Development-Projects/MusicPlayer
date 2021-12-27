package com.abdulhaseeb.musicplayer.presentation.ui.fragments

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.abdulhaseeb.musicplayer.R
import com.abdulhaseeb.musicplayer.databinding.FragmentMusicHomeBinding
import com.abdulhaseeb.musicplayer.presentation.ui.RequestPermissionClass

class MusicHomeFragment : Fragment(R.layout.fragment_music_home) {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            checkIfUserRequestedDontAskAgain()
        }

    }
    private val requestPermissionObj = RequestPermissionClass()
    private lateinit var binding: FragmentMusicHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissionObj.requestPermission(
            requestPermissionLauncher,
            requireActivity(),
            requireContext()
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMusicHomeBinding.bind(view)
        binding.btnRequestPermission.visibility = View.INVISIBLE

        binding.btnRequestPermission.setOnClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", activity?.packageName, null)
            intent.data = uri
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        if (ActivityCompat.checkSelfPermission(requireContext(), READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ){
            binding.btnRequestPermission.visibility = View.INVISIBLE
        } 
    }


    private fun checkIfUserRequestedDontAskAgain() {
        val rationalFlagRead =
            shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)
            if(!rationalFlagRead){
                binding.btnRequestPermission.isVisible = true
            }

    }
}