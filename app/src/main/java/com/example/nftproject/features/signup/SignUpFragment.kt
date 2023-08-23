package com.example.nftproject.features.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.nftproject.databinding.FragmentSignUpBinding
import com.example.nftproject.network.util.LoadingDialog
import com.example.nftproject.network.util.hideKeyboard
import java.util.regex.Pattern

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    //private val viewModel by viewModels<SignUpViewModel>()
    val emailPattern = android.util.Patterns.EMAIL_ADDRESS
    val pwPattern = Pattern.compile("^(?=.*([a-z].*[A-Z])|([A-Z].*[a-z]))(?=.*[0-9])(?=.*[\$@\$!%*#?&.])[A-Za-z[0-9]\$@\$!%*#?&.]{8,20}\$")
    val checkArr = arrayListOf(false, false, false, false)

    lateinit var dialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog = LoadingDialog(requireContext())

        //subscribeUI()

        setUiEvent()
    }

    private fun setUiEvent() {
        binding.btnEmailAuthenticate.setOnClickListener {
          //  viewModel.check(binding.edtEmail.text.toString())
        }

        binding.btnSignUp.setOnClickListener {
            val id = binding.edtId.text.toString()
            val pw = binding.edtPassword.text.toString()
            val pwCheck = binding.edtPasswordConfirm.text.toString()
            val name = binding.edtUsername.text.toString()
            val email = binding.edtEmail.text.toString()
            val code = binding.edtAuthenticateCode.text.toString()

   //         viewModel.signup(id, pw, pwCheck, email, name, field, code)
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.layoutMain.setOnClickListener {
            this.hideKeyboard()
        }

        // 아이디 입력 이벤트
        binding.edtId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                val id = binding.edtId.text.toString()
                if (p0 == null || id.isEmpty()) {
                    binding.tvId.visibility = View.GONE
                    return
                }

                if (id.length < 5 || id.length > 20) {
                    binding.tvId.visibility = View.VISIBLE
                    checkArr[0] = false
                } else {
                    binding.tvId.visibility = View.GONE
                    checkArr[0] = true
                }
            }
        })

        // 비밀번호 입력 이벤트
        binding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                val pw = binding.edtPassword.text.toString()
                if (p0 == null || pw.isEmpty()) {
                    binding.tvPassword.visibility = View.GONE
                    return
                }

                if (pwPattern.matcher(pw).find()) {
                    binding.tvPassword.visibility = View.GONE
                    checkArr[1] = true
                } else {
                    binding.tvPassword.visibility = View.VISIBLE
                    checkArr[1] = false
                }

            }
        })

        // 비밀번호 확인 이벤트
        binding.edtPasswordConfirm.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                var pwConfirm = binding.edtPasswordConfirm.text.toString()
                if (p0 != null && !pwConfirm.equals("")) {
                    if (checkArr[1] && binding.edtPassword.text.toString() == pwConfirm) {
                        binding.tvPasswordConfirm.visibility = View.GONE
                        checkArr[2] = true
                    } else {
                        binding.tvPasswordConfirm.visibility = View.VISIBLE
                        checkArr[2] = false
                    }
                } else {
                    binding.tvPasswordConfirm.visibility = View.GONE
                }

            }
        })

        // 이메일 입력 이벤트
        binding.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                var email = binding.edtEmail.text.toString()
                if (p0 != null && !email.equals("")) { // 공백이 아니라면
                    if (emailPattern.matcher(email).matches()) { // 이메일 형식이 맞다면
                        binding.tvEmail.visibility = View.GONE
                        checkArr[3] = true
                        binding.btnEmailAuthenticate.visibility = View.VISIBLE

                    } else { // 이메일 형식이 아니라면
                        binding.tvEmail.visibility = View.VISIBLE
                        checkArr[3] = false
                        binding.btnEmailAuthenticate.visibility = View.GONE

                    }
                } else { // 공백 이라면
                    binding.tvEmail.visibility = View.GONE
                    binding.btnEmailAuthenticate.visibility = View.GONE

                }
            }
        })
    }

    /*private fun subscribeUI() {
        viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) dialog.show() else dialog.dismiss()
        }

        viewModel.checkSuccess.observe(viewLifecycleOwner){ isSuccess ->
            if(!isSuccess) return@observe
            binding.edtEmail.isEnabled = false
            binding.tvEmail.text = getString(R.string.please_input_email_code)
            binding.tvEmail.visibility = View.VISIBLE
            binding.edtAuthenticateCode.visibility = View.VISIBLE
            binding.btnEmailAuthenticate.visibility = View.GONE
        }

        viewModel.signupSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if(!isSuccess) { // 이메일 중복
                binding.edtEmail.isEnabled = true
                binding.edtEmail.setText("")
                binding.tvEmail.text= getString(R.string.email_invalied_format)
                binding.edtAuthenticateCode.setText("")
                return@observe
            }
            findNavController().popBackStack()
        }
    }*/
}