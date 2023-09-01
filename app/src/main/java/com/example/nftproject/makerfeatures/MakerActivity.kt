package com.example.nftproject.makerfeatures

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.nftproject.R
import com.example.nftproject.features.home.HomeFragment
import com.example.nftproject.makerfeatures.mboard.MboardFragment
import com.example.nftproject.makerfeatures.mgift.MgiftFragment
import com.example.nftproject.makerfeatures.mhome.MhomeFragment
import com.example.nftproject.makerfeatures.mmpage.MpageFragment

import com.google.android.material.bottomnavigation.BottomNavigationView

class MakerActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maker)

        bottomNavigationView = findViewById(R.id.nav_dir_bar)

        // 처음 화면
        supportFragmentManager.beginTransaction().add(R.id.maker_frame, HomeFragment()).commit()

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.mhomeFragment -> supportFragmentManager.beginTransaction().replace(R.id.maker_frame, MhomeFragment()).commit()
                R.id.mgiftFragment -> supportFragmentManager.beginTransaction().replace(R.id.maker_frame, MgiftFragment()).commit()
                R.id.mboardFragment -> supportFragmentManager.beginTransaction().replace(R.id.maker_frame, MboardFragment()).commit()
                R.id.mmypageFragment -> supportFragmentManager.beginTransaction().replace(R.id.maker_frame, MpageFragment()).commit()
            }
            true
        }
    }
}