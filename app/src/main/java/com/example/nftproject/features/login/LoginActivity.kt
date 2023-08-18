package com.example.nftproject.features.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.nftproject.R
import com.example.nftproject.databinding.ActivityLoginBinding
import com.example.nftproject.features.MainActivity
import com.example.nftproject.features.home.HomeFragment

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val intent = Intent(this, MainActivity::class.java)
        binding.loginBtn.setOnClickListener{startActivity(intent)}

    }
}