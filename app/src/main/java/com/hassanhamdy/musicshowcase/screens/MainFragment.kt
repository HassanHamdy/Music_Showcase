package com.hassanhamdy.musicshowcase.screens

import MusicModel
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hassanhamdy.musicshowcase.NetworkBase
import com.hassanhamdy.musicshowcase.databinding.FragmentMainBinding
import com.hassanhamdy.musicshowcase.util.MusicAdapter
import com.hassanhamdy.musicshowcase.viewmodel.MusicViewModel
import java.net.URL


class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MusicViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        val musics = ArrayList<MusicModel>()
        musics.add(MusicModel())
        musics.add(MusicModel())
        musics.add(MusicModel())
        musics.add(MusicModel())
        musics.add(MusicModel())
        musics.add(MusicModel())
        musics.add(MusicModel())
        musics.add(MusicModel())
        musics.add(MusicModel())
        musics.add(MusicModel())
        musics.add(MusicModel())

        binding.rcvMusics.layoutManager = LinearLayoutManager(context!!)
        binding.rcvMusics.adapter = MusicAdapter(musics, context!!){
                position -> print("HELO HASSAN $position")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(MusicViewModel::class.java)

        /*
        //Launch the data receiver fragment
            val myfragment = ReceiveFragment()
            val fragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.framefragmenthome, myfragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
         */
    }
}