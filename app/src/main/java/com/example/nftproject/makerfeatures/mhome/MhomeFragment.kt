package com.example.nftproject.makerfeatures.mhome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nftproject.MyApplication
import com.example.nftproject.R
import com.example.nftproject.databinding.FragmentHomeBinding
import com.example.nftproject.databinding.FragmentMhomeBinding
import com.example.nftproject.makerfeatures.makeNft.MakenftFragment
import com.example.nftproject.model.nftListItem
import com.example.nftproject.network.util.LoadingDialog
import com.example.nftproject.network.util.X_ACCESS_TOKEN
import com.example.nftproject.network.util.X_REFRESH_TOKEN
import com.unity.mynativeapp.config.DialogFragment
import kotlin.math.log

class MhomeFragment: DialogFragment<FragmentMhomeBinding>(FragmentMhomeBinding::bind, R.layout.fragment_mhome)  {

    lateinit var homeAdapter: MhomeAdaptor
    private val viewModel by viewModels<MhomeViewModel>()
    private var getPostHasNext = false
    private var getPostIsFirst = true
    private var currentPage = 0
    private val pageSize = 20
    private lateinit var publisherName: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        subscribeUI()
        binding.makenftBtn.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.mhomeFrame, MakenftFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun setView() {
        // 게시글 어댑터 설정
        homeAdapter = MhomeAdaptor(requireContext())
        binding.rvMynftList.adapter = homeAdapter
        // LinearLayoutManager로 레이아웃 매니저 설정
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvMynftList.layoutManager = layoutManager
        // 유저 이름
        publisherName = MyApplication.prefUtil.getString("username", "").toString()
        Log.d("이름",publisherName)
        // 내 게시물 목록 요청
        viewModel.myPost("최신순", publisherName, currentPage, pageSize)

    }

    private fun subscribeUI() {
        viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) showLoadingDialog(requireContext()) else dismissLoadingDialog()
        }
        viewModel.logout.observe(this){
            if(it) logout()
        }

        viewModel.myNftData.observe(viewLifecycleOwner) { data ->
            if (data != null) {
                homeAdapter.removeAllItem()

                val getNftInfo = data.nftListDtos

                if (getNftInfo.isEmpty()) {
                    binding.emptyView.visibility = View.VISIBLE
                    binding.rvMynftList.visibility = View.GONE
                } else {
                    binding.emptyView.visibility = View.INVISIBLE
                    binding.rvMynftList.visibility = View.VISIBLE
                }

                for (pnft in getNftInfo) {
                    homeAdapter.addItem(
                        nftListItem(
                            movieTitle = pnft.movieTitle,
                            nftPrice = pnft.nftPrice,
                            nftCount = pnft.nftCount,
                            runningTime = pnft.runningTime,
                            saleStartDate = pnft.saleStartDate,
                            saleEndDate = pnft.saleEndDate
                        )
                    )
                }

                getPostHasNext = data.hasNext
                getPostIsFirst = data.isFirst
            }
        }
    }
}