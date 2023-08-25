package com.example.nftproject.features.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

        // 기본적으로 RadioButton2를 선택하고 초기 상태 설정
        binding.radioGroup.check(R.id.directlogin_btn)

        // 각 버튼의 활성화 상태를 변경하는 함수 호출
        handleRadioButtonSelection()

        // RadioGroup의 변화를 감지하고 각 버튼의 활성화 상태를 변경합니다.
        binding.radioGroup.setOnCheckedChangeListener { _, _ ->
            handleRadioButtonSelection()
        }

        val intent = Intent(this, MainActivity::class.java)
        binding.kakaoBtn.setOnClickListener { startActivity(intent) }

        val button = binding.makeaccountBtn
        button.setOnClickListener {
            val fragment1 = SignUpFragment()
            supportFragmentManager.beginTransaction().add(R.id.framelayout, fragment1).commit()
        }
    }

    private fun handleRadioButtonSelection() {
        val isRadioButton2Checked = binding.customerloginBtn.isChecked

        binding.kakaoBtn.visibility = if (isRadioButton2Checked) View.VISIBLE else View.GONE

        // 나머지 버튼들을 표시 또는 숨김
        binding.idText.visibility = if (!isRadioButton2Checked) View.VISIBLE else View.GONE
        binding.pwText.visibility = if (!isRadioButton2Checked) View.VISIBLE else View.GONE
        binding.loginBtn.visibility = if (!isRadioButton2Checked) View.VISIBLE else View.GONE
        binding.makeaccountBtn.visibility = if (!isRadioButton2Checked) View.VISIBLE else View.GONE
        binding.imageView5.visibility = if (!isRadioButton2Checked) View.VISIBLE else View.GONE
        binding.tvFindId.visibility = if (!isRadioButton2Checked) View.VISIBLE else View.GONE
        binding.tvFindPw.visibility = if (!isRadioButton2Checked) View.VISIBLE else View.GONE
    }
}