package com.example.nftproject.features.home

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import com.example.nftproject.databinding.DialogYesOrNo1Binding


class YesOrNo1Dialog(context: Context): Dialog(context) {
    val binding by lazy { DialogYesOrNo1Binding.inflate(layoutInflater) }
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

