package com.example.hvacapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hvacapp.databinding.ItemIstorikoBinding
import com.example.hvacapp.data.entities.Istoriko

class IstorikoAdapter(
    private var entries: List<Istoriko>,
    private val onDeleteClick: (Istoriko) -> Unit,
    private val onItemClick: (Istoriko) -> Unit
) : RecyclerView.Adapter<IstorikoAdapter.IstorikoViewHolder>() {

    class IstorikoViewHolder(val binding: ItemIstorikoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IstorikoViewHolder {
        val binding = ItemIstorikoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IstorikoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IstorikoViewHolder, position: Int) {
        val current = entries[position]
        holder.binding.tvIstorikoName.text = current.onoma
        holder.binding.tvIstorikoDate.text = current.hmerominia

        holder.itemView.setOnClickListener { onItemClick(current) }
        holder.binding.btnDeleteIstoriko.setOnClickListener { onDeleteClick(current) }
    }

    override fun getItemCount() = entries.size

    fun updateData(newList: List<Istoriko>) {
        this.entries = newList
        notifyDataSetChanged()
    }
}