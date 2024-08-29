package com.example.todopractice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todopractice.databinding.ItemRecyclerviewBinding

class MyViewHolder(val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root) {
    init {
        binding.delBtn.setOnClickListener {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                (binding.root.context as MainActivity).adapter.removeItem(position)
                (binding.root.context as MainActivity).selectedPosition = position

            }
        }
    }
}

class MyAdapter(val datas: MutableList<String>?): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
        = MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        binding.itemData.text=datas!![position]
    }

    fun removeItem(position: Int){
        datas?.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, datas?.size ?: 0)
    }

}
