package com.hassanhamdy.musicshowcase.viewmodel

import Cover
import MainArtist
import MusicModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hassanhamdy.musicshowcase.NetworkBase
import com.hassanhamdy.musicshowcase.util.SharedPref
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

class MusicViewModel : ViewModel() {

    private val _musics = MutableLiveData<ArrayList<MusicModel>>()
    val musics: LiveData<ArrayList<MusicModel>>
        get() = _musics

    private val _musicItem = MutableLiveData<MusicModel>()
    val musicItem: LiveData<MusicModel>
        get() = _musicItem

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun onMusicItemClick(music: MusicModel) {
        _musicItem.value = music
    }

    fun getMusicsApi(keyword: String) {
        val accessToken = SharedPref.instance.getToken()
        if (accessToken != "") {
            val musicListResponse = getMusicProcesses(accessToken = accessToken, keyword = keyword)
            if (musicListResponse.first != 404) {
                parseMusicsInBackground(musicListResponse.second, ::onParseResponseJsonEnd)
            } else {
                _error.value = musicListResponse.second
            }
        } else {
            val tokenResponse = NetworkBase().getAccessToken().get()
            if (tokenResponse.first != 404) {
                parseTokenJson(tokenResponse.second)
                getMusicsApi(keyword)
            } else {
                _error.value = tokenResponse.second
            }
        }
    }

    private fun getMusicProcesses(accessToken: String, keyword: String): Pair<Int, String> {
        val millis = SharedPref.instance.getTokenValidTime()
        val expireCalender = getCalenderFromMilliSecond(millis.toLong())
        val currentCalender = Calendar.getInstance()
        return if (currentCalender <= expireCalender)
            NetworkBase().getMusics(token = accessToken, query = keyword).get()
        else {
            SharedPref.instance.putAccessToken("")
            getMusicsApi(keyword)
            Pair(404, "token expired get it again")
        }
    }

    private fun parseTokenJson(jsonStr: String) {
        val jsonObject: JSONObject = JSONObject(jsonStr)
        if (jsonObject.has("accessToken") && jsonObject.has("expiresIn")) {
            SharedPref.instance.putAccessToken(jsonObject.getString("accessToken"))
            val expireTimeInSeconds = jsonObject.getString("expiresIn").toInt()
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.SECOND, expireTimeInSeconds)
            SharedPref.instance.putTokenTime(calendar.timeInMillis.toString())

        }
    }

    private fun getCalenderFromMilliSecond(millis: Long): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis
        return calendar
    }

    private fun parseMusicsJson(jsonStr: String): ArrayList<MusicModel> {
        val musics = ArrayList<MusicModel>()
        val jsonArray = JSONArray(jsonStr)
        for (i in 0 until jsonArray.length()) {
            val music = jsonArray.getJSONObject(i)
            val coverJsonObject = music.getJSONObject("cover")
            val artistJsonObject = music.getJSONObject("mainArtist")
            musics.add(
                MusicModel(
                    type = music.getString("type"),
                    title = music.getString("title"),
                    publishingDate = music.getString("publishingDate"),
                    duration = music.getInt("duration"),
                    mainArtist = MainArtist(name = artistJsonObject.getString("name")),
                    cover = Cover(
                        tiny = coverJsonObject.getString("tiny"),
                        small = coverJsonObject.getString("small"),
                        medium = coverJsonObject.getString("medium"),
                        large = coverJsonObject.getString("large"),
                        default = coverJsonObject.getString("default"),
                        template = coverJsonObject.getString("template")
                    ),
                    bitmapImage = NetworkBase().getNetworkImage(coverJsonObject.getString("medium"))
                        .get()
                )
            )
        }
        return musics
    }

    private fun parseMusicsInBackground(
        json: String,
        listener: (ArrayList<MusicModel>) -> Unit
    ) {
        Executors.newSingleThreadExecutor().execute {
            listener(parseMusicsJson(json))
        }
    }

    private fun onParseResponseJsonEnd(musics: ArrayList<MusicModel>) {
        _musics.postValue(musics)
    }
}