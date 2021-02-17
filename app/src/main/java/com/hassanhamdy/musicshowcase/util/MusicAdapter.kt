package com.hassanhamdy.musicshowcase.util

import MusicModel
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hassanhamdy.musicshowcase.NetworkBase
import com.hassanhamdy.musicshowcase.R

class MusicAdapter(
    private val musics: ArrayList<MusicModel>,
    private val context: Context,
    private val listener: (position: Int) -> Unit
) :
    RecyclerView.Adapter<MusicAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return musics.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.music_item_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MusicAdapter.ViewHolder, position: Int) {
        holder.musicImage.setImageBitmap(
            NetworkBase().getNetworkImage("https://searchengineland.com/figz/wp-content/seloads/2015/09/Google-visual-update-September-2015-800x450.png")
                .get()
        )
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val musicImage: ImageView = itemView.findViewById(R.id.iv_music_image)
        val musicName: TextView = itemView.findViewById(R.id.tv_music_name)
        val artistName: TextView = itemView.findViewById(R.id.tv_artist_name)
        val musicType: TextView = itemView.findViewById(R.id.tv_music_type)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.invoke(adapterPosition)
        }

    }
}