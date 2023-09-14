package com.example.nftproject.features.exchange

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.SparseBooleanArray
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.nftproject.R
import com.example.nftproject.databinding.FragmentExchangeBinding
import com.example.nftproject.features.home.HomeViewModel
import com.example.nftproject.model.movieListItem
import com.unity.mynativeapp.config.DialogFragment
import java.util.*


class ExchangeFragment: DialogFragment<FragmentExchangeBinding>(FragmentExchangeBinding::bind, R.layout.fragment_exchange) {
    lateinit var exAdapter: ExcFraAdapter
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
        exAdapter = ExcFraAdapter(requireContext())
        binding.rvPostList.adapter = exAdapter
        val layoutManager = GridLayoutManager(requireContext(), 1)
        binding.rvPostList.layoutManager = layoutManager
        viewModel.getMovie()

        exAdapter.setOnItemCheckedListener(object : ExcFraAdapter.OnItemCheckedListener {
            override fun onItemChecked(item: movieListItem, isChecked: Boolean) {
                if (isChecked) {
                    // 아이템이 체크되었을 때의 동작 구현
                    when {
                        binding.chooseimg1.tag == null -> {
                            binding.chooseimg1.tag = item.id
                            Glide.with(requireContext()).load(Uri.parse(item.poster)).into(binding.chooseimg1)
                        }
                        binding.chooseimg2.tag == null -> {
                            binding.chooseimg2.tag = item.id
                            Glide.with(requireContext()).load(Uri.parse(item.poster)).into(binding.chooseimg2)
                        }
                        binding.chooseimg3.tag == null -> {
                            binding.chooseimg3.tag = item.id
                            Glide.with(requireContext()).load(Uri.parse(item.poster)).into(binding.chooseimg3)
                        }
                        else -> {
                            return
                        }
                    }
                } else {
                    // 아이템이 해제되었을 때의 동작 구현
                    val itemIdToRemove = item.id
                    if (binding.chooseimg1.tag == itemIdToRemove) {
                        binding.chooseimg1.setImageResource(R.drawable.input_img)
                        binding.chooseimg1.tag = null // 태그 초기화
                    } else if (binding.chooseimg2.tag == itemIdToRemove) {
                        binding.chooseimg2.setImageResource(R.drawable.input_img)
                        binding.chooseimg2.tag= null//태그초기화
                    } else {
                        binding.chooseimg3.setImageResource(R.drawable.input_img)
                        binding.chooseimg3.tag = null//태그초기화
                    }
                }
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

                val originalList = viewModel.myMovieData.value?.movieListDtos ?: listOf()

                exAdapter.itemList = if (searchText.isEmpty()) {
                    originalList.toMutableList()
                } else {
                    originalList.filter { data ->
                        val movieName = data.movieTitle?.toLowerCase(Locale.getDefault())
                        movieName != null && movieName.contains(searchText)
                    }.toMutableList()
                }

                exAdapter.notifyDataSetChanged()
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
                    exAdapter.removeAllItem()
                    val getMovieInfo = data.movieListDtos
                    if (getMovieInfo.isEmpty()) {
                        binding.rvPostList.visibility = View.INVISIBLE
                        Log.d("있음", "있음")
                    } else {
                        binding.rvPostList.visibility = View.VISIBLE
                        Log.d("있음", "있음")
                    }
                    for (pnft in getMovieInfo) {
                        exAdapter.addItem(
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