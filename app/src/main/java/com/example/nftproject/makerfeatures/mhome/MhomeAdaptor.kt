package com.example.nftproject.makerfeatures.mhome

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nftproject.databinding.MakerLsitRvBinding
import com.example.nftproject.model.mnftData


class MhomeAdaptor(private val context: Context) : RecyclerView.Adapter<MhomeAdaptor.ViewHolder>() {

    var datas = mutableListOf<mnftData>()
    private var listener : OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MakerLsitRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    interface OnItemClickListener{
        fun onItemClick(v: View, data: mnftData, pos : Int)
    }

    fun setOnItemClickListener(listener : OnItemClickListener?) {
        this.listener = listener
    }

    inner class ViewHolder(private val binding: MakerLsitRvBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: mnftData) {
            binding.nnumList.text = item.num
            binding.ntitleList.text = item.title
            binding.ncostList.text = item.cost
            binding.ndayList.text = item.day
            binding.nsellList.text = item.sell
        }

        override fun onClick(v: View?) {
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                listener?.onItemClick(itemView, datas[pos], pos)
            }
        }
    }
}
