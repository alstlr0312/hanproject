package com.example.nftproject.features.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.nftproject.MyApplication
import com.example.nftproject.R
import com.example.nftproject.features.MainActivity
import com.example.nftproject.features.signup.SignUpFragment
import com.example.nftproject.makerfeatures.MakerActivity
import com.example.nftproject.network.util.X_ACCESS_TOKEN
import com.example.nftproject.network.util.X_REFRESH_TOKEN

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            checkLogin()}, 2000)
    }

    fun checkLogin(){
        val accessToken = MyApplication.prefUtil.getString(X_ACCESS_TOKEN, null)
        val nextActivity = if (accessToken != null) LoginActivity::class.java else LoginActivity::class.java
        startActivity(Intent(this, nextActivity))
        finish()
    }
}