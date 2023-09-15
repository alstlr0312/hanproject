package com.example.nftproject.features.home

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.nftproject.MyApplication
import com.example.nftproject.R
import com.example.nftproject.databinding.FragmentHomeDetailBinding
import com.example.nftproject.makerfeatures.makeNft.PostageSortDialog
import com.example.nftproject.makerfeatures.mhome.MhomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.unity.mynativeapp.config.DialogFragment


class HomeDetailFragment: DialogFragment<FragmentHomeDetailBinding>(FragmentHomeDetailBinding::bind, R.layout.fragment_home_detail)  {
    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getString("Id")?.toInt()
        val postPosterUri = arguments?.getString("postp")
        postPosterUri?.let{
            Glide.with(this).load(it).into(binding.mainImg)
        }

        if(id != -1) id?.let { viewModel.getMovieDetail(it as Int) }
        subscribeUI()
        hideBottomNavigation(true)
        setUiEvent(id)
    }

    private fun setUiEvent(id: Int?) {
        binding.buyBtn.setOnClickListener {
            val dialog = YesOrNo1Dialog(requireContext())
            dialog.show()
            dialog.setOnDismissListener {
                if (dialog.resultCode == 1) {
                    if (id != null) {
                        viewModel.getBuyNft(id)
                    }
                }
            }
        }
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
                binding.saledateTxt.text = data?.saleStartDate
                binding.saleendTxt.text = data?.saleEndDate
                binding.runningTimeTxt.text = data.runningTime.toString() + "분"
                binding.buyBtn.text = data.normalNFTPrice.toString()+"원"
                binding.directerTxt.text = data.director
                binding.actorsTxt.text = data?.actors?.takeIf { it.isNotEmpty() }?.joinToString(", ") ?: ""
                binding.filmRatingTxt.text = data.filmRating
                binding.genreTxt.text = data.movieGenre
                binding.descriptionTxt.text = data.description
                //binding.createdAtTxt.text = data.createdAt.substring(2, 10)
            }
        }
        viewModel.postBuyNftSuccess.observe(this) { data ->
            Toast.makeText(requireContext(), "구매하셨습니다.", Toast.LENGTH_SHORT).show()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.homeFrame, HomeFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideBottomNavigation(false)
    }
    fun hideBottomNavigation(bool: Boolean) {
        val bottomNavigation = activity!!.findViewById<BottomNavigationView>(R.id.nav_bar)
        if (bool == true) bottomNavigation.visibility = View.GONE else bottomNavigation.visibility =
            View.VISIBLE
    }
}