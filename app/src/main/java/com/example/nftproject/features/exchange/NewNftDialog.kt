package com.example.nftproject.features.exchange

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.example.nftproject.R
import com.example.nftproject.databinding.DialogNewNftBinding


class NewNftDialog(context: Context, private val newimg: String?, private val newtitle: String?, private val newlevel: String?) : Dialog(context) {
    val binding by lazy { DialogNewNftBinding.inflate(layoutInflater) }
    var resultCode = 0
    var img =true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Glide.with(context).load(newimg).into(binding.newnftImg)
        binding.tiketQr.visibility= View.INVISIBLE
        binding.btnQr.visibility= View.INVISIBLE
        binding.newnftImg.setOnClickListener {
            binding.newnftImg.visibility= View.INVISIBLE
            binding.btnApply.visibility= View.INVISIBLE
            binding.tiketQr.visibility= View.VISIBLE
            binding.btnQr.visibility= View.VISIBLE
        }
        binding.tiketQr.setOnClickListener {
            binding.newnftImg.visibility= View.VISIBLE
            binding.btnApply.visibility= View.VISIBLE
            binding.tiketQr.visibility= View.INVISIBLE
            binding.btnQr.visibility= View.INVISIBLE
        }
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
        binding.btnQr.setOnClickListener {
            val url = "https://www.cgv.co.kr/"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        }


    }
    override fun show() {
        if (!this.isShowing) super.show()
    }

    override fun dismiss() {
        if (this.isShowing) super.dismiss()
    }

}

