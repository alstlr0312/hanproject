package com.example.nftproject.features.exchange

import android.content.Context
import android.net.Uri
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nftproject.databinding.ExchangeRvBinding
import com.example.nftproject.model.movieListItem
import com.example.nftproject.model.nftListItem

class ExcFraAdapter(private val context: Context) : RecyclerView.Adapter<ExcFraAdapter.ViewHolder>() {
    var itemList = mutableListOf<nftListItem>()
    var checkedItems = HashMap<nftListItem, Boolean>()  // Change to HashMap
    private var listener: OnItemClickListener? = null

    private var itemCheckedListener: OnItemCheckedListener? = null

    fun setOnItemCheckedListener(listener: OnItemCheckedListener) {
        itemCheckedListener = listener
    }

    inner class ViewHolder(private val binding: ExchangeRvBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    handleCheckboxClick(itemList[position], isChecked)
                }
            }
        }

        fun bind(item: nftListItem) {
            //Glide.with(itemView).load(Uri.parse(item.poster)).into(binding.rvMovieimg)
            binding.rvTitle.text = item.movieTitle

            // Set checkbox state
            binding.checkBox.isChecked = checkedItems[item] ?: false  // Change to use the movieListItem as key instead of position.
        }

        override fun onClick(v: View?) {
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                listener?.onItemClick(itemView, itemList[pos], pos)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ExchangeRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    // 체크박스 클릭 처리 메서드
    private fun handleCheckboxClick(item: nftListItem, isChecked: Boolean) {
        if (isChecked && checkedItems.values.count { it } >= 3 && !checkedItems.getOrDefault(item,false)) {
            Toast.makeText(context, "이미 3개가 선택되어 있습니다", Toast.LENGTH_SHORT).show()
            return
        }
        // Update selection status.
        checkedItems[item] = isChecked
        itemCheckedListener?.onItemChecked(item, isChecked)
    }


    // 아이템 추가 메서드
    fun addItem(item: nftListItem){
        itemList.add(item)
        notifyItemInserted(itemCount-1)
    }

    // 모든 아이템 제거 메서드
    fun removeAllItem(){
        itemList.clear()
        notifyDataSetChanged()
    }

    interface OnItemCheckedListener {
        fun onItemChecked(item: nftListItem, isChecked: Boolean)
    }
    interface OnItemClickListener{
        fun onItemClick(v: View, data: nftListItem, pos : Int)
    }
}
