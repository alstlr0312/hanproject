package com.example.nftproject.features.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nftproject.R
import com.example.nftproject.databinding.ActivityLoginBinding
import com.example.nftproject.features.MainActivity
import com.example.nftproject.features.signup.SignUpFragment

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val intent = Intent(this, MainActivity::class.java)
        binding.loginBtn.setOnClickListener{startActivity(intent)}

        val button = binding.makeaccountBtn
        button.setOnClickListener{
            val fragment1 = SignUpFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.framelayout, fragment1)
                .commit()
        }

    }
}