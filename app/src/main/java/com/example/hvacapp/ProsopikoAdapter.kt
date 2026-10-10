package com.example.hvacapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hvacapp.data.entities.Prosopiko
import com.example.hvacapp.databinding.ItemProsopikoBinding

class ProsopikoAdapter(
    private var entries: List<Prosopiko>,
    private val onDelete: (Prosopiko) -> Unit,
    private val onClick: (Prosopiko) -> Unit
) : RecyclerView.Adapter<ProsopikoAdapter.ProsopikoViewHolder>() {

    class ProsopikoViewHolder(val binding: ItemProsopikoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProsopikoViewHolder {
        val binding = ItemProsopikoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProsopikoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProsopikoViewHolder, position: Int) {
        val entry = entries[position]
        holder.binding.tvStaffName.text = entry.onoma
        holder.binding.tvStaffTotal.text = "${entry.synolo}€"

        holder.itemView.setOnClickListener {
            onClick(entry)
        }

        holder.binding.btnDeleteStaff.setOnClickListener {
            onDelete(entry)
        }
    }

    override fun getItemCount() = entries.size

    fun update(newEntries: List<Prosopiko>) {
        entries = newEntries
        notifyDataSetChanged()
    }
}
