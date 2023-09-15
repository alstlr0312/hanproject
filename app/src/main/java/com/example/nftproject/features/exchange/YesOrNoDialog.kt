package com.example.nftproject.features.exchange

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import com.example.nftproject.databinding.DialogYesOrNo1Binding
import com.example.nftproject.databinding.DialogYesOrNoBinding


class YesOrNoDialog(context: Context): Dialog(context) {
    val binding by lazy { DialogYesOrNoBinding.inflate(layoutInflater) }
    var resultCode = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnCancel.setOnClickListener {
            resultCode = 0
            dismiss()
        }
        binding.btnApply.setOnClickListener {
            resultCode = 1
            dismiss()
        }

    }
    override fun show() {
        if (!this.isShowing) super.show()
    }

    override fun dismiss() {
        if (this.isShowing) super.dismiss()
    }

}

