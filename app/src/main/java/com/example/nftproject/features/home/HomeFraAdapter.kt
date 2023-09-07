package com.example.nftproject.features.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nftproject.R
import com.example.nftproject.databinding.HomefraRvBinding
import com.example.nftproject.model.movieListItem


class HomeFraAdapter(private val context: Context) : RecyclerView.Adapter<HomeFraAdapter.ViewHolder>() {
    var itemList = mutableListOf<movieListItem>()
    private var listener : OnItemClickListener? = null
    inner class ViewHolder(private val binding: HomefraRvBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        fun bind(item: movieListItem) {
            Glide.with(itemView).load(Uri.parse(item.poster)).into(binding.rvMovieimg)
            binding.rvTitle.text = item.movieTitle
            binding.root.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("postp", item.poster)
                    putString("Id",item.id.toString())
                }
                val fragment = HomeDetailFragment().apply { arguments = bundle }
                (itemView.context as? AppCompatActivity)?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.homeFrame, fragment)
                    ?.addToBackStack(null)
                    ?.commit()
            }
        }
        override fun onClick(v: View?) {
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                listener?.onItemClick(itemView, itemList[pos], pos)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(v:View, data: movieListItem, pos : Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(HomefraRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun addItem(item: movieListItem){
        itemList.add(item)
        notifyItemChanged(itemCount-1)
    }

    fun removeAllItem(){
        itemList = mutableListOf()
        notifyDataSetChanged()
    }


}
