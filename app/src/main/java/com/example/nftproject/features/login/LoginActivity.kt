package com.example.nftproject.features.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.nftproject.MyApplication
import com.example.nftproject.R
import com.example.nftproject.databinding.ActivityLoginBinding
import com.example.nftproject.features.MainActivity
import com.example.nftproject.features.signup.SignUpFragment
import com.example.nftproject.makerfeatures.MakerActivity
import com.unity.mynativeapp.config.DialogActivity

class LoginActivity : DialogActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    private val viewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)
        binding.radioGroup.check(R.id.directlogin_btn)

        //라디오 버튼 활성화 호출
        handleRadioButtonSelection()
        setView()
        setUiEvent()
        subscribeUI()
    }

    private fun setView(){
        val id = MyApplication.prefUtil.getString("id", null)
        if(id!=null){
            binding.idText.setText(id)
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

    private fun setUiEvent() {
        binding.radioGroup.setOnCheckedChangeListener { _, _ ->
            handleRadioButtonSelection()
        }

        //카카오 로그인
        val intent = Intent(this, MainActivity::class.java)
        binding.kakaoBtn.setOnClickListener { startActivity(intent) }
        //사업자 로그인
        binding.loginBtn.setOnClickListener {
            val id = binding.idText.text.toString()
            val password = binding.pwText.text.toString()
            viewModel.login(id, password)
        }
        //회원가입
        val button = binding.makeaccountBtn
        button.setOnClickListener {
            val fragment1 = SignUpFragment()
            supportFragmentManager.beginTransaction().add(R.id.framelayout, fragment1).commit()
        }



    }

    private fun subscribeUI() {
        viewModel.toastMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(this) { isLoading ->
            if (isLoading) showLoadingDialog(this) else dismissLoadingDialog()
        }

        viewModel.loginSuccess.observe(this) { isSuccess ->
            if (!isSuccess) return@observe
            startActivity(Intent(this, MainActivity::class.java))
            //startActivity(Intent(this, MakerActivity::class.java))
            finish()
        }
    }


}