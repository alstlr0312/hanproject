package com.unity.mynativeapp.config

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.nftproject.network.util.LoadingDialog


abstract class DialogActivity<B: ViewBinding>(private val inflate: (LayoutInflater) -> B):
    AppCompatActivity() {
    protected lateinit var binding: B
        private set
    lateinit var loadingDialog: LoadingDialog
    lateinit var inputMethodManager: InputMethodManager
    var keyBoardIsShowing: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)

        inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    }

    fun showCustomToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showLoadingDialog(context: Context) {
        loadingDialog = LoadingDialog(context)
        loadingDialog.show()
    }

    fun dismissLoadingDialog() {
        if (loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
    }

    // 키보드 보이기
    fun hideKeyBoard(){
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    fun showKeyBoard(edtText: EditText){
        keyBoardIsShowing = inputMethodManager.showSoftInput(edtText, InputMethodManager.HIDE_IMPLICIT_ONLY)

    }


}