package com.abdulhaseeb.musicplayer.presentation.ui.fragments

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.abdulhaseeb.musicplayer.R
import com.abdulhaseeb.musicplayer.databinding.FragmentLibraryBinding
import com.abdulhaseeb.musicplayer.presentation.ui.RequestPermissionClass
import android.Manifest.permission.*
import android.content.pm.PackageManager


class LibraryFragment : Fragment(R.layout.fragment_library) {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            checkIfUserRequestedDontAskAgain()
        }
    }
    private lateinit var binding: FragmentLibraryBinding
    private val requestPermissionObj = RequestPermissionClass()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            requestPermissionObj.requestPermission(
                requestPermissionLauncher,
                requireActivity(),
                requireContext()
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_library, container, false)
    }

    private fun checkIfUserRequestedDontAskAgain() {
        val rationalFlagRead =
            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (!rationalFlagRead) {
            //  binding.btnRequestPermission.isVisible = true
        }

    }

}