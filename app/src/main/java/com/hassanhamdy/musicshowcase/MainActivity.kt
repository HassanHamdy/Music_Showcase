package com.hassanhamdy.musicshowcase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hassanhamdy.musicshowcase.screens.DetailFragment
import com.hassanhamdy.musicshowcase.screens.MainFragment
import com.hassanhamdy.musicshowcase.util.SharedPref

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SharedPref.instance.initSharedPref(applicationContext)

        val myfragment = MainFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.main_fragment, myfragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}