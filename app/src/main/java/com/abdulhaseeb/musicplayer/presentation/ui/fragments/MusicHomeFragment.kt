package com.abdulhaseeb.musicplayer.presentation.ui.fragments

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdulhaseeb.musicplayer.R
import com.abdulhaseeb.musicplayer.adapter.AudioListAdapter
import com.abdulhaseeb.musicplayer.databinding.FragmentMusicHomeBinding
import com.abdulhaseeb.musicplayer.presentation.ui.RequestPermissionClass
import com.abdulhaseeb.musicplayer.repository.data.AudioData
import com.abdulhaseeb.musicplayer.viewModel.MainViewModel

class MusicHomeFragment : Fragment(R.layout.fragment_music_home) {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            checkIfUserRequestedDontAskAgain()
        }
    }
    private lateinit var viewModel : MainViewModel
    private val requestPermissionObj = RequestPermissionClass()
    private lateinit var binding: FragmentMusicHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context : Application
        binding = FragmentMusicHomeBinding.bind(view)
        binding.btnRequestPermission.visibility = View.INVISIBLE
        binding.btnRequestPermission.setOnClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", activity?.packageName, null)
            intent.data = uri
            startActivity(intent)
        }

        if(ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )== PackageManager.PERMISSION_GRANTED
        ){
            viewModel = ViewModelProvider(this)[MainViewModel::class.java]
            val audioList = viewModel._getAllAudioList
            observeData(audioList)
        }else{
            requestPermissionObj.requestPermission(requestPermissionLauncher,requireActivity(),requireContext())
        }


    }

    override fun onResume() {
        super.onResume()
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ){
            binding.btnRequestPermission.visibility = View.INVISIBLE
            viewModel = ViewModelProvider(this)[MainViewModel::class.java]
            observeData(viewModel._getAllAudioList)
        }
    }


    private fun checkIfUserRequestedDontAskAgain() {
        val rationalFlagRead =
            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
            if(!rationalFlagRead){
                binding.btnRequestPermission.isVisible = true
            }

    }

    private fun observeData(audioList: MutableLiveData<List<AudioData>>) {
        audioList.observe(viewLifecycleOwner, { audio_List ->
            val audioAdapter = AudioListAdapter(audio_List)
            binding.recyclerViewSongs.apply {
                adapter = audioAdapter
                layoutManager = LinearLayoutManager(requireContext())
                audioAdapter.setOnItemClickListener(object : AudioListAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        findNavController().navigate(R.id.action_musicHomeFragment_to_playingAudio)
                    }
                })
            }




        })
    }
}