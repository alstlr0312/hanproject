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

data class CbItem(
    val title: String,
    var isChecked: Boolean
)
class PostSortDialog(context: Context): Dialog(context) {
    val binding by lazy { DialogPostSortBinding.inflate(layoutInflater) }

    lateinit var categoryAdapter: CheckBoxAdapter
    var resultCode = 0
    var checkedCateText: String? = null

    var genrelist = listOf(R.string.ACTION,R.string.ART,R.string.ANIMATION,R.string.ADVENTURE,R.string.COMEDY,R.string.CONCERT,R.string.CRIME,R.string.DISASTER,R.string.DOCUMENTARY,
        R.string.DRAMA,R.string.EDUCATION,R.string.EXPERIMENT,R.string.EXPLOITATION,R.string.FANTASY,R.string.HORROR,R.string.MUMBLECORE,R.string.MUSICAL,R.string.MYSTERY,R.string.NARRATIVE,
        R.string.NOIR,R.string.ROMANCE,R.string.SCIENCEFICTION,R.string.SPORT,R.string.SUPERHERO,R.string.THRILLER,R.string.WAR)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        categoryAdapter = CheckBoxAdapter(setCbList(genrelist))
        binding.rvSort.layoutManager = GridLayoutManager(context, 3)
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

    private fun setCbList(titleList: List<Int>): MutableList<CbItem> {
        var list = mutableListOf<CbItem>()

        for(title in titleList){
                list.add(CbItem(context.getString(title), false))
        }
        return list
    }

    inner class CheckBoxAdapter(var cbList: MutableList<CbItem>): RecyclerView.Adapter<CheckBoxAdapter.ViewHolder>(){

        var bindingList = mutableListOf<ItemRvCheckboxBinding>()
        inner class ViewHolder(val binding: ItemRvCheckboxBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(item: CbItem){
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
            holder.bind(cbList[position])
        }
        override fun getItemCount(): Int {
            return cbList.size
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

