package com.example.nftproject.makerfeatures.makeNft

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nftproject.R
import com.example.nftproject.databinding.DialogPostSortBinding
import com.example.nftproject.databinding.ItemRvCheckboxBinding

data class AgeItem(
    val title: String,
    var isChecked: Boolean
)
class PostageSortDialog(context: Context): Dialog(context) {
    val binding by lazy { DialogPostSortBinding.inflate(layoutInflater) }

    lateinit var categoryAdapter: CheckBoxAdapter
    var resultCode = 0
    var checkedCateText: String? = null

    var genrelist = listOf(R.string.PG12,R.string.PG15,R.string.G_RATED,R.string.PG18)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        categoryAdapter = CheckBoxAdapter(setAgeList(genrelist))
        binding.rvSort.layoutManager = GridLayoutManager(context, 2)
        binding.rvSort.adapter = categoryAdapter

        binding.btnCancel.setOnClickListener {
            resultCode = 0
            dismiss()
        }
        binding.btnApply.setOnClickListener {
            resultCode = 1
            checkedCateText = categoryAdapter.getCheckedText()
            dismiss()
        }

    }

    override fun show() {
        if (!this.isShowing) super.show()
    }

    override fun dismiss() {
        if (this.isShowing) super.dismiss()
    }

    private fun setAgeList(titleList: List<Int>): MutableList<AgeItem> {
        var list = mutableListOf<AgeItem>()

        for(title in titleList){
                list.add(AgeItem(context.getString(title), false))
        }
        return list
    }

    inner class CheckBoxAdapter(var ageList: MutableList<AgeItem>): RecyclerView.Adapter<CheckBoxAdapter.ViewHolder>(){

        var bindingList = mutableListOf<ItemRvCheckboxBinding>()
        inner class ViewHolder(val binding: ItemRvCheckboxBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(item: AgeItem){
                binding.checkBox.text = item.title
                binding.checkBox.isChecked = item.isChecked
            }

            init{
                bindingList.add(binding)
                binding.checkBox.setOnClickListener {
                    for(item in bindingList){
                        item.checkBox.isChecked = item == binding
                    }
                }
            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(ItemRvCheckboxBinding.inflate(layoutInflater, parent, false))
        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(ageList[position])
        }
        override fun getItemCount(): Int {
            return ageList.size
        }

        fun getCheckedText(): String? {
            for(item in bindingList){
                if(item.checkBox.isChecked){
                    return item.checkBox.text.toString()
                }
            }
            return null
        }


    }
}

