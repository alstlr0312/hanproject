package com.example.nftproject.features.exchange

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.nftproject.MyApplication
import com.example.nftproject.R
import com.example.nftproject.databinding.FragmentExchangeBinding
import com.example.nftproject.makerfeatures.mhome.MhomeFragment
import com.example.nftproject.model.nftPickItem
import com.unity.mynativeapp.config.DialogFragment
import java.util.*


class ExchangeFragment: DialogFragment<FragmentExchangeBinding>(FragmentExchangeBinding::bind, R.layout.fragment_exchange) {
    lateinit var exAdapter: ExcFraAdapter
    private val viewModel by viewModels<ExViewModel>()
    private var getPostIsFirst = true
    private var getPostHasNext = false
    private lateinit var publisherName: String
    private var currentPage = 0
    private val pageSize = 20

    var nftSerialnum1: String? = null
    var nftLevel1: String? = null
    var nftSerialnum2: String? = null
    var nftLevel2: String? = null
    var nftSerialnum3: String? = null
    var nftLevel3: String? = null

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
        publisherName = MyApplication.prefUtil.getString("username", "").toString()
        viewModel.getmyPost( publisherName, currentPage, pageSize)

        exAdapter.setOnItemCheckedListener(object : ExcFraAdapter.OnItemCheckedListener {
            override fun onItemChecked(item: nftPickItem, isChecked: Boolean) {
                if (isChecked) {
                    // 아이템이 체크되었을 때의 동작 구현
                    when {
                        binding.chooseimg1.tag == null -> {
                            binding.chooseimg1.tag = item.movieTitle
                            nftSerialnum1 = item.nftSerialnum
                            nftLevel1 = item.nftLevel
                            Glide.with(requireContext()).load(Uri.parse(item.poster)).into(binding.chooseimg1)
                        }
                        binding.chooseimg2.tag == null -> {
                            binding.chooseimg2.tag = item.movieTitle
                            nftSerialnum2 = item.nftSerialnum
                            nftLevel2 = item.nftLevel
                            Glide.with(requireContext()).load(Uri.parse(item.poster)).into(binding.chooseimg2)
                        }
                        binding.chooseimg3.tag == null -> {
                            binding.chooseimg3.tag = item.movieTitle
                            nftSerialnum3 = item.nftSerialnum
                            nftLevel3 = item.nftLevel
                            Glide.with(requireContext()).load(Uri.parse(item.poster)).into(binding.chooseimg3)
                        }
                        else -> {
                            return
                        }
                    }
                } else {
                    // 아이템이 해제되었을 때의 동작 구현
                    val itemIdToRemove = item.movieTitle
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

        binding.changeBtn.setOnClickListener {
            val dialog = YesOrNoDialog(requireContext())
            dialog.show()
            dialog.setOnDismissListener {
                if (dialog.resultCode == 1) {
                    viewModel.exNftPost(
                        nftSerialnum1.toString(), nftLevel1.toString(),nftSerialnum2.toString() ,nftLevel2.toString(),nftSerialnum3.toString() ,nftLevel3.toString())
                }
            }
        }
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
                val originalList = viewModel.emyNftData.value?.nftPickDtos ?: listOf()

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

        viewModel.emyNftData.observe(viewLifecycleOwner) { data ->
            Log.d("Check", "Data: $data")
            if (data != null) {
                if (data.nftPickDtos != null) {
                    exAdapter.removeAllItem()
                    val getMovieInfo = data.nftPickDtos

                    for (pnft in getMovieInfo) {
                        exAdapter.addItem(
                            nftPickItem(
                                poster = pnft.poster,
                                movieTitle = pnft.movieTitle,
                                nftLevel = pnft.nftLevel,
                                nftSerialnum = pnft.nftSerialnum,
                            )
                        )
                    }
                    getPostHasNext = data.hasNext
                    getPostIsFirst = data.isFirst
                }
            }
        }

        viewModel.newNftData.observe(viewLifecycleOwner) { data ->
            Log.d("Check", "Data: $data")
            if (data != null) {
                val newimg = data.mediaUrl
                val newtitle = data.movieName
                val newlevel = data.nftLevel
                Log.d("다이얼로그확인", newtitle)
                val dialog = NewNftDialog(requireContext(), newimg, newtitle, newlevel)
                dialog.show()
                dialog.setOnDismissListener {
                    if (dialog.resultCode == 1) {
                        val refreshedFragment = ExchangeFragment()
                        requireActivity().supportFragmentManager.beginTransaction().apply {
                            replace(R.id.changeFrame, refreshedFragment)
                            commit()
                        }
                    }
                }
            }
        }
    }
}