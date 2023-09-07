package com.unity.mynativeapp.config

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.nftproject.MyApplication
import com.example.nftproject.features.home.HomeDetailFragment
import com.example.nftproject.network.util.LoadingDialog
import com.example.nftproject.network.util.X_ACCESS_TOKEN
import com.example.nftproject.network.util.X_REFRESH_TOKEN
import kotlin.system.exitProcess

open class DialogFragment<B : ViewBinding>(private val bind: (View) -> B, @LayoutRes layoutResId: Int
) : Fragment(layoutResId) {
    private var _binding: B? = null

    lateinit var lodingDialog: LoadingDialog
    protected val binding get() = _binding!!

    companion object {
        const val SHOW_LOADING = 0
        const val SHOW_TEXT_LOADING = 1
        const val DISMISS_LOADING = 2
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    fun showCustomToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    fun showLoadingDialog(context: HomeDetailFragment) {
      //  lodingDialog = LoadingDialog(context)
        //lodingDialog.show()
    }

    fun dismissLoadingDialog() {
        if (::lodingDialog.isInitialized) {
            lodingDialog.dismiss()
        }
    }
    fun logout() {
        MyApplication.prefUtil.removeKey(X_ACCESS_TOKEN)
        MyApplication.prefUtil.removeKey(X_REFRESH_TOKEN)

        try {
            startActivity(
                Intent.makeRestartActivityTask(
                    requireActivity().packageManager.getLaunchIntentForPackage(
                        requireActivity().packageName
                    )?.component
                )
            )
            exitProcess(0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}