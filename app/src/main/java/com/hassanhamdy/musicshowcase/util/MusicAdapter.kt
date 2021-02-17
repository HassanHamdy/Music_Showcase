package com.hassanhamdy.musicshowcase.util

import MusicModel
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hassanhamdy.musicshowcase.databinding.MusicItemListBinding

class MusicAdapter(
    private val musics: ArrayList<MusicModel>,
    private val onItemClicked: (MusicModel) -> Unit
) :
    RecyclerView.Adapter<MusicAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return musics.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicAdapter.ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: MusicAdapter.ViewHolder, position: Int) {
        holder.bind(musics[position], onItemClicked)
    }

    class ViewHolder(val binding: MusicItemListBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                /*
                * Remember to pass false to attachParent since the RecyclerView will take care of attaching it for us.
                * */
                val binding = MusicItemListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(
                    binding, parent.context
                )
            }
        }


        fun bind(musicItem: MusicModel, onItemClicked: (MusicModel) -> Unit) {
            binding.ivMusicImage.setImageBitmap(musicItem.bitmapImage)
            binding.tvMusicName.text = musicItem.title
            binding.tvArtistName.text = musicItem.mainArtist.name
            binding.tvMusicType.text = musicItem.type

            binding.root.setOnClickListener {
                onItemClicked(musicItem)
            }
        }

    }
}