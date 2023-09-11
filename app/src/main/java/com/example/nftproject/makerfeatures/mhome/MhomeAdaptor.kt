package com.example.nftproject.makerfeatures.mhome

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.nftproject.R
import com.example.nftproject.databinding.MakerLsitRvBinding
import com.example.nftproject.makerfeatures.mhome.detailfnt.DetailNftFragment
import com.example.nftproject.model.nftListItem


class MhomeAdaptor(private val context: Context) : RecyclerView.Adapter<MhomeAdaptor.ViewHolder>() {
    var itemList = mutableListOf<nftListItem>()
    private var counter = 0
    private var listener : OnItemClickListener? = null
    inner class ViewHolder(val binding: MakerLsitRvBinding): RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        fun bind(item: nftListItem){
            counter++
            binding.nnumList.text = counter.toString()
            binding.ntitleList.text = item.movieTitle
            binding.ncostList.text = item.nftPrice.toString()
            binding.ndayList.text = item.saleEndDate.toString()
            binding.nsellList.text = item.nftCount.toString()
            binding.root.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("key",item.id.toString())
                }
                val fragment = DetailNftFragment().apply { arguments = bundle }
                (itemView.context as? AppCompatActivity)?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.mhomeFrame, fragment)
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
        fun onItemClick(v:View, data: nftListItem, pos: Int)
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