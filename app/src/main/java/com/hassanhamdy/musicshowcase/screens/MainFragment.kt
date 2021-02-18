package com.hassanhamdy.musicshowcase.screens

import MusicModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hassanhamdy.musicshowcase.R
import com.hassanhamdy.musicshowcase.databinding.FragmentMainBinding
import com.hassanhamdy.musicshowcase.util.MusicAdapter
import com.hassanhamdy.musicshowcase.util.ShowViewsTypes
import com.hassanhamdy.musicshowcase.viewmodel.MusicViewModel


class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MusicViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(MusicViewModel::class.java)

        binding.rcvMusics.layoutManager = LinearLayoutManager(context!!)

        //handle button click listener
        binding.fabSearch.setOnClickListener {
            val searchKeyword = binding.etSearchField.text.toString()
            if (searchKeyword.isEmpty()) {
                binding.etSearchField.error = "Please Enter a search keyword"
            } else {
                handleViewsSwitch(ShowViewsTypes.LOADING)
                viewModel.getMusicsApi(searchKeyword)
            }
        }


        viewModel.musics.observe(viewLifecycleOwner, Observer { musicList ->
            if (musicList.isNotEmpty()) {
                binding.rcvMusics.adapter = MusicAdapter(musicList, ::onMusicClick)
                handleViewsSwitch(ShowViewsTypes.RECYCLERVIEW)
            } else {
                binding.tvNoMusic.text = "No Music Found"
                handleViewsSwitch(ShowViewsTypes.TEXT)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { errorTxt ->
            binding.tvNoMusic.text = errorTxt
            handleViewsSwitch(ShowViewsTypes.TEXT)
        })
    }

    private fun onMusicClick(music: MusicModel) {
        viewModel.onMusicItemClick(music)
        val myfragment = DetailFragment()
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.main_fragment, myfragment, "detail")
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun handleViewsSwitch(viewsTypes: ShowViewsTypes) {
        when (viewsTypes) {
            ShowViewsTypes.RECYCLERVIEW -> {
                binding.progressMusicList.visibility = View.GONE
                binding.tvNoMusic.visibility = View.GONE
                binding.rcvMusics.visibility = View.VISIBLE
            }
            ShowViewsTypes.TEXT -> {
                binding.progressMusicList.visibility = View.GONE
                binding.tvNoMusic.visibility = View.VISIBLE
                binding.rcvMusics.visibility = View.INVISIBLE
            }
            ShowViewsTypes.LOADING -> {
                binding.progressMusicList.visibility = View.VISIBLE
                binding.tvNoMusic.visibility = View.GONE
                binding.rcvMusics.visibility = View.INVISIBLE
            }
        }
    }
}