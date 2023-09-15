package com.example.nftproject

import android.app.Application
import com.example.nftproject.network.util.PreferenceUtil

class MyApplication: Application() {

    companion object {
        lateinit var prefUtil: PreferenceUtil
        var genrePartHashMap = HashMap<String, String>()
        var agePartHashMap = HashMap<String, String>()
    }
    override fun onCreate() {
        prefUtil = PreferenceUtil(applicationContext)

        genrePartHashMap[getString(R.string.ACTION)] = "ACTION"
        genrePartHashMap[getString(R.string.ADVENTURE)] = "ADVENTURE"
        genrePartHashMap[getString(R.string.FANTASY)] = "FANTASY"
        genrePartHashMap[getString(R.string.SCIENCEFICTION)] = "SCIENCEFICTION"
        genrePartHashMap[getString(R.string.NOIR)] = "NOIR"
        genrePartHashMap[getString(R.string.HORROR)] = "HORROR"
        genrePartHashMap[getString(R.string.WAR)] = "WAR"
        genrePartHashMap[getString(R.string.THRILLER)] = "THRILLER"
        genrePartHashMap[getString(R.string.MYSTERY)] = "MYSTERY"
        genrePartHashMap[getString(R.string.ROMANCE)] = "ROMANCE"
        genrePartHashMap[getString(R.string.COMEDY)] = "COMEDY"
        genrePartHashMap[getString(R.string.DRAMA)] = "DRAMA"
        genrePartHashMap[getString(R.string.ANIMATION)] = "ANIMATION"
        genrePartHashMap[getString(R.string.ART)] = "ART"
        genrePartHashMap[getString(R.string.MUSICAL)] = "MUSICAL"
        genrePartHashMap[getString(R.string.DOCUMENTARY)] = "DOCUMENTARY"
        genrePartHashMap[getString(R.string.MUMBLECORE)] = "MUMBLECORE"
        genrePartHashMap[getString(R.string.EDUCATION)] = "EDUCATION"
        genrePartHashMap[getString(R.string.NARRATIVE)] = "NARRATIVE"
        genrePartHashMap[getString(R.string.EXPERIMENT)] = "EXPERIMENT"
        genrePartHashMap[getString(R.string.EXPLOITATION)] = "EXPLOITATION"
        genrePartHashMap[getString(R.string.DISASTER)] = "DISASTER"
        genrePartHashMap[getString(R.string.CONCERT)] = "CONCERT"
        genrePartHashMap[getString(R.string.CRIME)] = "CRIME"
        genrePartHashMap[getString(R.string.SUPERHERO)] = "SUPERHERO"
        genrePartHashMap[getString(R.string.SPORT)] = "SPORT"

        agePartHashMap[getString(R.string.PG12)] = "PG12"
        agePartHashMap[getString(R.string.PG15)] = "PG15"
        agePartHashMap[getString(R.string.PG18)] = "PG18"
        agePartHashMap[getString(R.string.G_RATED)] = "G_RATED"

        super.onCreate()
    }
}