package com.example.nftproject.features.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.nftproject.R
import com.example.nftproject.databinding.FragmentHomeDetailBinding
import com.example.nftproject.model.homeData


class HomeDetailFragment : Fragment() {
    private lateinit var binding: FragmentHomeDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeDetailBinding.inflate(inflater, container, false)

        val data = arguments?.getParcelable<homeData>("data")

        data?.let {
            Glide.with(this).load(it.movieImage).into(binding.mainImg)
            binding.titleTxt.text = it.name
        }

        return binding.root
    }
}