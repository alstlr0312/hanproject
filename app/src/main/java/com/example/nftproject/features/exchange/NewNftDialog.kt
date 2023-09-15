package com.example.nftproject.features.exchange

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.nftproject.R
import com.example.nftproject.databinding.DialogNewNftBinding


class NewNftDialog(context: Context, private val newimg: String?, private val newtitle: String?, private val newlevel: String?) : Dialog(context) {
    val binding by lazy { DialogNewNftBinding.inflate(layoutInflater) }
    var resultCode = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Glide.with(context).load(newimg).into(binding.newnftImg)
        binding.newnftTitle.text = newtitle
        if (newlevel == "LEGEND") {
            binding.newnftLevel.setImageResource(R.drawable.legend_img)
        } else if(newlevel == "NORMAL"){
            binding.newnftLevel.setImageResource(R.drawable.normal_img)
        } else{binding.newnftLevel.setImageResource(R.drawable.rare_img)}

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

