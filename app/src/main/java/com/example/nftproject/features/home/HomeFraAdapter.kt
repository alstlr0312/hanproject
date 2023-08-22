package com.example.nftproject.features.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nftproject.R
import com.example.nftproject.databinding.HomefraRvBinding
import com.example.nftproject.model.homeData

class HomeFraAdapter(private val context: Context) : RecyclerView.Adapter<HomeFraAdapter.ViewHolder>() {

    var datas = mutableListOf<homeData>()
    private var listener : OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomefraRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    interface OnItemClickListener{
        fun onItemClick(v:View, data: homeData, pos : Int)
    }

    fun setOnItemClickListener(listener : OnItemClickListener?) {
        this.listener = listener
    }

    inner class ViewHolder(private val binding: HomefraRvBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: homeData) {
            Glide.with(itemView).load(item.movieImage).into(binding.rvMovieimg)
            binding.rvTitle.text = item.name
        }

        override fun onClick(v: View?) {
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                listener?.onItemClick(itemView, datas[pos], pos)
            }
        }
    }
}
