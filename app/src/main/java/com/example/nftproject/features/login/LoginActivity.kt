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
    }

    private fun setUiEvent() {
        binding.radioGroup.setOnCheckedChangeListener { _, _ ->
            handleRadioButtonSelection()
        }

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
            if(binding.customerloginBtn.isChecked){
                startActivity(Intent(this, MainActivity::class.java))
            }else{ startActivity(Intent(this, MakerActivity::class.java))}
            finish()
        }
    }


}