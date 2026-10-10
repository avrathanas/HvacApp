package com.example.hvacapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hvacapp.data.entities.Peratosi
import com.example.hvacapp.databinding.ItemPeratosiBinding

class PeratosiAdapter(
    private var list: List<Peratosi>,
    private val onDelete: (Peratosi) -> Unit,
    private val onClick: (Peratosi) -> Unit
) : RecyclerView.Adapter<PeratosiAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemPeratosiBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val b = ItemPeratosiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(b)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.tvItemNamePeratosi.text = item.onoma
        holder.binding.tvItemErgasiaPeratosi.text = item.ergasia
        holder.binding.btnDeletePeratosi.setOnClickListener { onDelete(item) }
        holder.itemView.setOnClickListener { onClick(item) }
    }

    override fun getItemCount() = list.size

    fun update(newList: List<Peratosi>) {
        list = newList
        notifyDataSetChanged()
    }
}