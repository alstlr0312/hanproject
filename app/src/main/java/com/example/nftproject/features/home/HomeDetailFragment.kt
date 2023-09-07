package com.example.nftproject.features.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.nftproject.R
import com.example.nftproject.databinding.FragmentHomeDetailBinding
import com.unity.mynativeapp.config.DialogFragment


class HomeDetailFragment: DialogFragment<FragmentHomeDetailBinding>(FragmentHomeDetailBinding::bind, R.layout.fragment_home_detail)  {
    private var firstStart = true
    private val viewModel by viewModels<HomeViewModel>()
    private var postId = -1
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getString("Id")?.toInt()
        val postPosterUri = arguments?.getString("postp")
        postPosterUri?.let{
            Glide.with(this).load(it).into(binding.mainImg)
        }

        if(id != -1) id?.let { viewModel.getMovieDetail(it as Int) }
        subscribeUI()
    }
    private fun subscribeUI() {

        viewModel.loading.observe(this) { isLoading ->
            if (isLoading) showLoadingDialog(this) else dismissLoadingDialog()
        }

        viewModel.logout.observe(this) {
            if (it) logout()
        }

        viewModel.toastMessage.observe(this) {
            showCustomToast(it)
        }

        // 게시글 조회 성공
        viewModel.postDetailData.observe(this) { data ->
            if (data != null) {
                /*Glide.with(binding.mainImg)
                    .load(data.poster)
                    .apply(RequestOptions.centerCropTransform())
                    .into(binding.mainImg)*/
                binding.titleTxt.text = data.movieTitle
                binding.publisherTxt.text = data.publisherName
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
                binding.saledateTxt.text = data?.saleStartTime
                binding.saleendTxt.text = data?.saleEndTime
                binding.runningTimeTxt.text = data.runningTime.toString() + "분"
                binding.sellBtn.text = data.normalNFTPrice.toString()+"원"
                binding.directerTxt.text = data.director
                binding.actorsTxt.text = data?.actors?.takeIf { it.isNotEmpty() }?.joinToString(", ") ?: ""
                binding.filmRatingTxt.text = data.filmRating
                binding.genreTxt.text = data.movieGenre
                binding.descriptionTxt.text = data.description
                binding.createdAtTxt.text = data.createdAt.substring(2, 10)
            }
        }
    }
}