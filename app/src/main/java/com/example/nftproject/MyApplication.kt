package com.example.nftproject

import android.app.Application
import com.example.nftproject.network.util.PreferenceUtil

class MyApplication: Application() {

    companion object {
        lateinit var prefUtil: PreferenceUtil
    }
    override fun onCreate() {
        prefUtil = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}