package com.hassanhamdy.musicshowcase.screens

import MusicModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hassanhamdy.musicshowcase.R
import com.hassanhamdy.musicshowcase.databinding.FragmentDetailBinding
import com.hassanhamdy.musicshowcase.viewmodel.MusicViewModel

class DetailFragment : Fragment() {
    lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: MusicViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MusicViewModel::class.java)

        viewModel.musicItem.observe(viewLifecycleOwner, Observer { musicItem ->
            Log.d("HASSAN", musicItem.title)
            assignViewsValues(musicItem)
        })
    }

    private fun assignViewsValues(music: MusicModel) {
        if (music.bitmapImage != null)
            binding.ivItemMusic.setImageBitmap(music.bitmapImage)
        else
            binding.ivItemMusic.setImageDrawable(
                ContextCompat.getDrawable(
                    context!!,
                    R.drawable.ic_broken_image
                )
            )
        binding.tvItemMusicName.text = music.title
        binding.tvItemMusicArtist.text = music.mainArtist.name
        binding.tvItemMusicType.text = music.type
    }

}