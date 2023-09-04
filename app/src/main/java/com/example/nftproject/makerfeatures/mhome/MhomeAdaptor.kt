package com.example.nftproject.makerfeatures.mhome

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nftproject.databinding.MakerLsitRvBinding
import com.example.nftproject.model.nftListItem


class MhomeAdaptor(private val context: Context) : RecyclerView.Adapter<MhomeAdaptor.ViewHolder>() {
    var itemList = mutableListOf<nftListItem>()
    private var counter = 0
    inner class ViewHolder(val binding: MakerLsitRvBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: nftListItem){
            counter++
            binding.nnumList.text = counter.toString()
            binding.ntitleList.text = item.movieTitle
            binding.ncostList.text = item.nftPrice.toString()
            binding.ndayList.text = item.saleEndDate.toString()
            binding.nsellList.text = item.nftCount.toString()

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MakerLsitRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun addItem(item: nftListItem){
        itemList.add(item)
        notifyItemChanged(itemCount-1)
    }

    fun removeAllItem(){
        itemList = mutableListOf()
        notifyDataSetChanged()
    }


}