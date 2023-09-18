package com.example.nftproject.makerfeatures.mhome.detailfnt

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.nftproject.R
import com.example.nftproject.databinding.FragmentDetailNftBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.unity.mynativeapp.config.DialogFragment


class DetailNftFragment: DialogFragment<FragmentDetailNftBinding>(FragmentDetailNftBinding::bind, R.layout.fragment_detail_nft)  {
    private lateinit var mediaVpAdapter: MediaViewPagerAdapter
    private val viewModel by viewModels<DetailNftViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getString("key")?.toInt()
        if(id != -1) id?.let { viewModel.getNFtDetail(it as Int) }
        subscribeUI()
        //setView()
    }

    private fun setView(){
        // 게시물 미디어 뷰페이저
        /*mediaVpAdapter = MediaViewPagerAdapter(this)
        binding.vpPostMedia.adapter = mediaVpAdapter
        binding.vpPostMedia.offscreenPageLimit = 1
        binding.vpPostMedia.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.vpPostMedia.setCurrentItem(0, false)
        // 뷰페이저 인디케이터
        TabLayoutMediator(binding.tabLayout, binding.vpPostMedia){ tab, pos ->
        }.attach()*/

    }


    private fun subscribeUI() {

        viewModel.loading.observe(this) { isLoading ->
            if (isLoading) showLoadingDialog(requireContext()) else dismissLoadingDialog()
        }

        viewModel.logout.observe(this) {
            if (it) logout()
        }

        viewModel.toastMessage.observe(this) {
            showCustomToast(it)
        }

        // 게시글 조회 성공
        viewModel.PnftDetailData.observe(this) { data ->
            if (data != null) {
             //   val getMedia = ArrayList<String>()
             //   getMedia.add(data.poster)
              //  getMedia.add(data.nftMedia)
                Log.d("dlalwl", data.poster)
                Glide.with(this).load(data.poster).into(binding.nftImg)
               // mediaVpAdapter.removeAllItem()
               // mediaVpAdapter.setMediaList(getMedia)
                binding.mynfttitle.text = data.movieTitle
                binding.nftpricetxt.text = data.nftPrice.toString()+"원"
                binding.nftsalestxt.text = data.nftCount.toString()
                binding.nftpublishtxt.text = data.saleEndTime
                binding.nftendtxt.text = data.saleStartTime
                if(data.nftLevel == "NORMAL"){
                    binding.normalDegree.visibility= View.VISIBLE
                    binding.rareDegree.visibility = View.INVISIBLE
                    binding.legendDegree.visibility = View.INVISIBLE
                }else if(data.nftLevel == "RARE"){
                    binding.normalDegree.visibility= View.INVISIBLE
                    binding.rareDegree.visibility = View.VISIBLE
                    binding.legendDegree.visibility = View.INVISIBLE
                }else{
                    binding.normalDegree.visibility= View.INVISIBLE
                    binding.rareDegree.visibility = View.INVISIBLE
                    binding.legendDegree.visibility = View.VISIBLE
                }
            }
        }
    }
}