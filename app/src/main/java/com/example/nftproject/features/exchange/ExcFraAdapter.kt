package com.example.nftproject.features.exchange

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nftproject.R
import com.example.nftproject.databinding.ExchangeRvBinding
import com.example.nftproject.model.nftPickItem

class ExcFraAdapter(private val context: Context) : RecyclerView.Adapter<ExcFraAdapter.ViewHolder>() {
    var itemList = mutableListOf<nftPickItem>()
    var checkedItems = HashMap<nftPickItem, Boolean>()  // Change to HashMap
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

        fun bind(item: nftPickItem) {
            Glide.with(itemView).load(Uri.parse(item.poster)).into(binding.rvMovieimg)
            binding.rvTitle.text = item.movieTitle
            val movieLevel = item.nftLevel
            val imageResource = getMovieLevelImageResource(movieLevel) // movie level 값에 해당하는 이미지 리소스 ID 가져옴
            binding.exlevelImg.setImageResource(imageResource)
            // Set checkbox state
            binding.checkBox.isChecked = checkedItems[item] ?: false  // Change to use the movieListItem as key instead of position.
        }

        override fun onClick(v: View?) {
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                listener?.onItemClick(itemView, itemList[pos], pos)
            }
        }
        private fun getMovieLevelImageResource(movieLevel: String): Int {
            return when (movieLevel) {
                "LEGEND" -> R.drawable.legend_img
                "NORMAL" -> R.drawable.normal_img
                else -> R.drawable.rare_img
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
    private fun handleCheckboxClick(item: nftPickItem, isChecked: Boolean) {
        if (isChecked && checkedItems.values.count { it } >= 3 && !checkedItems.getOrDefault(item,false)) {
            Toast.makeText(context, "이미 3개가 선택되어 있습니다", Toast.LENGTH_SHORT).show()
            return
        }
        // Update selection status.
        checkedItems[item] = isChecked
        itemCheckedListener?.onItemChecked(item, isChecked)
    }


    // 아이템 추가 메서드
    fun addItem(item: nftPickItem){
        itemList.add(item)
        notifyItemInserted(itemCount-1)
    }

    // 모든 아이템 제거 메서드
    fun removeAllItem(){
        itemList.clear()
        notifyDataSetChanged()
    }

    interface OnItemCheckedListener {
        fun onItemChecked(item: nftPickItem, isChecked: Boolean)
    }
    interface OnItemClickListener{
        fun onItemClick(v: View, data: nftPickItem, pos : Int)
    }
}
