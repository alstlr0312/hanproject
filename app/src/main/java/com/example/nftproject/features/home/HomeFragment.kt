package com.example.nftproject.features.home

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nftproject.R
import com.example.nftproject.databinding.FragmentHomeBinding
import com.example.nftproject.model.homeData
import java.util.*

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var homeAdapter: HomeFraAdapter
    private val datas = arrayListOf<homeData>()
    val filterdatas = mutableListOf<homeData>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        searchMovie()
    }

    private fun initRecycler() {
        homeAdapter = HomeFraAdapter(requireContext())
        binding.rvPostList.apply {
            datas.apply {
                add(homeData(movieImage = R.drawable.exchange_icon, name = "mary"))
                add(homeData(movieImage = R.drawable.gift_icon, name = "massry"))
                add(homeData(movieImage = R.drawable.ic_arrow_left, name = "marydf"))
                add(homeData(movieImage = R.drawable.img_logo, name = "mar334y"))
                add(homeData(movieImage = R.drawable.exchange_icon, name = "mary"))
                add(homeData(movieImage = R.drawable.exchange_icon, name = "mary"))
                add(homeData(movieImage = R.drawable.exchange_icon, name = "mary"))
                add(homeData(movieImage = R.drawable.exchange_icon, name = "mary"))
                add(homeData(movieImage = R.drawable.exchange_icon, name = "mary"))
            }
            layoutManager = GridLayoutManager(requireContext(),2)
            setHasFixedSize(true)
            homeAdapter.datas = datas
            homeAdapter.notifyDataSetChanged()
            binding.rvPostList.adapter = homeAdapter
        }

        homeAdapter.setOnItemClickListener(object : HomeFraAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: homeData, pos : Int) {
               Log.d(TAG,"zmfflr")
            }

        })

    }
    private fun searchMovie() {
        binding.searchtext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // No implementation needed
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // No implementation needed
            }

            override fun afterTextChanged(s: Editable) {
                val searchText = s.toString().toLowerCase(Locale.getDefault())
                filterdatas.clear()
                if (searchText.isEmpty()) {
                    filterdatas.addAll(datas)
                } else {
                    datas.forEach { data ->
                        val movieName = data.name?.toLowerCase(Locale.getDefault())
                        if (movieName != null) {
                            if (movieName.contains(searchText)) {
                                filterdatas.add(data)
                            }
                        }
                    }
                }
                homeAdapter.datas = filterdatas
                homeAdapter.notifyDataSetChanged()
            }
        })
    }

}