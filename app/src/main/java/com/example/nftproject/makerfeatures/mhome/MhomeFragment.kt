package com.example.nftproject.makerfeatures.mhome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.example.nftproject.R
import com.example.nftproject.databinding.FragmentMhomeBinding
import com.example.nftproject.features.home.HomeDetailFragment
import com.example.nftproject.features.home.HomeFraAdapter
import com.example.nftproject.model.homeData
import com.example.nftproject.model.mnftData

class MhomeFragment : Fragment() {
    private var _binding: FragmentMhomeBinding? = null
    private val binding get() = _binding!!
    lateinit var homeAdapter: MhomeAdaptor
    private val datas = arrayListOf<mnftData>()
    private lateinit var listener: AdapterView.OnItemSelectedListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMhomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        binding.makenftBtn.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.mhomeFrame, MakenftFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun initRecycler() {
        homeAdapter = MhomeAdaptor(requireContext())
        binding.rvMynftList.apply {
            datas.apply {
                add(mnftData(
                    num = "1",
                    title = "세상은 아름다워", cost = "10000", day = "2023-05-06", sell = "1000"))
            }

            homeAdapter.datas = datas
            binding.rvMynftList.adapter = homeAdapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}