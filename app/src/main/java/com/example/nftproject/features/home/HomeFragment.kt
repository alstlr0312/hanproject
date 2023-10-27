package com.example.nftproject.features.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nftproject.R
import com.example.nftproject.databinding.FragmentHomeBinding
import com.example.nftproject.model.movieListItem
import com.unity.mynativeapp.config.DialogFragment
import java.util.*

class HomeFragment: DialogFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {
    lateinit var hAdapter: HomeFraAdapter
    private val viewModel by viewModels<HomeViewModel>()
    private var getPostIsFirst = true
    private var getPostHasNext = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        subscribeUI()
        searchMovie()
    }

    private fun setView() {
        hAdapter = HomeFraAdapter(requireContext())
        binding.rvPostList.adapter = hAdapter
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvPostList.layoutManager = layoutManager
        viewModel.getMovie()
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

                hAdapter.itemList = if (searchText.isEmpty()) {
                    viewModel.myMovieData.value?.movieListDtos?.toMutableList() ?: mutableListOf()
                } else {
                    viewModel.myMovieData.value?.movieListDtos?.filter { data ->
                        val movieName = data.movieTitle?.toLowerCase(Locale.getDefault())
                        movieName != null && movieName.contains(searchText)
                    }?.toMutableList() ?: mutableListOf()
                }

                hAdapter.notifyDataSetChanged()
            }
        })
    }


    private fun subscribeUI() {
        viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
        //    if (isLoading) showLoadingDialog(requireContext()) else dismissLoadingDialog()
        }
        viewModel.logout.observe(this) {
            if (it) logout()
        }

        viewModel.myMovieData.observe(viewLifecycleOwner) { data ->
            Log.d("Check", "Data: $data")
            Log.d("있음", "있음")
            if (data != null) {
                Log.d("Check", "nftListDto: ${data.movieListDtos}")
                Log.d("있음", "있음")
                if (data.movieListDtos != null) {
                    hAdapter.removeAllItem()
                    val getMovieInfo = data.movieListDtos
                    if (getMovieInfo.isEmpty()) {
                        binding.rvPostList.visibility = View.INVISIBLE
                        binding.imageView6.visibility = View.VISIBLE
                        Log.d("있음", "있음")
                    } else {
                        binding.rvPostList.visibility = View.VISIBLE
                        binding.imageView6.visibility = View.INVISIBLE
                        Log.d("있음", "있음")
                    }
                    for (pnft in getMovieInfo) {
                        hAdapter.addItem(
                            movieListItem(
                                id = pnft.id,
                                movieTitle = pnft.movieTitle,
                                poster = pnft.poster
                            )
                        )
                    }
                    getPostHasNext = data.hasNext
                    getPostIsFirst = data.isFirst
                }
            }
        }
    }
}