package com.example.nftproject.features.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.nftproject.databinding.FragmentHomeDetailBinding
import com.example.nftproject.model.GetMovieResponse
import com.example.nftproject.model.movieListItem


class HomeDetailFragment : Fragment() {
    private lateinit var binding: FragmentHomeDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeDetailBinding.inflate(inflater, container, false)
        val postTitle = arguments?.getString("posttitle")
        val postPosterUri = arguments?.getString("postp")
        postTitle?.let {
            binding.titleTxt.text = it
        }
        postPosterUri?.let{
            Glide.with(this).load(it).into(binding.mainImg)
        }

        return binding.root
    }
}