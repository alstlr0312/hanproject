package com.example.nftproject.features.home

import android.content.Context
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
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(private val binding: HomefraRvBinding) : RecyclerView.ViewHolder(binding.root) {
        private val txtName: TextView = itemView.findViewById(R.id.rv_title)
        private val imgProfile: ImageView = itemView.findViewById(R.id.rv_movieimg)

        fun bind(item: homeData) {
            txtName.text = item.name
            Glide.with(itemView).load(item.movieImage).into(imgProfile)
            val pos = adapterPosition
            if(pos!= RecyclerView.NO_POSITION)
            {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView,item,pos)
                }
            }
            /*itemView.setOnClickListener {
                Intent(context, MovieDetailActivity::class.java).apply {
                    putExtra("data", item)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }
                Log.d(TAG,"클릭")
            }*/

        }
    }

}