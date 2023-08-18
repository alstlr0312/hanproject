package com.example.nftproject.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.nftproject.R
import com.example.nftproject.databinding.ActivityMainBinding
import com.example.nftproject.features.exchange.ExchangeFragment
import com.example.nftproject.features.gift.GiftFragment
import com.example.nftproject.features.home.HomeFragment
import com.example.nftproject.features.mypage.MypageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.nav_bar)

        // 처음 화면
        supportFragmentManager.beginTransaction().add(R.id.main_frame, HomeFragment()).commit()

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.homeFragment -> supportFragmentManager.beginTransaction().replace(R.id.main_frame, HomeFragment()).commit()
                R.id.exchangeFragment -> supportFragmentManager.beginTransaction().replace(R.id.main_frame, ExchangeFragment()).commit()
                R.id.giftFragment -> supportFragmentManager.beginTransaction().replace(R.id.main_frame, GiftFragment()).commit()
                R.id.mypageFragment -> supportFragmentManager.beginTransaction().replace(R.id.main_frame, MypageFragment()).commit()

            }
            true
        }
    }
}